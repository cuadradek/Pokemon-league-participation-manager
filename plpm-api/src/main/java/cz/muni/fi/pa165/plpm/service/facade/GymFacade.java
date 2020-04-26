package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.GymCreateDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;

import java.util.List;

/**
 * Gym facade layer
 *
 * @author Karolína Kolouchová
 */
public interface GymFacade {

    Long createGym(GymCreateDTO gym) throws PlpmServiceException;

    void removeGym(GymDTO gym);

    void updateGym(GymDTO gym) throws PlpmServiceException;

    GymDTO findGymById(Long id);

    GymDTO findGymByTrainer(Long trainerId);

    List<GymDTO> findGymsByCity(String city);

    List<GymDTO> findAllGyms();
}
