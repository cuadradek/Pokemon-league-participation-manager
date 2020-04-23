package cz.muni.fi.pa165.plpm.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 *
 * @author Jakub Doczy
 */
public class BadgeDTO {

    @NotNull
    private Long id;

    @NotNull
    private TrainerDTO trainer;

    @NotNull
    private GymDTO gym;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public GymDTO getGym() {
        return gym;
    }

    public void setGym(GymDTO gym) {
        this.gym = gym;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BadgeDTO)) return false;
        BadgeDTO otherBadgeDTO = (BadgeDTO) other;
        return Objects.equals(getTrainer(), otherBadgeDTO.getTrainer()) &&
                Objects.equals(getGym(), otherBadgeDTO.getGym());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainer(), getGym());
    }
}
