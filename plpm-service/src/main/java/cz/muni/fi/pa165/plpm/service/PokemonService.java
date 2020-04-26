package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.List;

/**
 * @author: Veronika Loukotova
 */
public interface PokemonService {
    Pokemon createPokemon(Pokemon pokemon);

    void updatePokemonInfo(Pokemon pokemon);

    void deletePokemon(Pokemon pokemon);

    Pokemon findPokemonById(Long id);

    List<Pokemon> findPokemonByName(String name);

    List<Pokemon> findPokemonByNickname(String nickname);

    List<Pokemon> findPokemonByTrainer(Trainer trainer);

    List<Pokemon> findAllPokemons();
}
