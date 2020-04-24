package cz.muni.fi.pa165.plpm.facade;

import cz.muni.fi.pa165.plpm.dao.TrainerDao;
import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacadeImpl;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;

import java.util.Date;

/**
 *
 * @author Jakub Doczy
 */
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class TrainerFacadeTest {

    @Mock
    private TrainerService trainerServiceMock;

    @Autowired
    private BeanMappingService beanMappingService;

    private TrainerFacade trainerFacade;

    private Trainer trainerAsh;
    private Trainer trainerGary;
    private Trainer trainerTracey;

    private TrainerDTO trainerDTO;
    private TrainerCreateDTO trainerCreateDTO;
    private TrainerUpdateInfoDTO trainerUpdateInfoDTO;
    private TrainerAuthenticateDTO trainerAuthenticateDTO;
    private TrainerChangePasswordDTO trainerChangePasswordDTO;

    @BeforeClass
    public void classSetup(){
        MockitoAnnotations.initMocks(this);
        this.trainerFacade = new TrainerFacadeImpl(trainerServiceMock, beanMappingService);
    }

    @BeforeMethod
    public void methodSetup(){
        trainerAsh = new Trainer();
        trainerAsh.setFirstName("Ash");
        trainerAsh.setLastName("Ketchum");
        trainerAsh.setNickname("Satoshi");
        trainerAsh.setBirthDate(new Date(0));
        // the most secure password in history
        trainerAsh.setPassword("nbusr123");

        trainerGary = new Trainer();
        trainerGary.setFirstName("Gary");
        trainerGary.setLastName("Oak");
        trainerGary.setNickname("Shigeru");
        trainerGary.setBirthDate(new Date(0));
        trainerGary.setAdmin(true);
        trainerGary.setPassword("Correct horse battery staple");

        trainerTracey = new Trainer();
        trainerTracey.setFirstName("Tracey ");
        trainerTracey.setLastName("Sketchit");
        trainerTracey.setNickname("Kenji");
        trainerTracey.setBirthDate(new Date(0));
        trainerTracey.setPassword("hunter2");
    }

    @Test
    public void createTrainer() {
        beanMappingService = new BeanMappingServiceImpl();
        trainerCreateDTO = beanMappingService.mapTo(trainerAsh, TrainerCreateDTO.class);

        when(trainerServiceMock.createTrainer(trainerAsh)).then(
                call -> { Trainer trainer = call.getArgument(0);
                    trainer.setId(1337L);
                    return trainer;
                });

        Long trainerId = trainerFacade.createTrainer(trainerCreateDTO);
        Assert.assertEquals(trainerId.longValue(), 1337);
    }




}
