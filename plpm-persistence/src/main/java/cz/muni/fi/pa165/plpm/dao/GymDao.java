package cz.muni.fi.pa165.plpm.dao;


import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.List;

/**
 * Gym DAO
 *
 * @author Karolína Kolouchová
 */
public interface GymDao {

    /**
     * Adds new gym to the database.
     *
     * @param gym - Gym we want to add
     */
    void create(Gym gym);

    /**
     * Removes gym from the database.
     *
     * @param gym - Gym we want to remove
     */
    void remove(Gym gym);

    /**
     * Updates gym in database.
     *
     * @param gym - Gym that we want to update
     */
    void update(Gym gym);

    /**
     * Finds gym with specified id.
     *
     * @param id - Id of the gym we want to obtain
     * @return Gym with given id
     */
    Gym findById(Long id);

    /**
     * Finds all gym that is led by given trainer.
     *
     * @param trainer - Trainer whose gym we want to find
     * @return Gym led by given trainer
     */
    Gym findByTrainer(Trainer trainer);

    /**
     * Finds all gyms in given city
     *
     * @param city - City from which we want to find gyms
     * @return List of gyms in given city
     */
    List<Gym> findByCity(String city);


    /**
     * @return List of all gyms from database.
     */
    List<Gym> findAll();

}
