package cz.muni.fi.pa165.plpm.entity;

import cz.muni.fi.pa165.plpm.dao.constraints.TrainerIsNotGymLeader;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class that represents badge obtained by
 * defeating a Gym Leader in a Pokemon battle.
 *
 * @author Jakub Doczy
 */
@Entity
@TrainerIsNotGymLeader
public class Badge {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public Badge() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Badge)) return false;
        Badge badge = (Badge) other;
        return getTrainer().equals(badge.getTrainer()) &&
                getGym().equals(badge.getGym());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainer(), getGym());
    }

    @Override
    public String toString() {
        return String.format("Gym Badge id %d, from gym in city %s, obtained by trainer %s %s",
                id, gym.getCity(), trainer.getFirstName(), trainer.getLastName());
    }
}
