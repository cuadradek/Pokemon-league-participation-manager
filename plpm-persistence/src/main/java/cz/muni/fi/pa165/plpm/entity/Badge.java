package cz.muni.fi.pa165.plpm.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class that represents badge obtained by
 * defeating a Gym Leader in a Pokemon battle.
 *
 * @author Jakub Doczy
 */
@Entity
public class Badge {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    public Badge() {
    }

    public Long getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Badge badge = (Badge) o;
        return Objects.equals(getTrainer(), badge.getTrainer()) &&
                Objects.equals(getGym(), badge.getGym());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTrainer(), getGym());
    }

    @Override
    public String toString() {
        return String.format("Gym Badge id %d, from gym %s, obtained by trainer %s %s",
                id, "placeholder", trainer.getFirstName(), trainer.getLastName());
    }
}
