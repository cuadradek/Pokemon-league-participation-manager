package cz.muni.fi.pa165.plpm.facade;

import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.entity.Pokemon;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.service.BeanMappingService;
import cz.muni.fi.pa165.plpm.service.PokemonService;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacade;
import org.mockito.ArgumentCaptor;
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
    private BeanMappingService beanMappingService;

    @Autowired
    @InjectMocks
    private PokemonFacade pokemonFacade;

    private Pokemon pokemon1;

    private Pokemon pokemon2;

    private Trainer trainer1;

    private Trainer trainer2;

    private PokemonCreateDTO pokemonCreate1;

    private PokemonChangeTrainerDTO pokemon1ChangeTrainer;

    private PokemonDTO pokemonDTO;

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

        pokemonDTO = new PokemonDTO();
        pokemonDTO.setId(2L);
        pokemonDTO.setName("Caterpie");
        pokemonDTO.setNickname("Cat");
        pokemonDTO.setType(PokemonType.BUG);
        pokemonDTO.setTrainer(trainer2DTO);
        pokemonDTO.setLevel(1);

        trainer1DTO = new TrainerDTO();
        trainer1DTO.setId(1L);
        trainer1DTO.setNickname("ashy");
        trainer1DTO.setFirstName("Ash");
        trainer1DTO.setLastName("Satoshi");
        trainer1DTO.setBirthDate(new Date());
        trainer1DTO.setPassword("p455w0rd");

        trainer2DTO = new TrainerDTO();
        trainer2DTO.setId(2L);
        trainer2DTO.setNickname("norm");
        trainer2DTO.setFirstName("Norman");
        trainer2DTO.setLastName("Senri");
        trainer2DTO.setPassword("123");
        trainer2DTO.setBirthDate(new Date());

        pokemon1ChangeTrainer = new PokemonChangeTrainerDTO();
        pokemon1ChangeTrainer.setTrainer(trainer1DTO);
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

        verify(pokemonServiceMock).updatePokemonInfo(updatedPokemon);
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
        when(beanMappingService.mapTo(pokemon2, PokemonDTO.class)).thenReturn(pokemonDTO);

        PokemonDTO found = pokemonFacade.getPokemonById(pokemon2.getId());

        Assert.assertEquals(pokemonDTO, found);
    }

    @Test
    public void findPokemonsByName() {
        when(pokemonServiceMock.findPokemonByName(pokemon2.getName())).thenReturn(Collections.singletonList(pokemon2));
        when(beanMappingService.mapTo(pokemon2, PokemonDTO.class)).thenReturn(pokemonDTO);

        List<PokemonDTO> found = pokemonFacade.getPokemonByName(pokemon2.getName());

        verify(pokemonServiceMock).findPokemonByName(pokemon2.getName());
        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(pokemonDTO));
    }

    @Test
    public void findPokemonsByNickname() {
        when(pokemonServiceMock.findPokemonByNickname(pokemon2.getNickname())).thenReturn(Collections.singletonList(pokemon2));
        when(beanMappingService.mapTo(pokemon2, PokemonDTO.class)).thenReturn(pokemonDTO);

        List<PokemonDTO> found = pokemonFacade.getPokemonByNickname(pokemon2.getNickname());

        verify(pokemonServiceMock).findPokemonByNickname(pokemon2.getNickname());
        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(pokemonDTO));
    }

    @Test
    public void findPokemonsByTrainer() {
        when(pokemonServiceMock.findPokemonByTrainer(trainer2)).thenReturn(Collections.singletonList(pokemon2));
        when(beanMappingService.mapTo(pokemon2, PokemonDTO.class)).thenReturn(pokemonDTO);
        when(beanMappingService.mapTo(trainer2, Trainer.class)).thenReturn(trainer2);

        List<PokemonDTO> found = pokemonFacade.getPokemonByTrainer(trainer2DTO);

        verify(pokemonServiceMock).findPokemonByTrainer(trainer2);
        Assert.assertEquals(1, found.size());
        Assert.assertTrue(found.contains(pokemonDTO));
    }
}
