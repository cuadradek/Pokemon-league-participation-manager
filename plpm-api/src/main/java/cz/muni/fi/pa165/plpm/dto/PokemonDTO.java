package cz.muni.fi.pa165.plpm.dto;

import cz.muni.fi.pa165.plpm.enums.PokemonType;

import java.util.Objects;

/**
 * @author: Veronika Loukotova
 */
public class PokemonDTO {
    private Long id;

    private String name;

    private String nickname;

    private TrainerDTO trainer;

    private int level = 1;

    private PokemonType type;

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

    public TrainerDTO getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerDTO trainer) {
        this.trainer = trainer;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof PokemonDTO)) return false;
        PokemonDTO pokemon = (PokemonDTO) o;

        return Objects.equals(nickname, pokemon.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }
}
