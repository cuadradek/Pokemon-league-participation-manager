package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.Collection;

/**
 * An interface that defines a service access to the {@link Badge} entity.
 *
 * @author Jakub Doczy
 */
public interface BadgeService {

    void createBadge(Badge badge);

    void deleteBadge(Badge badge);

    Collection<Badge> getBadgesByTrainer(Trainer trainer);

    Collection<Badge> getBadgesByGym(Gym gym);

    Collection<Badge> getAllBadges();
}
