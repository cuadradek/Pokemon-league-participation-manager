package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.List;

/**
 * Trainer DAO interface.
 *
 * @author Radoslav Cerhak
 */
public interface TrainerDao {

    /**
     * Inserts trainer into DB.
     *
     * @param trainer
     */
    void createTrainer(Trainer trainer);

    /**
     * Updates trainer in DB.
     *
     * @param trainer
     */
    void updateTrainer(Trainer trainer);

    /**
     * Removes trainer from DB.
     *
     * @param trainer
     */
    void deleteTrainer(Trainer trainer);

    /**
     * Finds trainer by id in DB.
     *
     * @param id
     * @return trainer or null if there is no trainer with the given id.
     */
    Trainer findTrainerById(Long id);

    /**
     * Finds trainer by nickname in DB.
     *
     * @param nickname
     * @return trainer or null if there is no trainer with the given nickname.
     */
    Trainer findTrainerByNickname(String nickname);

    /**
     * Finds trainers by first name in DB.
     *
     * @param firstName
     * @return list of trainers.
     */
    List<Trainer> findTrainerByFirstName(String firstName);

    /**
     * Finds trainers by last name in DB.
     *
     * @param lastName
     * @return list of trainers.
     */
    List<Trainer> findTrainerByLastName(String lastName);

    /**
     * Finds all trainers in DB.
     *
     * @return list of trainers.
     */
    List<Trainer> findAllTrainers();
}
