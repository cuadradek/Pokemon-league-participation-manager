package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.GymCreateDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karolína Kolouchová
 */
@Service
@Transactional
public class GymFacadeImpl implements GymFacade {

    @Autowired
    private GymService gymService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createGym(GymCreateDTO gymCreateDTO) {
        Trainer trainer = trainerService.findTrainerById(gymCreateDTO.getTrainerId());
        if (trainer == null) {
            throw new IllegalStateException("Couldn't create gym - trainer with id " + gymCreateDTO.getTrainerId() + " doesn't exist");
        }

        Gym gym = new Gym();
        gym.setLeader(trainer);
        gym.setType(gymCreateDTO.getType());
        gym.setCity(gymCreateDTO.getCity());
        gymService.createGym(gym);
        return gym.getId();
    }

    @Override
    public void removeGym(GymDTO gymDTO) {
        gymService.removeGym(beanMappingService.mapTo(gymDTO, Gym.class));
    }

    @Override
    public void updateGym(GymDTO gymDTO) {
        gymService.updateGym(beanMappingService.mapTo(gymDTO, Gym.class));
    }

    @Override
    public GymDTO findGymById(Long id) {
        Gym gym = gymService.findGymById(id);
        return gym != null ? beanMappingService.mapTo(gym, GymDTO.class) : null;
    }

    @Override
    public GymDTO findGymByTrainer(Long trainerId) {
        Trainer trainer = trainerService.findTrainerById(trainerId);
        Gym gym = gymService.findGymByTrainer(trainer);
        return gym != null ? beanMappingService.mapTo(gym, GymDTO.class) : null;
    }

    @Override
    public List<GymDTO> findGymsByCity(String city) {
        List<Gym> gyms = gymService.findGymsByCity(city);
        return gyms != null ? beanMappingService.mapTo(gyms, GymDTO.class) : null;
    }

    @Override
    public List<GymDTO> findAllGyms() {
        List<Gym> gyms = gymService.findAllGyms();
        return gyms != null ? beanMappingService.mapTo(gyms, GymDTO.class) : null;
    }

    @Override
    public boolean attackGym(long trainerId, long[] selectedPokemonIds, long gymId) {
        int attackCost = 1;
        Trainer trainer = trainerService.findTrainerById(trainerId);

        if (trainer.getActionPoints() < attackCost) {
            throw new PlpmServiceException("Trainer has not enough actions points to perform attack. Trainers points: "
                    + trainer.getActionPoints() + "; required points: " + attackCost);
        }

        trainer.setActionPoints(trainer.getActionPoints() - 1);
        trainerService.updateTrainerInfo(trainer);

        Gym attackedGym = gymService.findGymById(gymId);
        List<Pokemon> defendingPokemons = pokemonService.findPokemonByTrainer(attackedGym.getLeader());

        while (defendingPokemons.size() > 5) {
            // Theoretically we should remove pokemons with lowest level (or random ones) but this is simpler
            defendingPokemons.remove(defendingPokemons.size() - 1);
        }

        List<Pokemon> attackingPokemons = new ArrayList<>();

        for (long attackingPokemonId : selectedPokemonIds) {
            attackingPokemons.add(pokemonService.findPokemonById(attackingPokemonId));
        }

        return pokemonService.fight(attackingPokemons, defendingPokemons) == BattleResults.ATTACKER_WINS;
    }


}
