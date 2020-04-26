package cz.muni.fi.pa165.plpm.facade;

import cz.muni.fi.pa165.plpm.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.plpm.dto.BadgeDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.BadgeService;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.GymService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacadeImpl;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

/**
 * @author Radoslav Cerhak
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BadgeFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private BadgeService badgeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private GymService gymService;

    @InjectMocks
    private BadgeFacade badgeFacade = new BadgeFacadeImpl();

    private GymDTO gymDTO;
    private Gym gym;
    private TrainerDTO trainerDTO;
    private Trainer trainer;
    private BadgeDTO badgeDTO;
    private Badge badge;

    private List<Badge> badgeList;
    private List<BadgeDTO> badgeDTOList;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void before() {
        trainerDTO = new TrainerDTO();
        trainerDTO.setId(1L);
        trainerDTO.setNickname("roxy");
        trainerDTO.setFirstName("Roxanne");
        trainerDTO.setLastName("Tsutsuji");
        trainerDTO.setBirthDate(new Date());
        trainerDTO.setPassword("123");

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setNickname("roxy");
        trainer.setFirstName("Roxanne");
        trainer.setLastName("Tsutsuji");
        trainer.setBirthDate(new Date());
        trainer.setPassword("123");

        gym = new Gym();
        gym.setId(1L);
        gym.setCity("Touka City");
        gym.setType(PokemonType.ROCK);
        gym.setLeader(trainer);

        gymDTO = new GymDTO();
        gymDTO.setId(1L);
        gymDTO.setCity("Touka City");
        gymDTO.setType(PokemonType.ROCK);
        gymDTO.setLeader(trainerDTO);

        badgeDTO = new BadgeDTO();
        badgeDTO.setId(1L);
        badgeDTO.setGym(gymDTO);
        badgeDTO.setTrainer(trainerDTO);

        badge = new Badge();
        badge.setId(1L);
        badge.setGym(gym);
        badge.setTrainer(trainer);

        badgeList = Collections.singletonList(badge);
        badgeDTOList = Collections.singletonList(badgeDTO);

        when(badgeService.getBadgeById(1L)).thenReturn(badge);
        when(beanMappingService.mapTo(badge, BadgeDTO.class)).thenReturn(badgeDTO);
        when(beanMappingService.mapTo(badgeList, BadgeDTO.class)).thenReturn(badgeDTOList);

        when(trainerService.findTrainerById(1L)).thenReturn(trainer);
        when(gymService.findGymById(1L)).thenReturn(gym);

    }

    @Test
    public void getBadgeById() {
        BadgeDTO b = badgeFacade.getBadgeById(1L);

        Assert.assertEquals(badgeDTO, b);
    }

    @Test
    public void getAllBadges() {
        when(badgeService.getAllBadges()).thenReturn(badgeList);

        List<BadgeDTO> badgeDTOList = badgeFacade.getAllBadges();

        Assert.assertEquals(badgeDTOList.size(), 1);
        Assert.assertTrue(badgeDTOList.contains(badgeDTO));
    }

    @Test
    public void getBadgesByGymId() {
        when(badgeService.getBadgesByGym(gym)).thenReturn(badgeList);

        List<BadgeDTO> badges = badgeFacade.getBadgesByGymId(1L);

        Assert.assertEquals(badges.size(), 1);
        Assert.assertTrue(badges.contains(badgeDTO));
    }

    @Test
    public void getBadgesByTrainerId() {
        when(badgeService.getBadgesByTrainer(trainer)).thenReturn(badgeList);

        List<BadgeDTO> badges = badgeFacade.getBadgesByTrainerId(1L);

        Assert.assertEquals(badges.size(), 1);
        Assert.assertTrue(badges.contains(badgeDTO));
    }

    @Test
    public void createBadge() {
        BadgeCreateDTO badgeCreateDTO = new BadgeCreateDTO();
        badgeCreateDTO.setGym(1L);
        badgeCreateDTO.setTrainerId(1L);

        Badge badge = new Badge();
        badge.setTrainer(trainer);
        badge.setGym(gym);

        doAnswer(invocation -> {
            Badge b = invocation.getArgument(0);
            b.setId(2L);
            return null;
        }).when(badgeService).createBadge(badge);

        Long id = badgeFacade.createBadge(badgeCreateDTO);

        Assert.assertEquals((long) id, 2L);
    }

    @Test
    public void getBeatenGyms() {
        Set<Gym> gyms = new HashSet<>(Collections.singletonList(gym));
        when(badgeService.getBeatenGyms(trainer)).thenReturn(gyms);
        when(beanMappingService.mapTo(gyms, GymDTO.class)).thenReturn(Collections.singletonList(gymDTO));

        List<GymDTO> beatenGyms = badgeFacade.getBeatenGyms(1L);
        Assert.assertEquals(beatenGyms.size(), 1);
        Assert.assertTrue(beatenGyms.contains(gymDTO));
    }
}
