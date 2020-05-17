package cz.muni.fi.pa165.plpm.service.facade;

import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.entity.Gym;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.PokemonService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Veronika Loukotova
 */
@Service
@Transactional
public class PokemonFacadeImpl implements PokemonFacade {
    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public Long createPokemon(PokemonCreateDTO pokemonCreateDTO) {
        return pokemonService.createPokemon(beanMappingService
                .mapTo(pokemonCreateDTO, Pokemon.class)).getId();
    }

    @Override
    public void changeTrainer(PokemonChangeTrainerDTO pokemonChangeTrainerDTO) {
        Pokemon pokemon = pokemonService.findPokemonById(pokemonChangeTrainerDTO.getId());
        if (pokemon == null) {
            throw new PlpmServiceException("Pokemon with id " + pokemonChangeTrainerDTO.getId() + " doesn't exist.");
        }
        pokemon.setTrainer(beanMappingService.mapTo(pokemonChangeTrainerDTO.getTrainer(), Trainer.class));
        pokemonService.updatePokemonInfo(pokemon);
    }

    @Override
    public void changeLevel(PokemonChangeLevelDTO pokemonChangeLevelDTO) {
        Pokemon pokemon = pokemonService.findPokemonById(pokemonChangeLevelDTO.getId());
        if (pokemon == null) {
            throw new PlpmServiceException("Pokemon with id " + pokemonChangeLevelDTO.getId() + " doesn't exist.");
        }
        pokemon.setLevel(pokemonChangeLevelDTO.getLevel());
        pokemonService.updatePokemonInfo(pokemon);
    }

    @Override
    public void updatePokemon(PokemonDTO pokemonDTO) {
        pokemonService.updatePokemonInfo(beanMappingService.mapTo(pokemonDTO, Pokemon.class));
    }

    @Override
    public void deletePokemon(Long id) {
        Pokemon pokemonToDelete = new Pokemon();
        pokemonToDelete.setId(id);
        pokemonService.deletePokemon(pokemonToDelete);
    }

    @Override
    public PokemonDTO getPokemonById(Long id) {
        Pokemon foundPokemon = pokemonService.findPokemonById(id);
        return foundPokemon == null ? null : beanMappingService.mapTo(foundPokemon, PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getPokemonByName(String name) {
        return beanMappingService.mapTo(pokemonService.findPokemonByName(name), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getPokemonByNickname(String nickname) {
        return beanMappingService.mapTo(pokemonService.findPokemonByNickname(nickname), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getPokemonByTrainer(TrainerDTO trainerDTO) {
        return beanMappingService
                .mapTo(pokemonService
                        .findPokemonByTrainer(beanMappingService.mapTo(trainerDTO, Trainer.class)), PokemonDTO.class);
    }

    @Override
    public List<PokemonDTO> getAllPokemons() {
        return beanMappingService.mapTo(pokemonService.findAllPokemons(), PokemonDTO.class);
    }
}
