package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.TrainerDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Trainer Service implementation.
 *
 * @author Radoslav Cerhak
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerDao trainerDao;

//    @Autowired
//    private BadgeService badgeService;
//
//    @Autowired
//    private PokemonService pokemonService;
//
//    @Autowired
//    private GymService gymService;

    @Override
    public Trainer createTrainer(Trainer trainer) throws PlpmServiceException {
        //check if nickname doesn't exist already
        if (findTrainerByNickname(trainer.getNickname()) != null) {
            throw new PlpmServiceException("Nickname already exists.");
        }

        trainer.setPassword(BCrypt.hashpw(trainer.getPassword(), BCrypt.gensalt()));
        trainerDao.createTrainer(trainer);
        return trainer;
    }

    @Override
    public boolean authenticate(Trainer trainer, String password) {
        return BCrypt.checkpw(password, trainer.getPassword());
    }

    @Override
    public void updateTrainerInfo(Trainer trainer) throws PlpmServiceException {
        Trainer storedTrainer = findTrainerById(trainer.getId());

        //if nickname should be changed, check if there isn't such nickname already
        if (!storedTrainer.getNickname().equals(trainer.getNickname())) {
            if (findTrainerByNickname(trainer.getNickname()) != null) {
                throw new PlpmServiceException("Nickname already exists.");
            } else storedTrainer.setNickname(trainer.getNickname());
        }

        storedTrainer.setFirstName(trainer.getFirstName());
        storedTrainer.setLastName(trainer.getLastName());
        storedTrainer.setBirthDate(trainer.getBirthDate());

        trainerDao.updateTrainer(storedTrainer);
    }

    @Override
    public boolean changePassword(Trainer trainer, String oldPassword, String newPassword) {
        if (!authenticate(trainer, oldPassword)) return false;

        trainer.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        trainerDao.updateTrainer(trainer);

        return true;
    }

    @Override
    public void deleteTrainer(Trainer trainer) {
//        List<Badge> badges = badgeService.findByTrainer(trainer);
//        for (Badge badge : badges) {
//            badgeService.remove(badge);
//        }

//        List<Pokemon> pokemons = pokemonService.findByTrainer(trainer);
//        for (Pokemon pokemon : pokemons) {
//            pokemon.setTrainer(null);
//            pokemonService.update(pokemon);
//             or:
//            pokemonService.releasePokemon(pokemon);
//        }

//        Gym gym = gymService.findByTrainer(trainer);
//        gymService.remove(gym)

        trainerDao.deleteTrainer(trainer);
    }

    @Override
    public Trainer findTrainerById(Long id) {
        return trainerDao.findTrainerById(id);
    }

    @Override
    public Trainer findTrainerByNickname(String nickname) {
        return trainerDao.findTrainerByNickname(nickname);
    }

    @Override
    public List<Trainer> findTrainerByFirstName(String firstName) {
        return trainerDao.findTrainerByFirstName(firstName);
    }

    @Override
    public List<Trainer> findTrainerByLastName(String lastName) {
        return trainerDao.findTrainerByLastName(lastName);
    }

    @Override
    public List<Trainer> findAllTrainers() {
        return trainerDao.findAllTrainers();
    }

    @Override
    public boolean isAdmin(Trainer trainer) {
        return findTrainerById(trainer.getId()).isAdmin();
    }

}