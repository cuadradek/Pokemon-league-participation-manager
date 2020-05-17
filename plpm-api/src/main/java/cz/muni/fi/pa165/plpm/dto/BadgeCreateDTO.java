package cz.muni.fi.pa165.plpm.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 *
 * @author Jakub Doczy
 */
public class BadgeCreateDTO {

    @NotNull
    private Long trainerId;

    @NotNull
    private Long gymId;

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BadgeCreateDTO)) return false;
        BadgeCreateDTO otherBadgeCreateDTO = (BadgeCreateDTO) other;
        return Objects.equals(getTrainerId(), otherBadgeCreateDTO.getTrainerId()) &&
                Objects.equals(getGymId(), otherBadgeCreateDTO.getGymId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainerId(), getGymId());
    }
}
