package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Date;

/**
 * @author Karolína Kolouchová
 */
@ContextConfiguration(classes= PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class BadgeDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BadgeDao badgeDao;

    @Autowired
    private TrainerDao trainerDao;

    @Autowired
    private GymDao gymDao;

    private Gym gym1;
    private Gym gym2;
    private Gym gym3;

    private Trainer trainer1;
    private Trainer trainer2;
    private Trainer trainer3;

    private Badge badge1;
    private Badge badge2;

    @BeforeMethod
    public void init() {
        trainer1 = new Trainer();
        trainer1.setNickname("roxy");
        trainer1.setFirstName("Roxanne");
        trainer1.setLastName("Tsutsuji");
        trainer1.setBirthDate(new Date());
        trainer1.setPassword("123");
        trainerDao.createTrainer(trainer1);

        trainer2 = new Trainer();
        trainer2.setNickname("norm");
        trainer2.setFirstName("Norman");
        trainer2.setLastName("Senri");
        trainer2.setPassword("123");
        trainer2.setBirthDate(new Date());
        trainerDao.createTrainer(trainer2);

        trainer3 = new Trainer();
        trainer3.setNickname("ashy");
        trainer3.setFirstName("Ash");
        trainer3.setLastName("Satoshi");
        trainer3.setBirthDate(new Date());
        trainer3.setPassword("123");
        trainerDao.createTrainer(trainer3);

        gym1 = new Gym();
        gym1.setCity("Kanazumi City");
        gym1.setType(PokemonType.ROCK);
        gym1.setLeader(trainer1);
        gymDao.create(gym1);

        gym2 = new Gym();
        gym2.setCity("Touka City");
        gym2.setType(PokemonType.NORMAL);
        gym2.setLeader(trainer2);
        gymDao.create(gym2);

        gym3 = new Gym();
        gym3.setCity("Kinsetsu City");
        gym3.setType(PokemonType.ELECTRIC);
        gym3.setLeader(trainer3);
        gymDao.create(gym3);

        badge1 = new Badge();
        badge1.setGym(gym2);
        badge1.setTrainer(trainer1);
        badgeDao.create(badge1);

        badge2 = new Badge();
        badge2.setGym(gym1);
        badge2.setTrainer(trainer3);
        badgeDao.create(badge2);
    }

    @Test
    public void createBadge() {
        Badge badge = new Badge();
        badge.setGym(gym1);
        badge.setTrainer(trainer2);

        badgeDao.create(badge);

        Badge found = badgeDao.findById(badge.getId());
        Assert.assertEquals(badge, found);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createBadgeTrainerIsGymLeader() {
        Badge badge = new Badge();
        badge.setTrainer(trainer1);
        badge.setGym(gym1);

        badgeDao.create(badge);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createBadgeNoGym() {
        Badge badge = new Badge();
        badge.setTrainer(trainer1);

        badgeDao.create(badge);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createBadgeNoTrainer() {
        Badge badge = new Badge();
        badge.setGym(gym1);

        badgeDao.create(badge);
    }

    @Test
    public void removeBadge() {
        Long id = badge1.getId();
        badgeDao.remove(badge1);

        Collection<Badge> list = badgeDao.findAll();
        Assert.assertEquals(1, list.size());
        Assert.assertTrue(list.contains(badge2));

        Badge found = badgeDao.findById(id);
        Assert.assertNull(found);
    }

    @Test
    public void updateBadgeGym() {
        badge2.setGym(gym2);
        badgeDao.update(badge2);

        Badge updated = badgeDao.findById(badge2.getId());
        Assert.assertEquals(badge2, updated);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void updateBadgeNullGym() {
        badge2.setGym(null);
        badgeDao.update(badge2);
    }

    @Test
    public void updateBadgeTrainer() {
        badge2.setTrainer(trainer2);
        badgeDao.update(badge2);

        Badge updated = badgeDao.findById(badge2.getId());
        Assert.assertEquals(badge2, updated);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void updateBadgeNullTrainer() {
        badge2.setTrainer(null);
        badgeDao.update(badge2);
    }

    @Test
    public void findById() {
        Badge found = badgeDao.findById(badge2.getId());

        Assert.assertEquals(badge2, found);
    }

    @Test
    public void findByNonexistentId() {
        Badge found = badgeDao.findById(123L);

        Assert.assertNull(found);
    }

    @Test
    public void findByTrainer() {
        Collection<Badge> found = badgeDao.findByTrainer(trainer1);

        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(badge1));
    }

    @Test
    public void findByTrainerNoneExists() {
        Collection<Badge> found = badgeDao.findByTrainer(trainer2);

        Assert.assertEquals(0, found.size());
    }

    @Test
    public void findByGym() {
        Collection<Badge> found = badgeDao.findByGym(gym1);

        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(badge2));
    }

    @Test
    public void findByGymNoneExists() {
        Collection<Badge> found = badgeDao.findByGym(gym3);

        Assert.assertEquals(0, found.size());
    }

    @Test
    public void findAll() {
        Collection<Badge> found = badgeDao.findAll();

        Assert.assertEquals(2, found.size());
        Assert.assertTrue(found.contains(badge1));
        Assert.assertTrue(found.contains(badge2));
    }
}
