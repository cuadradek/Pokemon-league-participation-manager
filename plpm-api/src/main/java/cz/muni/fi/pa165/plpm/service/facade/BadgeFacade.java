package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.plpm.dto.BadgeDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Jakub Doczy
 */
public interface BadgeFacade {

    Long createBadge(BadgeCreateDTO badgeCreateDTO);

    BadgeDTO getBadgeById(Long id);

    List<BadgeDTO> getBadgesByTrainerId(Long trainerId);

    List<GymDTO> getBeatenGyms(Long trainerId);

    List<BadgeDTO> getBadgesByGymId(Long gymId);

    List<BadgeDTO> getAllBadges();
}
