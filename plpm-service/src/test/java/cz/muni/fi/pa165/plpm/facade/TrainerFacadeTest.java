package cz.muni.fi.pa165.plpm.facade;

import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.resources.DefaultTrainers;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacadeImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Jakub Doczy
 */
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class TrainerFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TrainerService trainerServiceMock;

    @Autowired
    private BeanMappingService beanMappingService;

    private TrainerFacade trainerFacade;

    private Trainer trainerAsh;
    private Trainer trainerGary;
    private Trainer trainerTracey;

    @BeforeClass
    public void classSetup(){
        MockitoAnnotations.initMocks(this);
        this.trainerFacade = new TrainerFacadeImpl(trainerServiceMock, beanMappingService);
    }

    @BeforeMethod
    public void methodSetup(){
        trainerAsh = DefaultTrainers.getAsh();
        trainerGary = DefaultTrainers.getGary();
        trainerTracey = DefaultTrainers.getTracey();
    }


    @Test
    public void createTrainer() {
        TrainerCreateDTO trainerCreateDTO = beanMappingService.mapTo(trainerAsh, TrainerCreateDTO.class);

        when(trainerServiceMock.createTrainer(trainerAsh)).then(
                call -> { Trainer trainer = call.getArgument(0);
                    trainer.setId(1337L);
                    return trainer;
                });

        Long trainerId = trainerFacade.createTrainer(trainerCreateDTO);
        Assert.assertEquals(trainerId.longValue(), 1337);
    }

    @Test
    public void updateTrainerInfo() {
        TrainerUpdateInfoDTO trainerUpdateInfoDTO = beanMappingService.mapTo(trainerAsh, TrainerUpdateInfoDTO.class);

        trainerFacade.updateTrainerInfo(trainerUpdateInfoDTO);
        verify(trainerServiceMock).updateTrainerInfo(trainerAsh);
    }

    @Test
    public void changePassword() {
        TrainerChangePasswordDTO trainerChangePasswordDTO = beanMappingService.mapTo(trainerAsh, TrainerChangePasswordDTO.class);
        trainerChangePasswordDTO.setOldPassword(trainerAsh.getPassword());
        trainerChangePasswordDTO.setNewPassword("CHUCK NORRIS");

        when(trainerServiceMock.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        trainerFacade.changePassword(trainerChangePasswordDTO);
        verify(trainerServiceMock).changePassword(trainerAsh, trainerAsh.getPassword(), "CHUCK NORRIS");
    }

    @Test
    public void deleteTrainer() {
        when(trainerServiceMock.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        trainerFacade.deleteTrainer(trainerAsh.getId());
        verify(trainerServiceMock).deleteTrainer(trainerAsh);
    }

    @Test
    public void findTrainerById() {
        when(trainerServiceMock.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        TrainerDTO returnedTrainerDTO = trainerFacade.findTrainerById(trainerAsh.getId());
        Trainer returnedTrainer = beanMappingService.mapTo(returnedTrainerDTO, Trainer.class);

        Assert.assertEquals(returnedTrainer, trainerAsh);
    }

    @Test
    public void findTrainerByNickname() {
        when(trainerServiceMock.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);

        TrainerDTO returnedTrainerDTO = trainerFacade.findTrainerByNickname(trainerAsh.getNickname());
        Trainer returnedTrainer = beanMappingService.mapTo(returnedTrainerDTO, Trainer.class);

        Assert.assertEquals(returnedTrainer, trainerAsh);
    }

    @Test
    public void findTrainerByFirstName() {
        when(trainerServiceMock.findTrainerByFirstName(trainerAsh.getFirstName())).thenReturn(asList(trainerAsh));

        List<TrainerDTO> returnedTrainerDTOs = trainerFacade.findTrainerByFirstName(trainerAsh.getFirstName());
        List<Trainer> returnedTrainers = beanMappingService.mapTo(returnedTrainerDTOs, Trainer.class);

        Assert.assertEquals(returnedTrainers.size(), 1);
        Assert.assertTrue(returnedTrainers.contains(trainerAsh));
    }

    @Test
    public void findTrainerByLastName() {
        when(trainerServiceMock.findTrainerByLastName(trainerAsh.getLastName())).thenReturn(asList(trainerAsh));

        List<TrainerDTO> returnedTrainerDTOs = trainerFacade.findTrainerByLastName(trainerAsh.getLastName());
        List<Trainer> returnedTrainers = beanMappingService.mapTo(returnedTrainerDTOs, Trainer.class);

        Assert.assertEquals(returnedTrainers.size(), 1);
        Assert.assertTrue(returnedTrainers.contains(trainerAsh));
    }

    @Test
    public void findAllTrainers() {
        when(trainerServiceMock.findAllTrainers()).thenReturn(asList(trainerAsh, trainerGary, trainerTracey));

        List<TrainerDTO> returnedTrainerDTOs = trainerFacade.findAllTrainers();
        List<Trainer> returnedTrainers = beanMappingService.mapTo(returnedTrainerDTOs, Trainer.class);

        Assert.assertEquals(returnedTrainers.size(), 3);
        Assert.assertTrue(returnedTrainers.contains(trainerAsh));
        Assert.assertTrue(returnedTrainers.contains(trainerGary));
        Assert.assertTrue(returnedTrainers.contains(trainerTracey));
    }

    @Test
    public void authenticate() {
        TrainerAuthenticateDTO trainerAuthenticateDTO = beanMappingService.mapTo(trainerAsh, TrainerAuthenticateDTO.class);

        when(trainerServiceMock.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);
        when(trainerServiceMock.authenticate(trainerAsh, trainerAsh.getPassword())).thenReturn(true);

        Assert.assertTrue(trainerFacade.authenticate(trainerAuthenticateDTO));
        verify(trainerServiceMock).authenticate(trainerAsh, trainerAsh.getPassword());
    }

    @Test
    public void unsuccessfulAuthenticate() {
        TrainerAuthenticateDTO trainerAuthenticateDTO = beanMappingService.mapTo(trainerAsh, TrainerAuthenticateDTO.class);
        trainerAuthenticateDTO.setPassword("invalid password");

        when(trainerServiceMock.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerGary);
        when(trainerServiceMock.authenticate(trainerAsh, "invalid password")).thenReturn(false);

        Assert.assertFalse(trainerFacade.authenticate(trainerAuthenticateDTO));
    }

    @Test
    public void isAdmin() {
        TrainerDTO trainerDTO = beanMappingService.mapTo(trainerAsh, TrainerDTO.class);
        when(trainerServiceMock.isAdmin(trainerAsh)).thenReturn(true);
        Assert.assertTrue(trainerFacade.isAdmin(trainerDTO));
    }

    @Test
    public void isNotAdmin() {
        TrainerDTO trainerDTO = beanMappingService.mapTo(trainerGary, TrainerDTO.class);
        when(trainerServiceMock.isAdmin(trainerGary)).thenReturn(false);
        Assert.assertFalse(trainerFacade.isAdmin(trainerDTO));
    }
}
