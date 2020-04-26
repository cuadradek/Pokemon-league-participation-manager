package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;

import java.util.List;

/**
 * @author: Veronika Loukotova
 */
public interface PokemonFacade {
    Long createPokemon(PokemonCreateDTO pokemonCreateDTO) throws PlpmServiceException;

    void changeTrainer(PokemonChangeTrainerDTO pokemonChangeTrainierDTO) throws PlpmServiceException;

    void changeLevel(PokemonChangeLevelDTO pokemonChangeLevelDTO) throws PlpmServiceException;

    void deletePokemon(Long id);

    PokemonDTO getPokemonById(Long id);

    List<PokemonDTO> getPokemonByName(String name);

    List<PokemonDTO> getPokemonByNickname(String nickname);

    List<PokemonDTO> getPokemonByTrainer(TrainerDTO trainerDTO);

    List<PokemonDTO> getAllPokemons();
}
