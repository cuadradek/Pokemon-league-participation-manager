package cz.muni.fi.pa165.plpm.facade;

import cz.muni.fi.pa165.plpm.dto.GymCreateDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.GymService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.GymFacade;
import cz.muni.fi.pa165.plpm.service.facade.GymFacadeImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * author: Veronika Loukotova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GymFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private GymService gymService;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private GymFacade gymFacade = new GymFacadeImpl();

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    private GymDTO gymDTO;
    private TrainerDTO trainerDTO;
    private Gym gym;
    private Trainer trainer;
    private GymCreateDTO gymCreateDTO;

    @BeforeMethod
    public void before() {
        trainerDTO = new TrainerDTO();
        trainerDTO.setAdmin(true);
        trainerDTO.setBirthDate(new Date(23, 12, 1980));
        trainerDTO.setFirstName("John");
        trainerDTO.setId(1L);
        trainerDTO.setLastName("Lennon");
        trainerDTO.setNickname("Johny");
        trainerDTO.setPassword("1-z-5");

        gymDTO = new GymDTO();
        gymDTO.setId(1L);
        gymDTO.setCity("New York");
        gymDTO.setLeader(trainerDTO);
        gymDTO.setType(PokemonType.DARK);

        trainer = new Trainer();
        trainer.setAdmin(true);
        trainer.setBirthDate(new Date(23, 12, 1980));
        trainer.setFirstName("John");
        trainer.setId(1L);
        trainer.setLastName("Lennon");
        trainer.setNickname("Johny");
        trainer.setPassword("1-z-5");

        gym = new Gym();
        gym.setId(1L);
        gym.setCity("New York");
        gym.setLeader(trainer);
        gym.setType(PokemonType.DARK);

        gymCreateDTO = new GymCreateDTO();
        gymCreateDTO.setCity("New York");
        gymCreateDTO.setTrainerId(1L);
        gymCreateDTO.setType(PokemonType.DARK);

        when(beanMappingService.mapTo(gymDTO, Gym.class)).thenReturn(gym);
        when(beanMappingService.mapTo(gym, GymDTO.class)).thenReturn(gymDTO);
        when(gymService.findGymById(1L)).thenReturn(gym);
        when(trainerService.findTrainerById(1L)).thenReturn(trainer);
    }

    @Test
    public void createGymWithValidArguments() {
        Long gymSetId = 2L;
        gym.setId(null);

        doAnswer(invocation -> {
            Gym gym = invocation.getArgument(0);
            gym.setId(gymSetId);
            return null;
        }).when(gymService).createGym(gym);

        Long createdGymId = gymFacade.createGym(gymCreateDTO);

        Assert.assertEquals(createdGymId, gymSetId);
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void createGymWithInvalidTrainer() {
        when(trainerService.findTrainerById(1L)).thenReturn(null);

        Long createdGymId = gymFacade.createGym(gymCreateDTO);
    }

    @Test
    public void removeGym() {
        gymFacade.removeGym(gymDTO);
        verify(gymService).removeGym(gym);
    }

    @Test
    public void updateGym() {
        gymFacade.updateGym(gymDTO);
        verify(gymService).updateGym(gym);
    }

    @Test
    public void findGymById() {
        GymDTO foundGym = gymFacade.findGymById(1L);

        Assert.assertEquals(foundGym, gymDTO);
    }

    @Test
    public void findByTrainer() {
        when(gymService.findGymByTrainer(trainer)).thenReturn(gym);

        GymDTO foundGym = gymFacade.findGymByTrainer(trainer.getId());

        Assert.assertEquals(foundGym, gymDTO);
    }

    @Test
    public void findGymesByCity() {
        List<Gym> listOfGyms = Collections.singletonList(gym);
        List<GymDTO> expectedResult = Collections.singletonList(gymDTO);
        when(gymService.findGymsByCity(gym.getCity())).thenReturn(listOfGyms);
        when(beanMappingService.mapTo(listOfGyms, GymDTO.class)).thenReturn(expectedResult);

        List<GymDTO> result = gymFacade.findGymsByCity(gym.getCity());

        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void findAllGyms() {
        List<Gym> listOfGyms = Collections.singletonList(gym);
        List<GymDTO> expectedResult = Collections.singletonList(gymDTO);
        when(gymService.findAllGyms()).thenReturn(listOfGyms);
        when(beanMappingService.mapTo(listOfGyms, GymDTO.class)).thenReturn(expectedResult);

        List<GymDTO> result = gymFacade.findAllGyms();

        Assert.assertEquals(result, expectedResult);
    }
}
