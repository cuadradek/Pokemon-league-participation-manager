package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerUpdateInfoDTO;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import cz.muni.fi.pa165.plpm.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Radoslav Cerhak
 *
 */
@Service
@Transactional
public class TrainerFacadeImpl implements TrainerFacade {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createTrainer(TrainerCreateDTO trainerCreateDTO) {
        return trainerService.createTrainer(beanMappingService
                .mapTo(trainerCreateDTO, Trainer.class)).getId();
    }

    @Override
    public void updateTrainerInfo(TrainerUpdateInfoDTO trainerUpdateInfoDTO) {
        trainerService.updateTrainerInfo(beanMappingService.mapTo(trainerUpdateInfoDTO, Trainer.class));
    }

    @Override
    public boolean changePassword(TrainerChangePasswordDTO trainerChangePasswordDTO) {
        Trainer trainer = trainerService.findTrainerById(trainerChangePasswordDTO.getId());

        return trainerService.changePassword(trainer, trainerChangePasswordDTO.getOldPassword(),
                trainerChangePasswordDTO.getNewPassword());
    }

    @Override
    public void deleteTrainer(Long id) {
        Trainer trainer = new Trainer();
        trainer.setId(id);
        trainerService.deleteTrainer(trainer);
    }

    @Override
    public TrainerDTO findTrainerById(Long id) {
        Trainer trainer = trainerService.findTrainerById(id);
        return trainer == null ? null : beanMappingService.mapTo(trainer, TrainerDTO.class);
    }

    @Override
    public TrainerDTO findTrainerByNickname(String nickname) {
        Trainer trainer = trainerService.findTrainerByNickname(nickname);
        return trainer == null ? null : beanMappingService.mapTo(trainer, TrainerDTO.class);
    }

    @Override
    public List<TrainerDTO> findTrainerByFirstName(String firstName) {
        return beanMappingService.mapTo(trainerService.findTrainerByFirstName(firstName), TrainerDTO.class);
    }

    @Override
    public List<TrainerDTO> findTrainerByLastName(String lastName) {
        return beanMappingService.mapTo(trainerService.findTrainerByLastName(lastName), TrainerDTO.class);
    }

    @Override
    public List<TrainerDTO> findAllTrainers() {
        return beanMappingService.mapTo(trainerService.findAllTrainers(), TrainerDTO.class);
    }

    @Override
    public boolean authenticate(TrainerAuthenticateDTO trainerAuthenticateDTO) {
        Trainer trainer = trainerService.findTrainerByNickname(trainerAuthenticateDTO.getNickname());

        if (trainer == null) return false;

        return trainerService.authenticate(trainer, trainerAuthenticateDTO.getPassword());
    }

    @Override
    public boolean isAdmin(TrainerDTO trainerDTO) {
        return trainerService.isAdmin(beanMappingService.mapTo(trainerDTO, Trainer.class));
    }
}
