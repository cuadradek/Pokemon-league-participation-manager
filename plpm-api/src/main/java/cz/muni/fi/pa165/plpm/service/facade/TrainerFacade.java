package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerUpdateInfoDTO;

import java.util.List;

/**
 * @author Radoslav Cerhak
 *
 */
public interface TrainerFacade {

    Long createTrainer(TrainerCreateDTO trainerCreateDTO);

    void updateTrainerInfo(TrainerUpdateInfoDTO trainerUpdateInfoDTO);

    boolean changePassword(TrainerChangePasswordDTO trainerChangePasswordDTO);

    void deleteTrainer(Long id);

    TrainerDTO findTrainerById(Long id);

    TrainerDTO findTrainerByNickname(String nickname);

    List<TrainerDTO> findTrainerByFirstName(String firstName);

    List<TrainerDTO> findTrainerByLastName(String lastName);

    List<TrainerDTO> findAllTrainers();

    boolean authenticate(TrainerAuthenticateDTO trainerAuthenticateDTO);

    boolean isAdmin(TrainerDTO trainerDTO);
}
