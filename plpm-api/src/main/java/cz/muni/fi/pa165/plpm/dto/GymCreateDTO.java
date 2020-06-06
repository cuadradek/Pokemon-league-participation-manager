package cz.muni.fi.pa165.plpm.dto;

import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.enums.PokemonType;

import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof GymCreateDTO)) return false;
        GymCreateDTO gym = (GymCreateDTO) o;
        return Objects.equals(trainerId, gym.getTrainerId()) && Objects.equals(city, gym.getCity())
                && type == gym.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainerId, city, type);
    }
}
