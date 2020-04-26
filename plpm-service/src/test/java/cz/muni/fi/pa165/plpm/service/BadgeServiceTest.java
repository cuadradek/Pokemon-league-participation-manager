package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.BadgeDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Radoslav Cerhak
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BadgeServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private BadgeDao badgeDao;

    @Mock
    private GymService gymService;

    @Autowired
    @InjectMocks
    private BadgeService badgeService;

    private Gym gym1;
    private Gym gym2;
    private Gym gym3;

    private Trainer trainer1;
    private Trainer trainer2;
    private Trainer trainer3;

    private Badge badge1;
    private Badge badge2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(badgeDao);
        Mockito.reset(gymService);
    }


    @BeforeMethod
    public void before() {
        trainer1 = new Trainer();
        trainer1.setId(1L);
        trainer1.setNickname("roxy");
        trainer1.setFirstName("Roxanne");
        trainer1.setLastName("Tsutsuji");
        trainer1.setBirthDate(new Date());
        trainer1.setPassword("123");

        trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setNickname("norm");
        trainer2.setFirstName("Norman");
        trainer2.setLastName("Senri");
        trainer2.setPassword("123");
        trainer2.setBirthDate(new Date());

        trainer3 = new Trainer();
        trainer1.setId(3L);
        trainer3.setNickname("ashy");
        trainer3.setFirstName("Ash");
        trainer3.setLastName("Satoshi");
        trainer3.setBirthDate(new Date());
        trainer3.setPassword("123");

        gym1 = new Gym();
        gym1.setId(1L);
        gym1.setCity("Kanazumi City");
        gym1.setType(PokemonType.ROCK);
        gym1.setLeader(trainer1);

        gym2 = new Gym();
        gym1.setId(2L);
        gym2.setCity("Touka City");
        gym2.setType(PokemonType.NORMAL);
        gym2.setLeader(trainer2);

        gym3 = new Gym();
        gym1.setId(3L);
        gym3.setCity("Kinsetsu City");
        gym3.setType(PokemonType.ELECTRIC);
        gym3.setLeader(trainer3);

        badge1 = new Badge();
        badge1.setId(1L);
        badge1.setGym(gym2);
        badge1.setTrainer(trainer1);

        badge2 = new Badge();
        badge2.setId(2L);
        badge2.setGym(gym1);
        badge2.setTrainer(trainer3);
    }

    @Test
    public void getAllBadges() {
        when(badgeDao.findAll()).thenReturn(Arrays.asList(badge1, badge2));

        List<Badge> badges = badgeService.getAllBadges();

        Assert.assertEquals(badges.size(), 2);
        Assert.assertTrue(badges.contains(badge1));
        Assert.assertTrue(badges.contains(badge2));
    }

    @Test
    public void getAllBadgesEmpty() {
        when(badgeDao.findAll()).thenReturn(new ArrayList<>());

        List<Badge> badges = badgeService.getAllBadges();

        Assert.assertEquals(badges.size(), 0);
    }

    @Test
    public void getBadgesByTrainer() {
        when(badgeDao.findByTrainer(trainer1)).thenReturn(Collections.singletonList(badge1));

        List<Badge> badges = badgeService.getBadgesByTrainer(trainer1);

        Assert.assertEquals(badges.size(), 1);
        Assert.assertTrue(badges.contains(badge1));
    }

    @Test
    public void getBadgesByTrainerEmpty() {
        when(badgeDao.findByTrainer(trainer2)).thenReturn(new ArrayList<>());

        List<Badge> badges = badgeService.getBadgesByTrainer(trainer2);

        Assert.assertEquals(badges.size(), 0);
    }

    @Test
    public void getBadgesByGym() {
        when(badgeDao.findByGym(gym1)).thenReturn(Collections.singletonList(badge2));

        List<Badge> badges = badgeService.getBadgesByGym(gym1);

        Assert.assertEquals(badges.size(), 1);
        Assert.assertTrue(badges.contains(badge2));
    }

    @Test
    public void getBadgesByGymEmpty() {
        when(badgeDao.findByGym(gym3)).thenReturn(new ArrayList<>());

        List<Badge> badges = badgeService.getBadgesByGym(gym3);

        Assert.assertEquals(badges.size(), 0);
    }

    @Test
    public void getBadgeById() {
        when(badgeDao.findById(1L)).thenReturn(badge1);

        Badge badge = badgeService.getBadgeById(1L);

        Assert.assertEquals(badge, badge1);
    }

    @Test
    public void getBadgeByNotExistingId() {
        when(badgeDao.findById(4000L)).thenReturn(null);

        Badge badge = badgeService.getBadgeById(4000L);

        Assert.assertNull(badge);
    }

    @Test
    public void createBadge() {
        badge1.setId(null);
        doAnswer(invocation -> {
            Badge b = invocation.getArgument(0);
            b.setId(1L);
            return null;
        }).when(badgeDao).create(badge1);

        badgeService.createBadge(badge1);

        Assert.assertEquals(badge1.getId(), new Long(1L));
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void createBadgeForTheGymLeader() {
        badge1.setId(null);
        badge1.setGym(gym1);
        badge1.setTrainer(badge1.getGym().getLeader());

        badgeService.createBadge(badge1);
    }

    @Test(expectedExceptions = DataAccessException.class)
    public void createBadgeWithExistingId() {
        doThrow(new DataAccessException("Badge has id") {}).when(badgeDao).create(badge1);

        badgeService.createBadge(badge1);
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void createBadgeWithNullTrainer() {
        badge1.setTrainer(null);
        doThrow(new ConstraintViolationException(new HashSet<>()) {}).when(badgeDao).create(badge1);

        badgeService.createBadge(badge1);
    }

    @Test
    public void deleteBadge() {
        badgeService.deleteBadge(badge1);

        verify(badgeDao).remove(badge1);
    }

    @Test
    public void getBeatenGyms() {
        when(badgeDao.findByTrainer(trainer1)).thenReturn(Collections.singletonList(badge1));
        when(gymService.findGymByTrainer(trainer1)).thenReturn(gym1);

        Set<Gym> gyms = badgeService.getBeatenGyms(trainer1);

        Assert.assertEquals(gyms.size(), 2);
        Assert.assertTrue(gyms.contains(gym1));
        Assert.assertTrue(gyms.contains(gym2));
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void getBeatenGymsBadgeFromOwnGym() {
        badge1.setGym(gym1);
        when(badgeDao.findByTrainer(trainer1)).thenReturn(Collections.singletonList(badge1));

        badgeService.getBeatenGyms(trainer1);
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void getBeatenGymsTwoBadgesFromTheSameGym() {
        badge1.setGym(gym1);
        badge2.setGym(gym1);
        badge1.setTrainer(trainer1);
        badge2.setTrainer(trainer1);
        when(badgeDao.findByTrainer(trainer1)).thenReturn(Arrays.asList(badge1, badge2));

        badgeService.getBeatenGyms(trainer1);
    }

}
