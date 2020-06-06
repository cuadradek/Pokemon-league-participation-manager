package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.PokemonDao;
import cz.muni.fi.pa165.plpm.dto.PokemonDTO;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author: Veronika Loukotova
 */
@Service
public class PokemonServiceImpl implements PokemonService {

    private static Random random = new Random(0);

    private static final int CATCH_POKEMON_ACTION_POINTS = -1;
    private static final int TRAIN_POKEMON_ACTION_POINTS = -1;

    @Autowired
    private PokemonDao pokemonDao;

    @Autowired
    private TrainerService trainerService;

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
        if (!foundPokemon.getNickname().equals(pokemon.getNickname()) &&
                !findPokemonByNickname(pokemon.getNickname()).isEmpty()) {
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
    public void changeTrainer(Pokemon pokemon, Trainer trainer) {
        trainerService.addActionPoints(trainer, CATCH_POKEMON_ACTION_POINTS);
        pokemon.setTrainer(trainer);

        try {
            pokemonDao.update(pokemon);
        } catch (ConstraintViolationException ex) {
            throw new PlpmServiceException("Pokemon update failed.", ex);
        }
    }

    @Override
    public void trainPokemon(Pokemon pokemon, Trainer trainer) {
        if (!pokemon.getTrainer().equals(trainer))
            throw new PlpmServiceException("You can train only your own pokemons!");

        trainerService.addActionPoints(pokemon.getTrainer(), TRAIN_POKEMON_ACTION_POINTS);
        pokemon.setLevel(pokemon.getLevel() + 1);
        updatePokemonInfo(pokemon);
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


    @Override
    public BattleResults fight(List<Pokemon> attackersPokemons, List<Pokemon> defendersPokemons) {
        int attackersFightingPokemonIndex = 0;
        int defendersFightingPokemonIndex = 0;

        while (attackersFightingPokemonIndex < attackersPokemons.size() && defendersFightingPokemonIndex < defendersPokemons.size()) {
            long attackersAttack = attackersPokemons.get(attackersFightingPokemonIndex).getLevel();
            long defendersAttack = defendersPokemons.get(defendersFightingPokemonIndex).getLevel();
            long randomChance = random.nextInt() % 81 - 40;

            if (attackersAttack > defendersAttack + randomChance) {
                defendersFightingPokemonIndex++;
            } else {
                attackersFightingPokemonIndex++;
            }
        }

        if (attackersFightingPokemonIndex >= attackersPokemons.size()) {
            return BattleResults.DEFENDER_WINS;
        }
        return BattleResults.ATTACKER_WINS;
    }

}
