package cz.muni.fi.pa165.plpm.dao;

import cz.muni.fi.pa165.plpm.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;

/**
 * @author Radoslav Cerhak
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PokemonDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PokemonDao pokemonDao;

    @Autowired
    private TrainerDao trainerDao;

    private Pokemon p1;
    private Pokemon p2;
    private Trainer t1;
    private Trainer t2;

    @BeforeMethod
    public void before() {
        t1 = new Trainer();
        t1.setFirstName("Martin");
        t1.setLastName("Novy");
        t1.setBirthDate(new Date());

        t2 = new Trainer();
        t2.setFirstName("Andrej");
        t2.setLastName("Stary");
        t2.setBirthDate(new Date());

        p1 = new Pokemon();
        p1.setName("Pikachu");
        p1.setNickname("Pika");
        p1.setType(PokemonType.ELECTRIC);
        p1.setTrainer(t1);

        p2 = new Pokemon();
        p2.setName("Caterpie");
        p2.setNickname("Cat");
        p2.setType(PokemonType.BUG);
        p2.setTrainer(t2);

        trainerDao.createTrainer(t1);
        trainerDao.createTrainer(t2);

        pokemonDao.create(p1);
        pokemonDao.create(p2);
    }

    @Test
    public void createPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Novy pokemon");
        pokemon.setNickname("NP");
        pokemon.setType(PokemonType.ELECTRIC);
        pokemon.setTrainer(t1);

        pokemonDao.create(pokemon);

        Pokemon foundPokemon = pokemonDao.findById(pokemon.getId());

        Assert.assertEquals(pokemon, foundPokemon);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createPokemonWithNullName() {
        Pokemon pokemon = new Pokemon();
        pokemon.setNickname("NP");
        pokemon.setType(PokemonType.ELECTRIC);
        pokemon.setTrainer(t1);

        pokemonDao.create(pokemon);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createPokemonWithNullNickname() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("NP");
        pokemon.setType(PokemonType.ELECTRIC);
        pokemon.setTrainer(t1);

        pokemonDao.create(pokemon);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void createPokemonWithNullType() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("NP");
        pokemon.setNickname("NP");
        pokemon.setTrainer(t1);

        pokemonDao.create(pokemon);
    }

    @Test
    public void createPokemonWithoutTrainer() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Novy pokemon");
        pokemon.setNickname("NP");
        pokemon.setType(PokemonType.ELECTRIC);

        pokemonDao.create(pokemon);

        Pokemon foundPokemon = pokemonDao.findById(pokemon.getId());

        Assert.assertEquals(pokemon, foundPokemon);
    }

    @Test
    public void updatePokemonsName() {
        p1.setName("new name");
        pokemonDao.update(p1);

        Pokemon foundPokemon = pokemonDao.findById(p1.getId());
        Assert.assertEquals(p1, foundPokemon);
    }

    @Test
    public void updatePokemonsNickname() {
        p1.setNickname("new nick");
        pokemonDao.update(p1);
        Pokemon foundPokemon = pokemonDao.findById(p1.getId());
        Assert.assertEquals(p1, foundPokemon);
    }

    @Test
    public void updatePokemonsType() {
        p1.setType(PokemonType.DARK);
        pokemonDao.update(p1);
        Pokemon foundPokemon = pokemonDao.findById(p1.getId());
        Assert.assertEquals(p1, foundPokemon);
    }

    @Test
    public void updatePokemonsTrainer() {
        p1.setTrainer(t2);
        pokemonDao.update(p1);
        Pokemon foundPokemon = pokemonDao.findById(p1.getId());
        Assert.assertEquals(p1, foundPokemon);
    }

    @Test
    public void updatePokemonsTrainerToNull() {
        p1.setTrainer(null);

        pokemonDao.update(p1);

        Pokemon foundPokemon = pokemonDao.findById(p1.getId());

        Assert.assertEquals(p1, foundPokemon);
    }

    @Test
    public void removePokemon() {
        pokemonDao.remove(p1);

        List<Pokemon> pokemons = pokemonDao.findAll();

        Assert.assertEquals(1, pokemons.size());
        Assert.assertEquals(pokemons.get(0), p2);
    }

    @Test
    public void findPokemonById() {
        Pokemon foundPokemon = pokemonDao.findById(p1.getId());

        Assert.assertEquals(p1, foundPokemon);
    }

    @Test
    public void findPokemonByNotExistingId() {
        Pokemon foundPokemon = pokemonDao.findById(555L);

        Assert.assertNull(foundPokemon);
    }

    @Test
    public void findPokemonsByName() {
        List<Pokemon> pokemons = pokemonDao.findByName("Pikachu");

        Assert.assertEquals(p1, pokemons.get(0));
        Assert.assertEquals(1, pokemons.size());
    }

    @Test
    public void findPokemonsByNotExistingName() {
        List<Pokemon> pokemons = pokemonDao.findByName("Pikachuuuuu");

        Assert.assertEquals(0, pokemons.size());
    }

    @Test
    public void findPokemonsByNickname() {
        List<Pokemon> pokemons = pokemonDao.findByNickname("Cat");

        Assert.assertEquals(p2, pokemons.get(0));
        Assert.assertEquals(1, pokemons.size());
    }

    @Test
    public void findPokemonsByNotExistingNickname() {
        List<Pokemon> pokemons = pokemonDao.findByName("Pikachuuuuu");

        Assert.assertEquals(0, pokemons.size());
    }

    @Test
    public void findPokemonsByTrainer() {
        List<Pokemon> pokemonsOfFirstTrainer = pokemonDao.findPokemonsByTrainer(t1);
        List<Pokemon> pokemonsOfSecondTrainer = pokemonDao.findPokemonsByTrainer(t2);

        Assert.assertEquals(p1, pokemonsOfFirstTrainer.get(0));
        Assert.assertEquals(p2, pokemonsOfSecondTrainer.get(0));
        Assert.assertEquals(1, pokemonsOfFirstTrainer.size());
        Assert.assertEquals(1, pokemonsOfSecondTrainer.size());
    }

    @Test
    public void findPokemonsByWithNullTrainer() {
        p1.setTrainer(null);
        pokemonDao.update(p1);
        List<Pokemon> pokemons = pokemonDao.findPokemonsByTrainer(null);

        Assert.assertEquals(1, pokemons.size());
        Assert.assertEquals(p1, pokemons.get(0));
    }

    @Test
    public void findPokemonsByTrainerWithZeroPokemons() {
        p1.setTrainer(null);
        pokemonDao.update(p1);
        List<Pokemon> pokemons = pokemonDao.findPokemonsByTrainer(t1);

        Assert.assertEquals(0, pokemons.size());
    }

    @Test
    public void findAllPokemons() {
        List<Pokemon> pokemons = pokemonDao.findAll();

        Assert.assertEquals(2, pokemons.size());
        Assert.assertTrue(pokemons.contains(p1));
        Assert.assertTrue(pokemons.contains(p2));
    }
}
