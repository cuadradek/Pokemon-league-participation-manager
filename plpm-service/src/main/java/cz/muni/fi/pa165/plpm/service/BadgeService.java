package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.entity.Badge;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.List;

/**
 * An interface that defines a service access to the {@link Badge} entity.
 *
 * @author Jakub Doczy
 */
public interface BadgeService {

    void createBadge(Badge badge);

    void deleteBadge(Badge badge);

    List<Badge> getBadgesByTrainer(Trainer trainer);

    List<Badge> getBadgesByGym(Gym gym);

    List<Badge> getAllBadges();
}
