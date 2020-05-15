package cz.muni.fi.pa165.plpm.sampledata;


import cz.muni.fi.pa165.plpm.dao.BadgeDao;
import cz.muni.fi.pa165.plpm.dao.GymDao;
import cz.muni.fi.pa165.plpm.dao.PokemonDao;
import cz.muni.fi.pa165.plpm.dao.TrainerDao;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * Tests data loading.
 *
 * @author Jakub Doczy
 */
@ContextConfiguration(classes = {SampleDataConfiguration.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SampleDataLoadingFacadeTest extends AbstractTestNGSpringContextTests {
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeTest.class);

    @Autowired
    public TrainerDao trainerDao;

    @Autowired
    public PokemonDao pokemonDao;

    @Autowired
    public GymDao gymDao;

    @Autowired
    public BadgeDao badgeDao;

    @Autowired
    public TrainerService trainerService;

    @Autowired
    public SampleDataLoadingFacade sampleDataLoadingFacade;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createSampleData() throws IOException {
        log.debug("starting test");

        Assert.assertTrue(trainerDao.findAllTrainers().size() > 0, "no trainers");
        Assert.assertTrue(pokemonDao.findAll().size() > 0, "no pokemons");
        Assert.assertTrue(gymDao.findAll().size() > 0, "no gyms");
        Assert.assertTrue(badgeDao.findAll().size() > 0, "no badges");

        Trainer admin = trainerService.findAllTrainers().stream().filter(trainerService::isAdmin).findFirst().get();
        Assert.assertTrue(trainerService.authenticate(admin, "admin"));
    }
}
