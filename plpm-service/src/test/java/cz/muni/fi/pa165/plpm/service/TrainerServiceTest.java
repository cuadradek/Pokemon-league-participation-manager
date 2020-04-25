package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.BadgeDao;
import cz.muni.fi.pa165.plpm.dao.GymDao;
import cz.muni.fi.pa165.plpm.dao.PokemonDao;
import cz.muni.fi.pa165.plpm.dao.TrainerDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import org.hibernate.service.spi.ServiceException;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import cz.muni.fi.pa165.plpm.resources.DefaultTrainers;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Jakub Doczy
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TrainerServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private BadgeDao badgeDao;

    @Mock
    private PokemonDao pokemonDao;

    @Mock
    private GymDao gymDao;

    @Autowired
    @InjectMocks
    TrainerService trainerService;

    private Trainer trainerAsh;
    private Trainer trainerGary;
    private Trainer trainerTracey;
    private Trainer newTrainer;

    private Gym pewterGym;
    private Pokemon ashesPikachu;
    private Badge ashesPewterBadge;

    @BeforeClass
    public void classSetup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void methodSetup(){
        trainerAsh = DefaultTrainers.getAsh();
        trainerGary = DefaultTrainers.getGary();
        trainerTracey = DefaultTrainers.getTracey();

        newTrainer = new Trainer();
        newTrainer.setNickname("Johny");
        newTrainer.setFirstName("John");
        newTrainer.setLastName("Smith");
        newTrainer.setPassword("passw0rd"); // unencrypted
        newTrainer.setAdmin(false);
        newTrainer.setNickname("Johny");
        newTrainer.setBirthDate(new Date());

        pewterGym = new Gym();
        pewterGym.setId(1L);
        pewterGym.setCity("Pewter City");
        pewterGym.setType(PokemonType.ROCK);
        pewterGym.setLeader(trainerTracey);

        ashesPikachu = new Pokemon();
        ashesPikachu.setTrainer(trainerAsh);
        ashesPikachu.setId(7L);
        ashesPikachu.setName("Pikachu");
        ashesPikachu.setNickname("Ash's Pikachu");
        ashesPikachu.setType(PokemonType.ELECTRIC);

        ashesPewterBadge = new Badge();
        ashesPewterBadge.setGym(pewterGym);
        ashesPewterBadge.setTrainer(trainerAsh);
        ashesPewterBadge.setId(11L);
    }

    @Test
    public void createTrainer() {
        when(trainerDao.findTrainerByNickname(newTrainer.getNickname())).thenReturn(null);
        trainerService.createTrainer(newTrainer);

        // assert password gets encrypted
        Assert.assertNotEquals(newTrainer, "passw0rd");
        // assert create is called
        verify(trainerDao).createTrainer(newTrainer);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void createOnExistingTrainerFails() {
        when(trainerDao.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);
        trainerService.createTrainer(trainerAsh);
    }

    // TODO: create user with invalid password? Or is it handled by facade layer?

    @Test
    public void authenticateSuccess() {
        when(trainerDao.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        Assert.assertTrue(trainerService.authenticate(trainerAsh, DefaultTrainers.getPlainPasswordAsh()));
    }

    @Test
    public void authenticateFail() {
        when(trainerDao.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);
        Assert.assertFalse(trainerService.authenticate(trainerAsh, "abc"));
    }

    @Test
    public void updateName() {
        Trainer changedTrainer = new Trainer();
        changedTrainer.setId(trainerAsh.getId());
        changedTrainer.setBirthDate(trainerAsh.getBirthDate());
        changedTrainer.setFirstName("Jessie");
        changedTrainer.setLastName("Musashi");
        changedTrainer.setNickname(trainerAsh.getNickname());
        changedTrainer.setPassword(trainerAsh.getPassword());

        when(trainerDao.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);
        when(trainerDao.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);

        trainerService.updateTrainerInfo(changedTrainer);

        verify(trainerDao).updateTrainer(trainerAsh);
        Assert.assertEquals(trainerAsh, changedTrainer);
    }

    @Test
    public void updateAllTrainerInfo() {
        Trainer changedTrainer = new Trainer();
        changedTrainer.setId(trainerAsh.getId());
        changedTrainer.setBirthDate(new Date(100));
        changedTrainer.setFirstName("Jessie");
        changedTrainer.setLastName("Musashi");
        changedTrainer.setNickname("Rocket");
        changedTrainer.setPassword(trainerAsh.getPassword());

        when(trainerDao.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);
        when(trainerDao.findTrainerByNickname("Rocket")).thenReturn(null);

        trainerService.updateTrainerInfo(changedTrainer);

        verify(trainerDao).updateTrainer(trainerAsh);
        Assert.assertEquals(trainerAsh, changedTrainer);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void updateInfoWithInvalidIdFails() {
        newTrainer.setId(-1L);

        when(trainerDao.findTrainerById(-1L)).thenReturn(null);
        when(trainerDao.findTrainerByNickname("Rocket")).thenReturn(null);

        trainerService.updateTrainerInfo(newTrainer);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void updateInfoWithConflictingNicknameFails() {
        Trainer changedTrainer = new Trainer();
        changedTrainer.setId(trainerAsh.getId());
        changedTrainer.setBirthDate(new Date(100));
        changedTrainer.setFirstName("Jessie");
        changedTrainer.setLastName("Musashi");
        changedTrainer.setNickname("Gary");
        changedTrainer.setPassword(trainerAsh.getPassword());

        when(trainerDao.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);
        when(trainerDao.findTrainerByNickname("Gary")).thenReturn(trainerGary);

        trainerService.updateTrainerInfo(newTrainer);
    }

    @Test
    public void changePassword() {
        String oldHashedPassword = trainerAsh.getPassword();
        String newPassword = "abc123";

        Assert.assertTrue(trainerService.changePassword(trainerAsh, DefaultTrainers.getPlainPasswordAsh(), newPassword));
        verify(trainerDao).updateTrainer(trainerAsh);
        // assert that password has changed
        Assert.assertNotEquals(trainerAsh.getPassword(), oldHashedPassword);
        // assert it is encrypted
        Assert.assertNotEquals(trainerAsh.getPassword(), newPassword);
        Assert.assertTrue(trainerService.authenticate(trainerAsh, newPassword));

    }

    @Test
    public void changePasswordFailsWhenGivenIncorrectPassword() {
        String oldHashedPassword = trainerAsh.getPassword();
        String newPassword = "abc123";

        Assert.assertFalse(trainerService.changePassword(trainerAsh, "-", newPassword));
        // assert password remains unchanged
        Assert.assertEquals(trainerAsh.getPassword(), oldHashedPassword);
    }

    @Test
    public void deleteTrainerSimple() {
        when(badgeDao.findByTrainer(trainerGary)).thenReturn(new ArrayList<>());
        when(pokemonDao.findPokemonsByTrainer(trainerGary)).thenReturn(new ArrayList<>());
        when(gymDao.findByTrainer(trainerGary)).thenReturn(null);

        trainerService.deleteTrainer(trainerGary);

        verify(trainerDao).deleteTrainer(trainerGary);
    }

    @Test
    public void deleteTrainerWithBadgeAndPokemon() {
        when(badgeDao.findByTrainer(trainerAsh)).thenReturn(asList(ashesPewterBadge));
        when(pokemonDao.findPokemonsByTrainer(trainerAsh)).thenReturn(asList(ashesPikachu));
        when(gymDao.findByTrainer(trainerAsh)).thenReturn(null);

        trainerService.deleteTrainer(trainerAsh);

        verify(trainerDao).deleteTrainer(trainerAsh);
        verify(badgeDao).remove(ashesPewterBadge);
        verify(pokemonDao).remove(ashesPikachu);
    }

    @Test
    public void deleteGymLeader() {
        when(badgeDao.findByTrainer(trainerTracey)).thenReturn(new ArrayList<>());
        when(pokemonDao.findPokemonsByTrainer(trainerTracey)).thenReturn(new ArrayList<>());
        when(gymDao.findByTrainer(trainerTracey)).thenReturn(pewterGym);

        trainerService.deleteTrainer(trainerTracey);

        verify(trainerDao).deleteTrainer(trainerTracey);
        verify(gymDao).remove(pewterGym);
    }

    // TODO: is this possible?
    @Test(expectedExceptions = ServiceException.class)
    public void deleteNonExistingTrainerFails() {
        newTrainer.setId(-1L);

        when(badgeDao.findByTrainer(newTrainer)).thenReturn(new ArrayList<>());
        when(pokemonDao.findPokemonsByTrainer(newTrainer)).thenReturn(new ArrayList<>());
        when(gymDao.findByTrainer(newTrainer)).thenReturn(null);

        trainerService.deleteTrainer(newTrainer);
    }

    @Test
    public void findTrainerById() {
        when(trainerService.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);
        Trainer returnedTrainer = trainerService.findTrainerById(trainerAsh.getId());
        Assert.assertEquals(returnedTrainer, trainerAsh);
    }

    // TODO: or maybe expect exception?
    @Test
    public void findTrainerByNonExistingId() {
        when(trainerService.findTrainerById(-1L)).thenReturn(null);
        Trainer returnedTrainer = trainerService.findTrainerById(-1L);
        Assert.assertNull(returnedTrainer);
    }

    @Test
    public void findTrainerByNickname() {
        when(trainerService.findTrainerByNickname(trainerAsh.getNickname())).thenReturn(trainerAsh);
        Trainer returnedTrainer = trainerService.findTrainerByNickname(trainerAsh.getNickname());
        Assert.assertEquals(returnedTrainer, trainerAsh);
    }

    @Test
    public void findTrainerByNonExistingNickname() {
        when(trainerService.findTrainerByNickname("Johny")).thenReturn(null);
        Trainer returnedTrainer = trainerService.findTrainerByNickname("Johny");
        Assert.assertNull(returnedTrainer);
    }

    @Test
    public void findTrainerByFirstName() {
        when(trainerService.findTrainerByFirstName(trainerAsh.getFirstName())).thenReturn(asList(trainerAsh));
        List<Trainer> returnedTrainers = trainerService.findTrainerByFirstName(trainerAsh.getFirstName());

        Assert.assertEquals(returnedTrainers, asList(trainerAsh));
    }

    @Test
    public void findMultipleTrainersByFirstName() {
        Trainer secondAsh = new Trainer();
        secondAsh.setId(69L);
        secondAsh.setNickname("Second Ash");
        secondAsh.setFirstName("Ash");
        secondAsh.setLastName("Two");
        secondAsh.setBirthDate(new Date(1));
        secondAsh.setPassword(DefaultTrainers.getPlainPasswordGary());

        when(trainerService.findTrainerByFirstName(trainerAsh.getFirstName())).thenReturn(asList(trainerAsh, secondAsh));
        List<Trainer> returnedTrainers = trainerService.findTrainerByFirstName(trainerAsh.getFirstName());

        Assert.assertEquals(returnedTrainers, asList(trainerAsh, secondAsh));
    }

    @Test
    public void findNoTrainersByFirstName() {
        when(trainerService.findTrainerByFirstName("Bill")).thenReturn(new ArrayList<>());
        List<Trainer> returnedTrainers = trainerService.findTrainerByFirstName("Bill");

        Assert.assertTrue(returnedTrainers.isEmpty());
    }

    @Test
    public void findTrainerByLastName() {
        when(trainerService.findTrainerByLastName(trainerAsh.getLastName())).thenReturn(asList(trainerAsh));
        List<Trainer> returnedTrainers = trainerService.findTrainerByLastName(trainerAsh.getLastName());

        Assert.assertEquals(returnedTrainers, asList(trainerAsh));
    }

    @Test
    public void findMultipleTrainersByLastName() {
        Trainer secondKetchum = new Trainer();
        secondKetchum.setId(69L);
        secondKetchum.setNickname("Second Ash");
        secondKetchum.setFirstName("Delia");
        secondKetchum.setLastName("Ketchum");
        secondKetchum.setBirthDate(new Date(1));
        secondKetchum.setPassword(DefaultTrainers.getPlainPasswordGary());

        when(trainerService.findTrainerByLastName(trainerAsh.getLastName())).thenReturn(asList(trainerAsh, secondKetchum));
        List<Trainer> returnedTrainers = trainerService.findTrainerByLastName(trainerAsh.getLastName());

        Assert.assertEquals(returnedTrainers, asList(trainerAsh, secondKetchum));
    }

    @Test
    public void findNoTrainersByLastName() {
        when(trainerService.findTrainerByLastName("Gates")).thenReturn(new ArrayList<>());
        List<Trainer> returnedTrainers = trainerService.findTrainerByLastName("Bill");

        Assert.assertTrue(returnedTrainers.isEmpty());
    }

    @Test
    public void findAllTrainers() {
        when(trainerService.findAllTrainers()).thenReturn(asList(trainerAsh, trainerGary, trainerTracey));
        List<Trainer> returnedTrainers = trainerService.findAllTrainers();

        Assert.assertEquals(returnedTrainers, asList(trainerAsh, trainerGary, trainerTracey));
    }

    @Test
    public void isAdminOnAdmin() {
        Trainer ashCopy = new Trainer();
        ashCopy.setId(trainerAsh.getId());
        ashCopy.setNickname(trainerAsh.getNickname());
        ashCopy.setPassword(trainerAsh.getPassword());

        when(trainerService.findTrainerById(trainerAsh.getId())).thenReturn(trainerAsh);

        Assert.assertTrue(trainerService.isAdmin(ashCopy));
    }

    @Test
    public void isAdminOnNonAdmin() {
        Trainer garyCopy = new Trainer();
        garyCopy.setId(trainerGary.getId());
        garyCopy.setNickname(trainerGary.getNickname());
        garyCopy.setPassword(trainerGary.getPassword());

        when(trainerService.findTrainerById(trainerGary.getId())).thenReturn(trainerGary);

        Assert.assertFalse(trainerService.isAdmin(garyCopy));
    }

    // TODO: is this possible?
    @Test(expectedExceptions = ServiceException.class)
    public void isAdminOnNonUser() {
        when(trainerService.findTrainerById(newTrainer.getId())).thenReturn(null);
        trainerService.isAdmin(newTrainer);
    }
}