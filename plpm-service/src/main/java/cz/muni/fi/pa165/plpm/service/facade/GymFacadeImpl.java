package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.GymCreateDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.GymService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
