package cz.muni.fi.pa165.plpm.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author: Veronika Loukotova
 */
public class PokemonChangeTrainerDTO {
    @NotNull
    private Long id;

    @NotNull
    private TrainerDTO trainer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainerDTO getTrainer() { return trainer; }

    public void setTrainer() { this.trainer = trainer; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokemonChangeTrainerDTO)) return false;
        PokemonChangeTrainerDTO that = (PokemonChangeTrainerDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
