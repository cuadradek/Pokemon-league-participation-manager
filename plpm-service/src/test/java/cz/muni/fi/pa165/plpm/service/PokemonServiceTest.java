package cz.muni.fi.pa165.plpm.service;

import cz.muni.fi.pa165.plpm.dao.PokemonDao;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * @author Karolína Kolouchová
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class PokemonServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private PokemonDao pokemonDaoMock;

    @Autowired
    @InjectMocks
    private PokemonService pokemonService;

    private Pokemon pokemon1;

    private Pokemon pokemon2;

    private Trainer trainer1;

    private Trainer trainer2;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void before() {
        trainer1 = new Trainer();
        trainer1.setId(1L);
        trainer1.setNickname("ashy");
        trainer1.setFirstName("Ash");
        trainer1.setLastName("Satoshi");
        trainer1.setBirthDate(new Date());
        trainer1.setPassword("p455w0rd");

        trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setNickname("norm");
        trainer2.setFirstName("Norman");
        trainer2.setLastName("Senri");
        trainer2.setPassword("123");
        trainer2.setBirthDate(new Date());

        pokemon1 = new Pokemon();
        pokemon1.setName("Pikachu");
        pokemon1.setNickname("Pikapika");
        pokemon1.setType(PokemonType.ELECTRIC);
        pokemon1.setTrainer(trainer1);
        pokemon1.setLevel(2);

        pokemon2 = new Pokemon();
        pokemon2.setName("Caterpie");
        pokemon2.setNickname("Cat");
        pokemon2.setType(PokemonType.BUG);
        pokemon2.setTrainer(trainer2);
        pokemon2.setLevel(1);
    }

    @Test
    public void createPokemon() {
        doAnswer(invocation -> {
            Pokemon p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        }).when(pokemonDaoMock).create(pokemon1);

        pokemonService.createPokemon(pokemon1);
        Assert.assertEquals(pokemon1.getId(), Long.valueOf(1));
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void createPokemonExistingNickname() {
        pokemon1.setNickname(pokemon2.getNickname());
        when(pokemonDaoMock.findByNickname(pokemon1.getNickname())).thenReturn(Collections.singletonList(pokemon2));
        pokemonService.createPokemon(pokemon1);
    }

    @Test
    public void updatePokemonInfo() {
        pokemon1.setId(1L);
        pokemon2.setId(1L);
        when(pokemonDaoMock.findById(pokemon2.getId())).thenReturn(pokemon1);
        when(pokemonDaoMock.findByNickname(pokemon2.getNickname())).thenReturn(Collections.emptyList());

        pokemonService.updatePokemonInfo(pokemon2);

        verify(pokemonDaoMock).update(pokemon1);
        Assert.assertEquals(pokemon2, pokemon1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void updateNonexistingPokemon() {
        pokemon2.setId(1L);
        when(pokemonDaoMock.findById(pokemon2.getId())).thenReturn(null);
        when(pokemonDaoMock.findByNickname(pokemon2.getNickname())).thenReturn(Collections.emptyList());

        pokemonService.updatePokemonInfo(pokemon2);
    }

    @Test(expectedExceptions = PlpmServiceException.class)
    public void updatePokemonWithExistingNickname() {
        pokemon1.setId(1L);
        pokemon2.setId(1L);
        pokemon2.setNickname(pokemon1.getNickname());
        when(pokemonDaoMock.findById(pokemon2.getId())).thenReturn(pokemon1);
        when(pokemonDaoMock.findByNickname(pokemon2.getNickname())).thenReturn(Collections.singletonList(pokemon1));

        pokemonService.updatePokemonInfo(pokemon2);
    }

    @Test()
    public void updatePokemonNoChangeNickname() {
        pokemon1.setId(1L);
        Pokemon updatePokemon = new Pokemon();
        updatePokemon.setId(pokemon1.getId());
        updatePokemon.setLevel(5);
        updatePokemon.setNickname(pokemon1.getNickname());
        updatePokemon.setType(pokemon1.getType());
        updatePokemon.setName(pokemon1.getName());
        updatePokemon.setTrainer(pokemon1.getTrainer());
        when(pokemonDaoMock.findById(updatePokemon.getId())).thenReturn(pokemon1);
        when(pokemonDaoMock.findByNickname(updatePokemon.getNickname())).thenReturn(Collections.singletonList(pokemon1));

        pokemonService.updatePokemonInfo(updatePokemon);

        Assert.assertEquals(pokemon1, updatePokemon);
    }

    @Test
    public void deletePokemon() {
        pokemon1.setId(1L);

        pokemonService.deletePokemon(pokemon1);

        verify(pokemonDaoMock).remove(pokemon1);
    }

    @Test
    public void findPokemonById() {
        pokemon1.setId(1L);
        when(pokemonDaoMock.findById(pokemon1.getId())).thenReturn(pokemon1);

        Pokemon found = pokemonService.findPokemonById(1L);

        verify(pokemonDaoMock).findById(1L);
        Assert.assertEquals(pokemon1, found);
    }

    @Test
    public void findPokemonByNonexistingId() {
        when(pokemonDaoMock.findById(35L)).thenReturn(null);

        Pokemon found = pokemonService.findPokemonById(35L);

        Assert.assertNull(found);
    }

    @Test
    public void findPokemonByName() {
        pokemon1.setId(1L);
        when(pokemonDaoMock.findByName(pokemon1.getName())).thenReturn(Collections.singletonList(pokemon1));

        List<Pokemon> found = pokemonService.findPokemonByName(pokemon1.getName());

        verify(pokemonDaoMock).findByName(pokemon1.getName());
        Assert.assertEquals(1, found.size());
        Assert.assertEquals(pokemon1, found.get(0));
    }

    @Test
    public void findPokemonByNonexistingName() {
        when(pokemonDaoMock.findByName("Snorlax")).thenReturn(Collections.emptyList());

        List<Pokemon> found = pokemonService.findPokemonByName("Snorlax");

        verify(pokemonDaoMock).findByName("Snorlax");
        Assert.assertEquals(0, found.size());
    }

    @Test
    public void findPokemonByNickname() {
        pokemon1.setId(1L);
        when(pokemonDaoMock.findByNickname(pokemon1.getNickname())).thenReturn(Collections.singletonList(pokemon1));

        List<Pokemon> found = pokemonService.findPokemonByNickname(pokemon1.getNickname());

        Assert.assertEquals(1, found.size());
        Assert.assertEquals(pokemon1, found.get(0));
    }

    @Test
    public void findPokemonByNonexistingNickname() {
        when(pokemonDaoMock.findByNickname("Snorly")).thenReturn(Collections.emptyList());

        List<Pokemon> found = pokemonService.findPokemonByNickname("Snorly");

        verify(pokemonDaoMock).findByNickname("Snorly");
        Assert.assertEquals(0, found.size());
    }

    @Test
    public void findPokemonByTrainer() {
        pokemon1.setId(1L);
        when(pokemonDaoMock.findPokemonsByTrainer(pokemon1.getTrainer())).thenReturn(Collections.singletonList(pokemon1));

        List<Pokemon> found = pokemonService.findPokemonByTrainer(pokemon1.getTrainer());

        verify(pokemonDaoMock).findPokemonsByTrainer(pokemon1.getTrainer());
        Assert.assertEquals(1, found.size());
        Assert.assertEquals(pokemon1, found.get(0));
    }

    @Test
    public void findPokemonByNonexistingTrainer() {
        when(pokemonDaoMock.findPokemonsByTrainer(trainer2)).thenReturn(Collections.emptyList());

        List<Pokemon> found = pokemonService.findPokemonByTrainer(trainer2);

        verify(pokemonDaoMock).findPokemonsByTrainer(trainer2);
        Assert.assertEquals(0, found.size());
    }

    @Test
    public void findAllPokemons() {
        pokemon1.setId(1L);
        pokemon2.setId(2L);
        when(pokemonDaoMock.findAll()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        List<Pokemon> found = pokemonService.findAllPokemons();

        verify(pokemonDaoMock).findAll();
        Assert.assertEquals(2, found.size());
        Assert.assertTrue(found.contains(pokemon1));
        Assert.assertTrue(found.contains(pokemon2));
    }

    @Test
    public void findAllPokemonsEmpty() {
        when(pokemonDaoMock.findAll()).thenReturn(Collections.emptyList());

        List<Pokemon> found = pokemonService.findAllPokemons();

        Assert.assertEquals(0, found.size());
    }
}
