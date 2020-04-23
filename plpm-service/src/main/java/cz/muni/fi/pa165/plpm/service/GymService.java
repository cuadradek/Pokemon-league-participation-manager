package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.List;

/**
 * Gym service interface
 *
 * @author Karolína Kolouchová
 */
public interface GymService {

    void createGym(Gym gym);

    void removeGym(Gym gym);

    void updateGym(Gym gym);

    Gym findGymById(Long id);

    Gym findGymByTrainer(Trainer trainer);

    List<Gym> findGymsByCity(String city);

    List<Gym> findAllGyms();
}
