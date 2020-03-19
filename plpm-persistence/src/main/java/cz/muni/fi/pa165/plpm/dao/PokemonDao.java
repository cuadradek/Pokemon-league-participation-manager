package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;

import java.util.List;

/**
 * author Veronika Loukotová
 */

public interface PokemonDao {
    /**
     * Adds pokemon to database.
     * @param pokemon
     */

    public void create(Pokemon pokemon);

    /**
     * Finds pokemon by id in database.
     * @param id
     * @return pokemon if it is found in database, null otherwise
     */
    public Pokemon findById(Long id);

    /**
     * Finds pokemons by name in database.
     * @param name
     * @return list of pokemons
     */
    public List<Pokemon> findByName(String name);

    /**
     * Finds pokemons by nickname in database.
     * @param nickname
     * @return list of pokemons
     */
    public List<Pokemon> findByNickname(String nickname);

    /**
     * Finds pokemons by trainer in database.
     * @param trainer
     * @return list of pokemons
     */
    public List<Pokemon> findPokemonsByTrainer(Trainer trainer);

    /**
     * Removes pokemon from database.
     * @param pokemon
     */
    public void remove(Pokemon pokemon);

    /**
     * Updates pokemon in database.
     * @param pokemon
     */
    public void update(Pokemon pokemon);

    /**
     * Finds all pokemons in database.
     * @return list of pokemons
     */
    public List<Pokemon> findAll();
}
