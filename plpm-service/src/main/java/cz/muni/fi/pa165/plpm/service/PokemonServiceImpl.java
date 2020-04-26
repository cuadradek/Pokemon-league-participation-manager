package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.PokemonDao;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @author: Veronika Loukotova
 */
@Service
public class PokemonServiceImpl implements PokemonService {
    @Autowired
    private PokemonDao pokemonDao;

    @Override
    public Pokemon createPokemon(Pokemon pokemon) {
        if (!pokemonDao.findByNickname(pokemon.getNickname()).isEmpty()) {
            throw new PlpmServiceException("Pokemon with same nickname already exists.");
        }
        try {
            pokemonDao.create(pokemon);
        } catch (ConstraintViolationException ex) {
            throw new PlpmServiceException("Pokemon creation failed.", ex);
        }
        return pokemon;
    }

    @Override
    public void updatePokemonInfo(Pokemon pokemon) {
        Pokemon foundPokemon = pokemonDao.findById(pokemon.getId());
        if (foundPokemon == null) {
            throw new PlpmServiceException("Cannot update Pokemon which doesn't exist.");
        }
        if (foundPokemon.getNickname() != pokemon.getNickname() && !pokemonDao.findByNickname(pokemon.getNickname()).isEmpty()) {
            throw new PlpmServiceException("Cannot update Pokemon " + pokemon.getId() + ", Pokemon with same nickname already exists.");
        }
        foundPokemon.setLevel(pokemon.getLevel());
        foundPokemon.setTrainer(pokemon.getTrainer());
        foundPokemon.setName(pokemon.getName());
        foundPokemon.setNickname(pokemon.getNickname());
        foundPokemon.setType(pokemon.getType());

        try {
            pokemonDao.update(foundPokemon);
        } catch (ConstraintViolationException ex) {
            throw new PlpmServiceException("Pokemon update failed.", ex);
        }
    }

    @Override
    public void deletePokemon(Pokemon pokemon) {
        pokemonDao.remove(pokemon);
    }

    @Override
    public Pokemon findPokemonById(Long id) {
        return pokemonDao.findById(id);
    }

    @Override
    public List<Pokemon> findPokemonByName(String name) {
        return pokemonDao.findByName(name);
    }

    @Override
    public List<Pokemon> findPokemonByNickname(String nickname) {
        return pokemonDao.findByNickname(nickname);
    }

    @Override
    public List<Pokemon> findPokemonByTrainer(Trainer trainer) {
        return pokemonDao.findPokemonsByTrainer(trainer);
    }

    @Override
    public List<Pokemon> findAllPokemons() {
        return pokemonDao.findAll();
    }
}
