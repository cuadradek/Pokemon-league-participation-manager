package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.GymDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Veronika Loukotova
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class GymServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private GymDao gymDao;

    @Mock
    private BadgeService badgeService;

    @Autowired
    @InjectMocks
    private GymService gymService;

    private Badge badge1;
    private Badge badge2;
    private Trainer trainer1;
    private Trainer trainer2;
    private Gym gym1;
    private Gym gym2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void before() {
        trainer1 = new Trainer();
        trainer1.setBirthDate(new Date(15, 10, 1980));
        trainer1.setFirstName("John");
        trainer1.setLastName("Lennon");
        trainer1.setNickname("Johny");
        trainer1.setId(1L);
        trainer1.setAdmin(true);
        trainer1.setPassword("1ab");

        trainer2 = new Trainer();
        trainer2.setBirthDate(new Date(10, 2, 1955));
        trainer2.setFirstName("Ringo");
        trainer2.setLastName("Starr");
        trainer2.setNickname("Dingo");
        trainer2.setPassword("ab1");
        trainer2.setId(2L);

        gym1 = new Gym();
        gym1.setId(1L);
        gym1.setCity("New York");
        gym1.setType(PokemonType.ELECTRIC);
        gym1.setLeader(trainer1);

        gym2 = new Gym();
        gym2.setId(2L);
        gym2.setCity("Madrid");
        gym2.setType(PokemonType.ROCK);
        gym2.setLeader(trainer2);

        badge1 = new Badge();
        badge1.setGym(gym1);
        badge1.setTrainer(trainer1);
        badge1.setId(1L);

        badge2 = new Badge();
        badge2.setId(2L);
        badge2.setTrainer(trainer2);
        badge2.setGym(gym2);
    }

    @Test
    public void createGym() {
        when(gymDao.findByTrainer(trainer1)).thenReturn(null);

        gymService.createGym(gym1);
        verify(gymDao).create(gym1);
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void createGymWithTrainerOfAnotherGym() {
        when(gymDao.findByTrainer(trainer1)).thenReturn(gym2);

        gymService.createGym(gym1);
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void createGymDaoException() {
        when(gymDao.findByTrainer(trainer1)).thenReturn(null);
        doThrow(ConstraintViolationException.class).when(gymDao).create(gym1);

        gymService.createGym(gym1);
    }

    @Test
    public void removeGym() {
        when(badgeService.getBadgesByGym(gym1)).thenReturn(Arrays.asList(badge1, badge2));

        gymService.removeGym(gym1);

        verify(badgeService).getBadgesByGym(gym1);
        verify(badgeService).deleteBadge(badge1);
        verify(badgeService).deleteBadge(badge2);
        verify(gymDao).remove(gym1);
    }

    @Test
    public void updateGym() {
        when(gymDao.findByTrainer(trainer1)).thenReturn(null);

        gymService.updateGym(gym1);

        verify(gymDao).update(gym1);
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void updateGymDaoException() {
        when(gymDao.findByTrainer(trainer1)).thenReturn(null);
        doThrow(ConstraintViolationException.class).when(gymDao).update(gym1);

        gymService.updateGym(gym1);
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void updateGymWithLeaderOfAnotherGym() {
        when(gymDao.findByTrainer(trainer1)).thenReturn(gym2);

        gymService.updateGym(gym1);
    }

    @Test
    public void findGymById() {
        when(gymDao.findById(gym1.getId())).thenReturn(gym1);

        Gym foundGym = gymService.findGymById(gym1.getId());

        Assert.assertEquals(foundGym, gym1);
    }

    @Test
    public void findGymByTrainer() {
        when(gymDao.findByTrainer(trainer1)).thenReturn(gym1);

        Gym foundGym = gymService.findGymByTrainer(trainer1);

        Assert.assertEquals(foundGym, gym1);
    }

    @Test
    public void findGymByCity() {
        when(gymDao.findByCity(gym1.getCity())).thenReturn(Collections.singletonList(gym1));

        List<Gym> foundGyms = gymService.findGymsByCity(gym1.getCity());

        Assert.assertEquals(foundGyms, Collections.singletonList(gym1));
    }

    @Test
    public void findAllGyms() {
        when(gymDao.findAll()).thenReturn(Arrays.asList(gym1, gym2));

        List<Gym> foundGyms = gymService.findAllGyms();

        Assert.assertEquals(foundGyms, Arrays.asList(gym1, gym2));
    }
}
