package cz.muni.fi.pa165.plpm.dto;

import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.enums.PokemonType;

import javax.validation.constraints.NotNull;

/**
 * @author Karolína Kolouchová
 */
public class GymCreateDTO {

    @NotNull
    private Long trainerId;

    @NotNull
    private String city;

    @NotNull
    private PokemonType type;

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
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
