package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Date;

/**
 * @author Jakub Doczy
 */
@ContextConfiguration(classes=PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class GymDaoTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private GymDao gymDao;

    @Autowired
    private TrainerDao trainerDao;

    private Gym pewterGym;
    private Gym kazGym;
    private Gym yasGym;

    private Trainer pewterLeader;

    // Trainer that is not a leader in any created gym
    private Trainer unassignedTrainer;

    @BeforeMethod
    public void setup() {
        pewterLeader = new Trainer();
        pewterLeader.setNickname("brockyy");
        pewterLeader.setFirstName("Brock");
        pewterLeader.setLastName("Takeshi");
        pewterLeader.setPassword("123");
        pewterLeader.setBirthDate(new Date());

        pewterGym = new Gym();
        pewterGym.setLeader(pewterLeader);
        pewterGym.setCity("Pewter City");
        pewterGym.setType(PokemonType.ROCK);

        Trainer kazLeader = new Trainer();
        kazLeader.setNickname("kkaz");
        kazLeader.setFirstName("Kaz");
        kazLeader.setLastName("Otsuka");
        kazLeader.setPassword("123");
        kazLeader.setBirthDate(new Date());

        kazGym = new Gym();
        kazGym.setLeader(kazLeader);
        kazGym.setCity("Dark City");
        kazGym.setType(PokemonType.ELECTRIC);

        Trainer yasLeader = new Trainer();
        yasLeader.setNickname("yas");
        yasLeader.setFirstName("Yas");
        yasLeader.setLastName("Otsuka");
        yasLeader.setPassword("123");
        yasLeader.setBirthDate(new Date());

        yasGym = new Gym();
        yasGym.setLeader(yasLeader);
        yasGym.setCity("Dark City");
        yasGym.setType(PokemonType.BUG);

        unassignedTrainer = new Trainer();
        unassignedTrainer.setNickname("mist");
        unassignedTrainer.setFirstName("Misty");
        unassignedTrainer.setLastName("Kasumi");
        unassignedTrainer.setPassword("123");
        unassignedTrainer.setBirthDate(new Date());

        trainerDao.createTrainer(pewterLeader);
        trainerDao.createTrainer(yasLeader);
        trainerDao.createTrainer(kazLeader);
        trainerDao.createTrainer(unassignedTrainer);

        gymDao.create(pewterGym);
        gymDao.create(kazGym);
        gymDao.create(yasGym);
    }

    @Test
    public void createNewGym() {
        Gym ceruleanGym = new Gym();
        ceruleanGym.setLeader(unassignedTrainer);
        ceruleanGym.setCity("Cerulean City");
        ceruleanGym.setType(PokemonType.WATER);

        gymDao.create(ceruleanGym);

        Gym foundGym = gymDao.findById(ceruleanGym.getId());
        Assert.assertEquals(ceruleanGym, foundGym);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createGymWithNoLeaderFails() {
        Gym ceruleanGym = new Gym();
        ceruleanGym.setCity("Cerulean City");
        ceruleanGym.setType(PokemonType.WATER);
        gymDao.create(ceruleanGym);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createGymWithNoTypeFails() {
        Gym ceruleanGym = new Gym();
        ceruleanGym.setCity("Cerulean City");
        ceruleanGym.setLeader(unassignedTrainer);
        gymDao.create(ceruleanGym);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createGymWithNoCityFails() {
        Gym ceruleanGym = new Gym();
        ceruleanGym.setLeader(unassignedTrainer);
        ceruleanGym.setType(PokemonType.WATER);
        gymDao.create(ceruleanGym);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createGymDuplicateFails() {
        Gym duplicatedYasGym = new Gym();
        duplicatedYasGym.setLeader(yasGym.getLeader());
        duplicatedYasGym.setCity(yasGym.getCity());
        duplicatedYasGym.setType(yasGym.getType());
        gymDao.create(duplicatedYasGym);
    }

    @Test
    public void findByExistingId() {
        Gym foundGym = gymDao.findById(pewterGym.getId());
        Assert.assertEquals(pewterGym, foundGym);
    }

    @Test
    public void findByNotExistingId() {
        long id = 1337;
        // ensure that no item with given id exist
        while (id == pewterGym.getId() || id == kazGym.getId() || id == yasGym.getId()) {
            id++;
        }
        Gym foundGym = gymDao.findById(id);
        Assert.assertNull(foundGym);
    }

    @Test
    public void removeExistingGym() {
        long kazGymId = kazGym.getId();
        gymDao.remove(kazGym);

        // gym with given id no longer exist
        Gym foundGym = gymDao.findById(kazGymId);
        Assert.assertNull(foundGym);

        // all other gyms remain
        Collection<Gym> gyms = gymDao.findAll();
        Assert.assertEquals(gyms.size(), 2);

        for (Gym gym : gyms) {
            Assert.assertTrue(gym.equals(yasGym) || gym.equals(pewterGym));
        }
    }

    @Test
    public void removeNotExitingGym() {
        long initialNumberOfGyms = gymDao.findAll().size();

        Gym ceruleanGym = new Gym();
        ceruleanGym.setLeader(unassignedTrainer);
        ceruleanGym.setCity("Cerulean City");
        ceruleanGym.setType(PokemonType.WATER);

        gymDao.remove(ceruleanGym);
        long finishingNumberOfGyms = gymDao.findAll().size();
        Assert.assertEquals(initialNumberOfGyms, finishingNumberOfGyms);
    }

    @Test
    public void removeAllGyms() {
        gymDao.remove(pewterGym);
        gymDao.remove(kazGym);
        gymDao.remove(yasGym);

        Collection<Gym> allGyms = gymDao.findAll();
        Assert.assertTrue(allGyms.isEmpty());
    }

    @Test
    public void updateGymLeader() {
        pewterGym.setLeader(unassignedTrainer);
        gymDao.update(pewterGym);

        Gym foundGym = gymDao.findById(pewterGym.getId());
        Assert.assertEquals(pewterGym, foundGym);
    }

    @Test
    public void updateGymType() {
        pewterGym.setType(PokemonType.WATER);
        gymDao.update(pewterGym);

        Gym foundGym = gymDao.findById(pewterGym.getId());
        Assert.assertEquals(pewterGym, foundGym);
    }

    @Test
    public void updateGymCity() {
        pewterGym.setCity("Brno");
        gymDao.update(pewterGym);

        Gym foundGym = gymDao.findById(pewterGym.getId());
        Assert.assertEquals(pewterGym, foundGym);
    }

    @Test(expectedExceptions = InvalidDataAccessApiUsageException.class)
    public void updateRemovedGymFails() {
        gymDao.remove(pewterGym);
        gymDao.update(pewterGym);
    }

    @Test
    public void findByCityWithOneExistingGym() {
        Collection<Gym> foundGyms = gymDao.findByCity("Pewter City");
        Assert.assertEquals(1, foundGyms.size());
        Assert.assertTrue(foundGyms.contains(pewterGym));
    }

    @Test
    public void findByCityWithTwoExistingGyms() {
        Collection<Gym> foundGyms = gymDao.findByCity("Dark City");
        Assert.assertEquals(2, foundGyms.size());
        Assert.assertTrue(foundGyms.contains(kazGym));
        Assert.assertTrue(foundGyms.contains(yasGym));
    }

    @Test
    public void findByCityWithNoExistingGym() {
        Collection<Gym> foundGyms = gymDao.findByCity("Brno");
        Assert.assertTrue(foundGyms.isEmpty());
    }

    @Test
    public void findByNullCity() {
        Collection<Gym> foundGyms = gymDao.findByCity(null);
        Assert.assertTrue(foundGyms.isEmpty());
    }

    @Test
    public void findByExistingLeader() {
        Gym foundGym = gymDao.findByTrainer(pewterLeader);
        Assert.assertEquals(pewterGym, foundGym);
    }

    @Test
    public void findByTrainerThatIsNotALeader() {
        Gym foundGym = gymDao.findByTrainer(unassignedTrainer);
        Assert.assertNull(foundGym);
    }

    @Test
    public void findByNullTrainer() {
        Gym foundGym = gymDao.findByTrainer(null);
        Assert.assertNull(foundGym);
    }

    @Test
    public void findAllGyms() {
        Collection<Gym> allGyms = gymDao.findAll();
        Assert.assertEquals(allGyms.size(), 3);
        Assert.assertTrue(allGyms.contains(pewterGym));
        Assert.assertTrue(allGyms.contains(kazGym));
        Assert.assertTrue(allGyms.contains(yasGym));
    }


}
