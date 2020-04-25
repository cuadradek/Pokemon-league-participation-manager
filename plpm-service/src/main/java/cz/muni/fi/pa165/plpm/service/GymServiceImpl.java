package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.GymDao;
import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of Gym service
 *
 * @author Karolína Kolouchová
 */
@Service
public class GymServiceImpl implements GymService {

    @Autowired
    private GymDao gymDao;

    @Autowired
    private BadgeService badgeService;

    @Override
    public void createGym(Gym gym) {
        Gym foundGym = gymDao.findByTrainer(gym.getLeader());
        if (foundGym != null) {
            throw new IllegalArgumentException("Trainer is already leader of another gym.");
        }
        gymDao.create(gym);
    }

    @Override
    public void removeGym(Gym gym) {
        List<Badge> gymBadges = badgeService.getBadgesByGym(gym);
        for (Badge badge : gymBadges) {
            badgeService.deleteBadge(badge);
        }
        gymDao.remove(gym);
    }

    @Override
    public void updateGym(Gym gym) {
        Gym foundGym = gymDao.findByTrainer(gym.getLeader());
        if (foundGym != null && !foundGym.getId().equals(gym.getId())) {
            throw new IllegalArgumentException("New gym leader is already leader of another gym.");
        }
        gymDao.update(gym);
    }

    @Override
    public Gym findGymById(Long id) {
        return gymDao.findById(id);
    }

    @Override
    public Gym findGymByTrainer(Trainer trainer) {
        return gymDao.findByTrainer(trainer);
    }

    @Override
    public List<Gym> findGymsByCity(String city) {
        return gymDao.findByCity(city);
    }

    @Override
    public List<Gym> findAllGyms() {
        return gymDao.findAll();
    }
}
