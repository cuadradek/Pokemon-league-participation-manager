package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;

import java.util.List;
import java.util.Set;

/**
 * An interface that defines a service access to the {@link Badge} entity.
 *
 * @author Jakub Doczy
 */
public interface BadgeService {

    void createBadge(Badge badge) throws PlpmServiceException;

    void deleteBadge(Badge badge);

    Badge getBadgeById(Long id);

    List<Badge> getBadgesByTrainer(Trainer trainer);

    Set<Gym> getBeatenGyms(Trainer trainer) throws PlpmServiceException;

    List<Badge> getBadgesByGym(Gym gym);

    List<Badge> getAllBadges();
}
