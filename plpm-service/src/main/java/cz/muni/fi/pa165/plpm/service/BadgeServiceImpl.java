package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.BadgeDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link BadgeService}.
 *
 * @author Jakub Doczy
 */
@Service
public class BadgeServiceImpl implements BadgeService {

    // get beaten / unbeaten gyms?

    @Autowired
    private BadgeDao badgeDao;

    @Override
    public void createBadge(Badge badge) {
        badgeDao.create(badge);
    }

    @Override
    public void deleteBadge(Badge badge) {
        badgeDao.remove(badge);
    }

    @Override
    public List<Badge> getBadgesByTrainer(Trainer trainer) {
        return badgeDao.findByTrainer(trainer);
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
