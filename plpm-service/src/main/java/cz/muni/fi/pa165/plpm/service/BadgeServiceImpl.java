package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.BadgeDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of {@link BadgeService}.
 *
 * @author Jakub Doczy
 */
@Service
public class BadgeServiceImpl implements BadgeService {

    @Autowired
    private BadgeDao badgeDao;

    //@Autowired
    //private GymService gymService;

    @Override
    public void createBadge(Badge badge) {
        badgeDao.create(badge);
    }

    @Override
    public void deleteBadge(Badge badge) {
        badgeDao.remove(badge);
    }

    @Override
    public Badge getBadgeById(Long id) {
        return badgeDao.findById(id);
    }

    @Override
    public List<Badge> getBadgesByTrainer(Trainer trainer) {
        return badgeDao.findByTrainer(trainer);
    }

    @Override
    public Set<Gym> getBeatenGyms(Trainer trainer) {
        List<Badge> badges = getBadgesByTrainer(trainer);
        Set<Gym> gyms = new HashSet<>();

        for (Badge badge : badges) {
            Gym gym = badge.getGym();
            if (gym.getLeader().equals(trainer)) {
                throw new ServiceException("Trainer " + trainer.toString() +
                        " has received a badge from his own gym: " + gym.toString());
            }

            if (gyms.contains(badge.getGym())) {
                throw new ServiceException("Trainer " + trainer.toString() +
                        " has received 2 or more badges from the same gym: " + gym.toString());
            }
            gyms.add(gym);
        }

        //Gym trainersGym = gymService.findByTrainer(trainer);
        Gym trainersGym = null;
        if (trainersGym != null) {
            gyms.add(trainersGym);
        }

        return gyms;
    }

    @Override
    public List<Badge> getBadgesByGym(Gym gym) {
        return badgeDao.findByGym(gym);
    }

    @Override
    public List<Badge> getAllBadges() {
        return badgeDao.findAll();
    }
}
