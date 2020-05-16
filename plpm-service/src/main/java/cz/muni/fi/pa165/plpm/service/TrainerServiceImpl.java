package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.TrainerDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
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

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private GymService gymService;

    @Override
    public Trainer createTrainer(Trainer trainer) {
        //check if nickname doesn't exist already
        if (findTrainerByNickname(trainer.getNickname()) != null) {
            throw new PlpmServiceException("Nickname already exists.");
        }

        trainer.setPassword(new BCryptPasswordEncoder().encode(trainer.getPassword()));
        try {
            trainerDao.createTrainer(trainer);
        } catch (ConstraintViolationException ex) {
            throw new PlpmServiceException("Constraint violation.");
        }
        return trainer;
    }

    @Override
    public boolean authenticate(Trainer trainer, String password) {
        return BCrypt.checkpw(password, trainer.getPassword());
    }

    @Override
    public void updateTrainerInfo(Trainer trainer) {
        Trainer storedTrainer = findTrainerById(trainer.getId());

        if (storedTrainer == null)
            throw new PlpmServiceException("Trainer doesn't exist.");

        //if nickname should be changed, check if there isn't such nickname already
        if (!storedTrainer.getNickname().equals(trainer.getNickname()) &&
                findTrainerByNickname(trainer.getNickname()) != null) {
            throw new PlpmServiceException("Nickname already exists.");
        }

        //isAdmin and password must be set from storedTrainer, because it wasn't updated
        trainer.setAdmin(storedTrainer.isAdmin());
        trainer.setPassword(storedTrainer.getPassword());

        try {
            trainerDao.updateTrainer(trainer);
        } catch (ConstraintViolationException ex) {
            throw new PlpmServiceException("Constraint violation.");
        }
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
        Gym gym = gymService.findGymByTrainer(trainer);
        gymService.removeGym(gym);

        List<Badge> badges = badgeService.getBadgesByTrainer(trainer);
        for (Badge badge : badges) {
            badgeService.deleteBadge(badge);
        }

        List<Pokemon> pokemons = pokemonService.findPokemonByTrainer(trainer);
        for (Pokemon pokemon : pokemons) {
            pokemon.setTrainer(null);
        }


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