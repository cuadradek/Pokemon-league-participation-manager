package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;

import java.util.List;

/**
 * Trainer Service interface.
 *
 * @author Radoslav Cerhak
 */
public interface TrainerService {

    Trainer createTrainer(Trainer trainer);

    boolean authenticate(Trainer trainer, String password);

    void updateTrainerInfo(Trainer trainer);

    boolean changePassword(Trainer trainer, String oldPassword, String newPassword);

    void addActionPoints(Trainer trainer, int actionPoints);

    void deleteTrainer(Trainer trainer);

    Trainer findTrainerById(Long id);

    Trainer findTrainerByNickname(String nickname);

    List<Trainer> findTrainerByFirstName(String firstName);

    List<Trainer> findTrainerByLastName(String lastName);

    List<Trainer> findAllTrainers();

    boolean isAdmin(Trainer trainer);

    void addActionPointsToEveryTrainer();

}
