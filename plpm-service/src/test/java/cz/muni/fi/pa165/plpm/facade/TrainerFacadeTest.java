package cz.muni.fi.pa165.plpm.facade;

import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.resources.DefaultTrainers;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import cz.muni.fi.pa165.plpm.service.TrainerServiceImpl;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacadeImpl;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

import java.util.List;

/**
 * Tests of {@link TrainerFacade}.
 *
 * @author Jakub Doczy
 */
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class TrainerFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TrainerService trainerService;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private TrainerFacade trainerFacade = new TrainerFacadeImpl();

    private Trainer trainerAsh;
    private Trainer trainerGary;
    private Trainer trainerTracey;

    private TrainerDTO trainerAshDTO;
    private TrainerCreateDTO trainerAshCreateDTO;
    private TrainerUpdateInfoDTO trainerAshUpdateInfoDTO;
    private TrainerChangePasswordDTO trainerAshChangePasswordDTO;
    private TrainerAuthenticateDTO trainerAshAuthenticateDTO;

    @BeforeClass
    public void classSetup(){
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void methodSetup(){
        trainerAsh = DefaultTrainers.getAsh();
        trainerGary = DefaultTrainers.getGary();
        trainerTracey = DefaultTrainers.getTracey();

        trainerAshDTO = new TrainerDTO();
        trainerAshDTO.setId(trainerAsh.getId());
        trainerAshDTO.setNickname(trainerAsh.getNickname());
        trainerAshDTO.setFirstName(trainerAsh.getFirstName());
        trainerAshDTO.setLastName(trainerAsh.getLastName());
        trainerAshDTO.setBirthDate(trainerAsh.getBirthDate());
        trainerAshDTO.setPassword(trainerAsh.getPassword());
        trainerAshDTO.setActionPoints(trainerAsh.getActionPoints());

        trainerAshCreateDTO = new TrainerCreateDTO();
        trainerAshCreateDTO.setNickname(trainerAsh.getNickname());
        trainerAshCreateDTO.setFirstName(trainerAsh.getFirstName());
        trainerAshCreateDTO.setLastName(trainerAsh.getLastName());
        trainerAshCreateDTO.setBirthDate(trainerAsh.getBirthDate());
        trainerAshCreateDTO.setPassword(trainerAsh.getPassword());

        trainerAshUpdateInfoDTO = new TrainerUpdateInfoDTO();
        trainerAshUpdateInfoDTO.setId(trainerAsh.getId());
        trainerAshUpdateInfoDTO.setNickname(trainerAsh.getNickname());
        trainerAshUpdateInfoDTO.setFirstName(trainerAsh.getFirstName());
        trainerAshUpdateInfoDTO.setLastName(trainerAsh.getLastName());
        trainerAshUpdateInfoDTO.setBirthDate(trainerAsh.getBirthDate());

        trainerAshChangePasswordDTO = new TrainerChangePasswordDTO();
        trainerAshChangePasswordDTO.setId(trainerAsh.getId());
        trainerAshChangePasswordDTO.setOldPassword(DefaultTrainers.getPlainPasswordAsh());
        trainerAshChangePasswordDTO.setNewPassword("123456");

        trainerAshAuthenticateDTO = new TrainerAuthenticateDTO();
        trainerAshAuthenticateDTO.setNickname(trainerAsh.getNickname());
        trainerAshAuthenticateDTO.setPassword(DefaultTrainers.getPlainPasswordAsh());

        when(beanMappingService.mapTo(trainerAsh, TrainerDTO.class)).thenReturn(trainerAshDTO);
        when(beanMappingService.mapTo(trainerAshDTO, Trainer.class)).thenReturn(trainerAsh);
        when(beanMappingService.mapTo(trainerAsh, TrainerAuthenticateDTO.class)).thenReturn(trainerAshAuthenticateDTO);
        when(beanMappingService.mapTo(trainerAshAuthenticateDTO, Trainer.class)).thenReturn(trainerAsh);
        when(beanMappingService.mapTo(trainerAsh, TrainerChangePasswordDTO.class)).thenReturn(trainerAshChangePasswordDTO);
        when(beanMappingService.mapTo(trainerAshChangePasswordDTO, Trainer.class)).thenReturn(trainerAsh);
        when(beanMappingService.mapTo(trainerAsh, TrainerUpdateInfoDTO.class)).thenReturn(trainerAshUpdateInfoDTO);
        when(beanMappingService.mapTo(trainerAshUpdateInfoDTO, Trainer.class)).thenReturn(trainerAsh);
        when(beanMappingService.mapTo(trainerAsh, TrainerCreateDTO.class)).thenReturn(trainerAshCreateDTO);
        when(beanMappingService.mapTo(trainerAshCreateDTO, Trainer.class)).thenReturn(trainerAsh);
    }

    @Test
    public void createTrainer() {
        when(trainerService.createTrainer(trainerAsh)).then(
                call -> { Trainer trainer = call.getArgument(0);
                    trainer.setId(1337L);
                    return trainer;
                });

        Long trainerId = trainerFacade.createTrainer(trainerAshCreateDTO);
        Assert.assertEquals(trainerId.longValue(), 1337);
    }

    @Test
    public void updateTrainerInfo() {
        trainerFacade.updateTrainerInfo(trainerAshUpdateInfoDTO);
        verify(trainerService).updateTrainerInfo(trainerAsh);
    }

    @Test
    public void changePassword() {
        trainerAshChangePasswordDTO.setNewPassword("CHUCK NORRIS");

        when(trainerService.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        trainerFacade.changePassword(trainerAshChangePasswordDTO);
        verify(trainerService).changePassword(trainerAsh, DefaultTrainers.getPlainPasswordAsh(), "CHUCK NORRIS");
    }

    @Test
    public void deleteTrainer() {
        ArgumentCaptor<Trainer> trainerCaptor = ArgumentCaptor.forClass(Trainer.class);
        when(trainerService.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        trainerFacade.deleteTrainer(trainerAsh.getId());
        verify(trainerService, times(1)).deleteTrainer(trainerCaptor.capture());
        Assert.assertEquals(trainerCaptor.getValue().getId(), trainerAsh.getId());
    }

    @Test
    public void addActionPointsToEveryTrainer() {
        trainerFacade.addActionPointsToEveryTrainer();
        verify(trainerService).addActionPointsToEveryTrainer();
    }

    @Test
    public void findTrainerById() {
        when(trainerService.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        TrainerDTO returnedTrainerDTO = trainerFacade.findTrainerById(trainerAsh.getId());
        Trainer returnedTrainer = beanMappingService.mapTo(returnedTrainerDTO, Trainer.class);

        Assert.assertEquals(returnedTrainer, trainerAsh);
    }

    @Test
    public void findTrainerByNickname() {
        when(trainerService.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);

        TrainerDTO returnedTrainerDTO = trainerFacade.findTrainerByNickname(trainerAsh.getNickname());
        Trainer returnedTrainer = beanMappingService.mapTo(returnedTrainerDTO, Trainer.class);

        Assert.assertEquals(returnedTrainer, trainerAsh);
    }

    @Test
    public void findTrainerByFirstName() {
        when(trainerService.findTrainerByFirstName(trainerAsh.getFirstName())).thenReturn(asList(trainerAsh));
        when(beanMappingService.mapTo(asList(trainerAsh), TrainerDTO.class)).thenReturn(asList(trainerAshDTO));

        List<TrainerDTO> returnedTrainerDTOs = trainerFacade.findTrainerByFirstName(trainerAsh.getFirstName());

        Assert.assertEquals(returnedTrainerDTOs.size(), 1);
        Assert.assertTrue(returnedTrainerDTOs.contains(trainerAshDTO));
    }

    @Test
    public void findTrainerByLastName() {
        when(trainerService.findTrainerByLastName(trainerAsh.getLastName())).thenReturn(asList(trainerAsh));
        when(beanMappingService.mapTo(asList(trainerAsh), TrainerDTO.class)).thenReturn(asList(trainerAshDTO));

        List<TrainerDTO> returnedTrainerDTOs = trainerFacade.findTrainerByLastName(trainerAsh.getLastName());

        Assert.assertEquals(returnedTrainerDTOs.size(), 1);
        Assert.assertTrue(returnedTrainerDTOs.contains(trainerAshDTO));
    }

    @Test
    public void findAllTrainers() {
        TrainerDTO garyMockDTO = new TrainerDTO();
        garyMockDTO.setId(trainerGary.getId());
        TrainerDTO traceyMockDTO = new TrainerDTO();
        traceyMockDTO.setId(trainerTracey.getId());

        List<Trainer> trainers = asList(trainerAsh, trainerGary, trainerTracey);
        List<TrainerDTO> trainerDTOS = asList(trainerAshDTO, garyMockDTO, traceyMockDTO);

        when(beanMappingService.mapTo(trainers, TrainerDTO.class)).thenReturn(trainerDTOS);
        when(trainerService.findAllTrainers()).thenReturn(trainers);

        List<TrainerDTO> returnedTrainerDTOs = trainerFacade.findAllTrainers();

        Assert.assertEquals(returnedTrainerDTOs, asList(trainerAshDTO, garyMockDTO, traceyMockDTO));
    }

    @Test
    public void authenticate() {
        when(trainerService.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);
        when(trainerService.authenticate(trainerAsh, DefaultTrainers.getPlainPasswordAsh())).thenReturn(true);

        Assert.assertTrue(trainerFacade.authenticate(trainerAshAuthenticateDTO));
        verify(trainerService).authenticate(trainerAsh, DefaultTrainers.getPlainPasswordAsh());
    }

    @Test
    public void unsuccessfulAuthenticate() {
        trainerAshAuthenticateDTO.setPassword("invalid password");

        when(trainerService.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);
        when(trainerService.authenticate(trainerAsh, "invalid password")).thenReturn(false);

        Assert.assertFalse(trainerFacade.authenticate(trainerAshAuthenticateDTO));
    }

    @Test
    public void isAdmin() {
        when(trainerService.isAdmin(trainerAsh)).thenReturn(true);

        Assert.assertTrue(trainerFacade.isAdmin(trainerAshDTO));
    }

    @Test
    public void isNotAdmin() {
        TrainerDTO trainerGaryDTO = new TrainerDTO();
        trainerGaryDTO.setId(trainerGary.getId());
        trainerGaryDTO.setNickname(trainerGary.getNickname());
        trainerGaryDTO.setFirstName(trainerGary.getFirstName());
        trainerGaryDTO.setLastName(trainerGary.getLastName());
        trainerGaryDTO.setPassword(trainerGary.getPassword());
        trainerGaryDTO.setBirthDate(trainerGary.getBirthDate());
        trainerGaryDTO.setAdmin(trainerGary.isAdmin());

        when(trainerService.isAdmin(trainerGary)).thenReturn(false);
        Assert.assertFalse(trainerFacade.isAdmin(trainerGaryDTO));
    }
}
