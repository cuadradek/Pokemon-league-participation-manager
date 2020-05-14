package cz.muni.fi.pa165.plpm.entity;

import cz.muni.fi.pa165.plpm.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class representing Gym
 *
 * @author Karolína Kolouchová
 */
@Entity
public class Gym {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "leader_id", unique = true)
    @NotNull
    private Trainer leader;

    @NotNull
    private String city;

    @Enumerated
    @NotNull
    private PokemonType type;

    public Gym() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trainer getLeader() {
        return leader;
    }

    public void setLeader(Trainer leader) {
        this.leader = leader;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Gym)) return false;
        Gym gym = (Gym) o;
        return city.equals(gym.city) &&
                type == gym.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, type);
    }

    public static class Builder {
        private Trainer leader;
        private String city;
        private PokemonType type;

        public Builder leader(Trainer leader) {
            this.leader = leader;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder type(PokemonType type) {
            this.type = type;
            return this;
        }

        public Gym build() {
            return new Gym(this);
        }
    }

    private Gym(Builder builder) {
        if (builder.leader == null || builder.city == null ||
                builder.type == null) {
            throw new IllegalArgumentException("Cannot build gym entity with missing required parameter.");
        }

        this.leader = builder.leader;
        this.city = builder.city;
        this.type = builder.type;
    }
}
