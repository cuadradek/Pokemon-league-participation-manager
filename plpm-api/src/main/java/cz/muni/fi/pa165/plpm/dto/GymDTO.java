package cz.muni.fi.pa165.plpm.dto;

import cz.muni.fi.pa165.plpm.enums.PokemonType;

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
}
