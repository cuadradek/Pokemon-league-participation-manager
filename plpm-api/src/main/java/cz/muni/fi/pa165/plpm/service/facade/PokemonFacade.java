package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.*;

import java.util.List;

/**
 * @author: Veronika Loukotova
 */
public interface PokemonFacade {
    Long createPokemon(PokemonCreateDTO pokemonCreateDTO);

    void changeTrainer(PokemonChangeTrainerDTO pokemonChangeTrainierDTO);

    void changeLevel(PokemonChangeLevelDTO pokemonChangeLevelDTO);

    void deletePokemon(Long id);

    PokemonDTO getPokemonById(Long id);

    List<PokemonDTO> getPokemonByName(String name);

    List<PokemonDTO> getPokemonByNickname(String nickname);

    List<PokemonDTO> getPokemonByTrainer(TrainerDTO trainerDTO);

    List<PokemonDTO> getAllPokemons();
}
