package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;

import java.util.List;

/**
 * Gym service interface
 *
 * @author Karolína Kolouchová
 */
public interface GymService {

    void createGym(Gym gym) throws PlpmServiceException;

    void removeGym(Gym gym);

    void updateGym(Gym gym) throws PlpmServiceException;

    Gym findGymById(Long id);

    Gym findGymByTrainer(Trainer trainer);

    List<Gym> findGymsByCity(String city);

    List<Gym> findAllGyms();
}
