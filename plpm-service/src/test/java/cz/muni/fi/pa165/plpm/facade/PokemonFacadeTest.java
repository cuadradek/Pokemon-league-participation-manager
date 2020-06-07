package cz.muni.fi.pa165.plpm.facade;

import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.PokemonService;
import cz.muni.fi.pa165.plpm.service.TrainerService;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacade;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacadeImpl;
import org.mockito.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Karolína Kolouchová
 */
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class PokemonFacadeTest extends AbstractTestNGSpringContextTests {

    @Mock
    private PokemonService pokemonServiceMock;

    @Mock
    private TrainerService trainerServiceMock;

    @Mock
    private BeanMappingService beanMappingService;

    @InjectMocks
    private PokemonFacade pokemonFacade = new PokemonFacadeImpl();

    private Pokemon pokemon1;

    private Pokemon pokemon2;

    private Trainer trainer1;

    private Trainer trainer2;

    private PokemonCreateDTO pokemonCreate1;

    private PokemonChangeTrainerDTO pokemon1ChangeTrainer;

    private PokemonDTO pokemon1DTO;

    private PokemonDTO pokemon2DTO;

    private TrainerDTO trainer1DTO;

    private TrainerDTO trainer2DTO;

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
        trainer1.setActionPoints(5);

        trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setNickname("norm");
        trainer2.setFirstName("Norman");
        trainer2.setLastName("Senri");
        trainer2.setPassword("123");
        trainer2.setBirthDate(new Date());
        trainer2.setActionPoints(5);

        pokemon1 = new Pokemon();
        pokemon1.setName("Pikachu");
        pokemon1.setNickname("Pikapika");
        pokemon1.setType(PokemonType.ELECTRIC);

        pokemon2 = new Pokemon();
        pokemon2.setId(2L);
        pokemon2.setName("Caterpie");
        pokemon2.setNickname("Cat");
        pokemon2.setType(PokemonType.BUG);
        pokemon2.setTrainer(trainer2);
        pokemon2.setLevel(1);

        pokemonCreate1 = new PokemonCreateDTO();
        pokemonCreate1.setName("Pikachu");
        pokemonCreate1.setNickname("Pikapika");
        pokemonCreate1.setType(PokemonType.ELECTRIC);

        pokemon1DTO = new PokemonDTO();
        pokemon1DTO.setId(1L);
        pokemon1DTO.setName("Pikachu");
        pokemon1DTO.setNickname("Pikapika");
        pokemon1DTO.setType(PokemonType.ELECTRIC);
        pokemon1DTO.setTrainer(trainer1DTO);
        pokemon1DTO.setLevel(1);

        pokemon2DTO = new PokemonDTO();
        pokemon2DTO.setId(2L);
        pokemon2DTO.setName("Caterpie");
        pokemon2DTO.setNickname("Cat");
        pokemon2DTO.setType(PokemonType.BUG);
        pokemon2DTO.setTrainer(trainer2DTO);
        pokemon2DTO.setLevel(1);

        trainer1DTO = new TrainerDTO();
        trainer1DTO.setId(1L);
        trainer1DTO.setNickname("ashy");
        trainer1DTO.setFirstName("Ash");
        trainer1DTO.setLastName("Satoshi");
        trainer1DTO.setBirthDate(new Date());
        trainer1DTO.setPassword("p455w0rd");
        trainer1DTO.setActionPoints(5);

        trainer2DTO = new TrainerDTO();
        trainer2DTO.setId(2L);
        trainer2DTO.setNickname("norm");
        trainer2DTO.setFirstName("Norman");
        trainer2DTO.setLastName("Senri");
        trainer2DTO.setPassword("123");
        trainer2DTO.setBirthDate(new Date());
        trainer2DTO.setActionPoints(5);

        pokemon1ChangeTrainer = new PokemonChangeTrainerDTO();
        pokemon1ChangeTrainer.setTrainer(trainer1DTO);
    }

    @AfterMethod
    public void reset() {
        Mockito.reset(pokemonServiceMock);
        Mockito.reset(beanMappingService);
    }

    @Test
    public void createPokemon() {
        when(beanMappingService.mapTo(pokemonCreate1, Pokemon.class)).thenReturn(pokemon1);
        when(pokemonServiceMock.createPokemon(pokemon1)).then(
                call -> { Pokemon pokemon = call.getArgument(0);
                    pokemon.setId(13L);
                    return pokemon;
                });

        Long createdId = pokemonFacade.createPokemon(pokemonCreate1);

        Assert.assertEquals(13L, createdId.longValue());
    }

    @Test
    public void changeTrainer() {
        pokemon1.setId(13L);
        pokemon1ChangeTrainer.setId(13L);
        Pokemon updatedPokemon = new Pokemon();
        updatedPokemon.setId(pokemon1.getId());
        updatedPokemon.setNickname(pokemon1.getNickname());
        updatedPokemon.setName(pokemon1.getName());
        updatedPokemon.setType(PokemonType.ELECTRIC);
        updatedPokemon.setTrainer(trainer1);
        when(beanMappingService.mapTo(pokemon1ChangeTrainer.getTrainer(), Trainer.class)).thenReturn(trainer1);
        when(pokemonServiceMock.findPokemonById(pokemon1.getId())).thenReturn(pokemon1);

        pokemonFacade.changeTrainer(pokemon1ChangeTrainer);

        verify(pokemonServiceMock).changeTrainer(updatedPokemon, trainer1);
    }

    @Test
    public void trainPokemon() {
        pokemon2.setId(13L);
        when(pokemonServiceMock.findPokemonById(pokemon2.getId())).thenReturn(pokemon2);
        when(trainerServiceMock.findTrainerByNickname(pokemon2.getTrainer().getNickname())).thenReturn(pokemon2.getTrainer());
        doAnswer(invocation -> {
            Pokemon p = invocation.getArgument(0);
            p.setLevel(p.getLevel() + 1);
            return null;
        }).when(pokemonServiceMock).trainPokemon(pokemon2, pokemon2.getTrainer());
        int level = pokemon2.getLevel();

        pokemonFacade.trainPokemon(pokemon2.getId(), pokemon2.getTrainer().getNickname());
        Assert.assertEquals(level + 1, pokemon2.getLevel());
    }

    @Test
    public void changeLevel() {
        pokemon1.setId(13L);
        PokemonChangeLevelDTO changeLevel = new PokemonChangeLevelDTO();
        changeLevel.setId(13L);
        changeLevel.setLevel(2);
        when(pokemonServiceMock.findPokemonById(changeLevel.getId())).thenReturn(pokemon1);

        pokemonFacade.changeLevel(changeLevel);
        pokemon1.setLevel(2);

        verify(pokemonServiceMock).updatePokemonInfo(pokemon1);
    }

    @Test
    public void deletePokemon() {
        ArgumentCaptor<Pokemon> pokemonCaptor = ArgumentCaptor.forClass(Pokemon.class);
        when(pokemonServiceMock.findPokemonById(pokemon2.getId())).thenReturn(pokemon2);

        pokemonFacade.deletePokemon(pokemon2.getId());
        verify(pokemonServiceMock, times(1)).deletePokemon(pokemonCaptor.capture());
        Assert.assertEquals(pokemonCaptor.getValue().getId(), pokemon2.getId());
    }

    @Test
    public void findPokemonById() {
        when(pokemonServiceMock.findPokemonById(pokemon2.getId())).thenReturn(pokemon2);
        when(beanMappingService.mapTo(pokemon2, PokemonDTO.class)).thenReturn(pokemon1DTO);

        PokemonDTO found = pokemonFacade.getPokemonById(pokemon2.getId());

        Assert.assertEquals(pokemon1DTO, found);
    }

    @Test
    public void findPokemonsByName() {
        when(pokemonServiceMock.findPokemonByName(pokemon2.getName())).thenReturn(Collections.singletonList(pokemon2));
        when(beanMappingService.mapTo(Collections.singletonList(pokemon2), PokemonDTO.class)).thenReturn(Collections.singletonList(pokemon1DTO));

        List<PokemonDTO> found = pokemonFacade.getPokemonByName(pokemon2.getName());

        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(pokemon1DTO));
    }

    @Test
    public void findPokemonsByNickname() {
        when(pokemonServiceMock.findPokemonByNickname(pokemon2.getNickname())).thenReturn(Collections.singletonList(pokemon2));
        when(beanMappingService.mapTo(Collections.singletonList(pokemon2), PokemonDTO.class)).thenReturn(Collections.singletonList(pokemon1DTO));

        List<PokemonDTO> found = pokemonFacade.getPokemonByNickname(pokemon2.getNickname());

        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(pokemon1DTO));
    }

    @Test
    public void findPokemonsByTrainer() {
        when(pokemonServiceMock.findPokemonByTrainer(trainer2)).thenReturn(Collections.singletonList(pokemon2));
        when(beanMappingService.mapTo(Collections.singletonList(pokemon2), PokemonDTO.class)).thenReturn(Collections.singletonList(pokemon1DTO));
        when(beanMappingService.mapTo(trainer2DTO, Trainer.class)).thenReturn(trainer2);

        List<PokemonDTO> found = pokemonFacade.getPokemonByTrainer(trainer2DTO);

        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(pokemon1DTO));
    }

    @Test
    public void getAllPokemons() {
        pokemon1.setId(1L);
        pokemon1.setTrainer(trainer1);
        pokemon1.setLevel(1);
        List<Pokemon> all = new ArrayList<>();
        all.add(pokemon1);
        all.add(pokemon2);
        List<PokemonDTO> allDtos = new ArrayList<>();
        allDtos.add(pokemon1DTO);
        allDtos.add(pokemon2DTO);
        when(pokemonServiceMock.findAllPokemons()).thenReturn(all);
        when(beanMappingService.mapTo(all, PokemonDTO.class)).thenReturn(allDtos);

        List<PokemonDTO> found = pokemonFacade.getAllPokemons();
        Assert.assertEquals(2, found.size());
        Assert.assertTrue(found.contains(pokemon1DTO));
        Assert.assertTrue(found.contains(pokemon2DTO));
    }
}
