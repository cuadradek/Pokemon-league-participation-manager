package cz.muni.fi.pa165.plpm.dto;

import cz.muni.fi.pa165.plpm.enums.PokemonType;

import java.util.Objects;

/**
 * @author Karolína Kolouchová
 */
public class GymDTO {

    private Long id;

    private TrainerDTO leader;

    private String city;

    private PokemonType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainerDTO getLeader() {
        return leader;
    }

    public void setLeader(TrainerDTO leader) {
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
        if (!(o instanceof GymDTO)) return false;
        GymDTO gym = (GymDTO) o;
        return Objects.equals(leader, gym.getLeader()) && Objects.equals(city, gym.getCity())
                && type == gym.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(leader, city, type);
    }
}
