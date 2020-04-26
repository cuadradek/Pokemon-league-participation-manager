package cz.muni.fi.pa165.plpm.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author: Veronika Loukotova
 */
public class PokemonChangeLevelDTO {
    @NotNull
    Long id;

    @NotNull
    int level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokemonChangeLevelDTO)) return false;
        PokemonChangeLevelDTO that = (PokemonChangeLevelDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
