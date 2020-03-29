package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;
import java.sql.Date;
import java.util.List;


/*
author: Veronika Loukotová
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TrainerDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TrainerDao trainerDao;

    private Trainer trainerJohn;
    private Trainer trainerPaul;
    private Trainer trainerRingo;

    @BeforeMethod
    private void setUp() {
        trainerJohn = new Trainer();
        trainerJohn.setFirstName("John");
        trainerJohn.setNickname("johny");
        trainerJohn.setLastName("Lennon");
        trainerJohn.setPassword("123");
        trainerJohn.setBirthDate(new Date(1940, 10, 9));

        trainerPaul = new Trainer();
        trainerPaul.setFirstName("Paul");
        trainerPaul.setNickname("paul");
        trainerPaul.setLastName("McCartney");
        trainerPaul.setPassword("123");
        trainerPaul.setBirthDate(new Date(1942, 6, 4));

        trainerRingo = new Trainer();
        trainerRingo.setFirstName("Ringo");
        trainerRingo.setNickname("ringo");
        trainerRingo.setLastName("Starr");
        trainerRingo.setPassword("123");
        trainerRingo.setBirthDate(new Date(1940, 7, 7));

        trainerDao.createTrainer(trainerJohn);
        trainerDao.createTrainer(trainerPaul);
        trainerDao.createTrainer(trainerRingo);
    }

    @Test
    private void createCorrectTrainer() {
        Trainer trainerGeorge = new Trainer();
        trainerGeorge.setFirstName("George");
        trainerGeorge.setNickname("george");
        trainerGeorge.setLastName("Harrison");
        trainerGeorge.setPassword("123");
        trainerGeorge.setBirthDate(new Date(1943, 2, 25));

        trainerDao.createTrainer(trainerGeorge);
        Trainer trainerFromDb = trainerDao.findTrainerById(trainerGeorge.getId());

        Assert.assertEquals(trainerFromDb, trainerGeorge);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    private void createNullTrainer() {
        trainerDao.createTrainer(null);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    private void createTrainerWithoutFirstName() {
        Trainer withouFirstName = new Trainer();
        withouFirstName.setLastName("Last");
        withouFirstName.setBirthDate(new Date(2020, 3, 27));

        trainerDao.createTrainer(withouFirstName);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    private void createTrainerWithoutLastName() {
        Trainer withoutLastName = new Trainer();
        withoutLastName.setFirstName("First");
        withoutLastName.setBirthDate(new Date(2000, 12, 24));

        trainerDao.createTrainer(withoutLastName);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    private void createTrainerWithoutBirthDate() {
        Trainer withoutBirthDate = new Trainer();
        withoutBirthDate.setFirstName("First");
        withoutBirthDate.setLastName("Last");

        trainerDao.createTrainer(withoutBirthDate);
    }

    @Test
    private void updateName() {
        String newName = "Johny";
        trainerJohn.setFirstName(newName);

        trainerDao.updateTrainer(trainerJohn);
        Trainer trainerFromDb = trainerDao.findTrainerById(trainerJohn.getId());

        Assert.assertEquals(trainerJohn, trainerFromDb);
        Assert.assertEquals(newName, trainerFromDb.getFirstName());
    }

    @Test
    private void updateLastName() {
        String lastName = "Last";
        trainerPaul.setLastName(lastName);

        trainerDao.updateTrainer(trainerPaul);
        Trainer trainerFromDb = trainerDao.findTrainerById(trainerPaul.getId());

        Assert.assertEquals(trainerPaul, trainerFromDb);
        Assert.assertEquals(lastName, trainerFromDb.getLastName());
    }

    @Test
    private void updateBirthDate() {
        Date newDate = new Date(2020, 3, 28);
        trainerRingo.setBirthDate(newDate);

        trainerDao.updateTrainer(trainerRingo);
        Trainer trainerFromDb = trainerDao.findTrainerById(trainerRingo.getId());

        Assert.assertEquals(trainerFromDb, trainerRingo);
        Assert.assertEquals(newDate, trainerFromDb.getBirthDate());
    }

    @Test
    private void updateLastNameToNull() {
        trainerRingo.setLastName(null);

        trainerDao.updateTrainer(trainerRingo);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    private void updateNullTrainer() {
        trainerDao.updateTrainer(null);
    }

    @Test
    private void delete() {
        List<Trainer> trainersBefore = trainerDao.findAllTrainers();

        trainerDao.deleteTrainer(trainerJohn);
        List<Trainer> trainersAfter = trainerDao.findAllTrainers();

        Assert.assertTrue(trainersBefore.contains(trainerJohn));
        Assert.assertFalse(trainersAfter.contains(trainerJohn));
    }

    @Test
    private void findTrainerByIdWithCorrectId() {
        Trainer ringo = trainerDao.findTrainerById(trainerRingo.getId());

        Assert.assertEquals(ringo, trainerRingo);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    private void findTrainerByIdWithNullId() {
        Trainer withNullId = trainerDao.findTrainerById(null);
    }

    @Test
    private void findTrainerByIdWithIncorrectId() {
        Trainer withIncorrectId = trainerDao.findTrainerById(new Long(1));

        Assert.assertNull(withIncorrectId);
    }

    @Test
    private void findTrainerByNameWithCorrectName() {
        List<Trainer> foundTrainers = trainerDao.findTrainerByFirstName(trainerRingo.getFirstName());

        Assert.assertTrue(foundTrainers.contains(trainerRingo));
        Assert.assertEquals(foundTrainers.size(), 1);
    }

    @Test
    private void findTrainerByNameWithIncorrectName() {
        List<Trainer> foundTrainers = trainerDao.findTrainerByFirstName("asdf");

        Assert.assertEquals(foundTrainers.size(), 0);
    }

    @Test
    private void findTrainerByLastNameWithCorrectLastName() {
        List<Trainer> foundTrainers = trainerDao.findTrainerByLastName(trainerJohn.getLastName());

        Assert.assertEquals(foundTrainers.size(), 1);
        Assert.assertTrue(foundTrainers.contains(trainerJohn));
    }

    @Test
    private void findTrainerByLastNameWithIncorrectLastName() {
        List<Trainer> foundTrainers = trainerDao.findTrainerByLastName("asdff");

        Assert.assertEquals(foundTrainers.size(), 0);
    }

    @Test
    private void findAllTrainers() {
        List<Trainer> foundTrainers = trainerDao.findAllTrainers();

        Assert.assertTrue(foundTrainers.contains(trainerJohn));
        Assert.assertTrue(foundTrainers.contains(trainerRingo));
        Assert.assertTrue(foundTrainers.contains(trainerPaul));
        Assert.assertEquals(foundTrainers.size(), 3);
    }

    @Test
    private void findTrainerByNicknameWithCorrectNickname() {
        Trainer foundTrainer = trainerDao.findTrainerByNickname(trainerJohn.getNickname());

        Assert.assertEquals(foundTrainer, trainerJohn);
    }

    @Test
    private void findTrainerByNicknameWithIncorrectNickname() {
        Trainer foundTrainer = trainerDao.findTrainerByNickname("incorrect");

        Assert.assertNull(foundTrainer);
    }
}
