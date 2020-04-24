package cz.muni.fi.pa165.plpm.dto;

import cz.muni.fi.pa165.plpm.enums.PokemonType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author: Veronika Loukotova
 */
public class PokemonCreateDTO {
    @NotNull
    @Size(max=40)
    String name;

    @NotNull
    @Size(max=40)
    String nickname;

    @NotNull
    PokemonType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
        if (!(o instanceof PokemonCreateDTO)) return false;
        PokemonCreateDTO that = (PokemonCreateDTO) o;
        return Objects.equals(nickname, that.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

}
