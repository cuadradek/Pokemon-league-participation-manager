package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.plpm.dto.BadgeDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.BadgeService;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.GymService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link BadgeFacade}.
 *
 * @author Jakub Doczy
 */
@Service
@Transactional
public class BadgeFacadeImpl implements BadgeFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private GymService gymService;

    @Override
    public Long createBadge(BadgeCreateDTO badgeCreateDTO) {
        Trainer trainer = trainerService.findTrainerById(badgeCreateDTO.getTrainerId());
        if (trainer == null) {
            throw new IllegalStateException("Failed to create badge: did not find trainer with id " +
                    badgeCreateDTO.getTrainerId().toString());
        }

        Gym gym = gymService.findGymById(badgeCreateDTO.getGymId());
        if (gym == null) {
            throw new IllegalStateException("Failed to create badge: did not find gym with id " +
                    badgeCreateDTO.getGymId().toString());
        }

        Badge badge = new Badge();
        badge.setTrainer(trainer);
        badge.setGym(gym);

        try {
            badgeService.createBadge(badge);
        } catch (PlpmServiceException serviceException) {
            throw new IllegalArgumentException("Failed to create badge " + badge.toString(), serviceException);
        }

        return badge.getId();
    }

    @Override
    public BadgeDTO getBadgeById(Long id) {
        Badge order = badgeService.getBadgeById(id);
        return (order == null) ? null : beanMappingService.mapTo(order, BadgeDTO.class);
    }

    @Override
    public List<BadgeDTO> getBadgesByTrainerId(Long trainerId) {
        Trainer trainer = trainerService.findTrainerById(trainerId);

        if (trainer == null) {
            throw new IllegalArgumentException("Cannot get badges of trainer that does not exits. Provided id: "
                + trainerId.toString());
        }

        List<Badge> badges = badgeService.getBadgesByTrainer(trainer);
        return beanMappingService.mapTo(badges,
                BadgeDTO.class);
    }

    @Override
    public List<GymDTO> getBeatenGyms(Long trainerId) {
        Trainer trainer = trainerService.findTrainerById(trainerId);

        if (trainer == null) {
            throw new IllegalArgumentException("Cannot get beaten gyms for trainer that does not exits. Provided id: "
                    + trainerId.toString());
        }

        Set<Gym> gyms;
        try {
            gyms = badgeService.getBeatenGyms(trainer);
        } catch (PlpmServiceException serviceException) {
           throw new IllegalArgumentException("Badge service failed to find beaten gyms", serviceException);
        }

        return beanMappingService.mapTo(gyms,
                GymDTO.class);
    }

    @Override
    public List<BadgeDTO> getBadgesByGymId(Long gymId) {
        Gym gym = gymService.findGymById(gymId);

        if (gym == null) {
            throw new IllegalArgumentException("Cannot get badges of gym that does not exits. Provided id: "
                    + gymId.toString());
        }

        List<Badge> badges = badgeService.getBadgesByGym(gym);
        return beanMappingService.mapTo(badges,
                BadgeDTO.class);
    }

    @Override
    public List<BadgeDTO> getAllBadges() {
        return beanMappingService.mapTo(badgeService.getAllBadges(),
                BadgeDTO.class);
    }
}
