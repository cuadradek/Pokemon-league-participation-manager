package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.GymCreateDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;

import java.util.List;

/**
 * Gym facade layer
 *
 * @author Karolína Kolouchová
 */
public interface GymFacade {

    Long createGym(GymCreateDTO gym);

    void removeGym(GymDTO gym);

    void updateGym(GymDTO gym);

    GymDTO findGymById(Long id);

    GymDTO findGymByTrainer(Long trainerId);

    List<GymDTO> findGymsByCity(String city);

    List<GymDTO> findAllGyms();

    boolean attackGym(long trainerId, long[] selectedPokemonIds, long gymId);
}
