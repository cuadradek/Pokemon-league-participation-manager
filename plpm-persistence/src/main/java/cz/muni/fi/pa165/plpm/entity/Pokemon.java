package cz.muni.fi.pa165.plpm.entity;

import cz.muni.fi.pa165.plpm.enums.PokemonType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Represents a Pokémon creature.
 *
 * @author Veronika Loukotová
 */

@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(unique = true)
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    private int level = 1;

    @Enumerated
    @NotNull
    private PokemonType type;

    public Pokemon() {
    }

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

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
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

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Pokemon)) return false;
        Pokemon pokemon = (Pokemon) o;

        return nickname.equals(pokemon.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nickname, trainer, level, type);
    }
}
