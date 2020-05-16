package cz.muni.fi.pa165.plpm.rest.controllers;

import cz.muni.fi.pa165.plpm.dto.PokemonChangeLevelDTO;
import cz.muni.fi.pa165.plpm.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.plpm.dto.PokemonDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Karolina Kolouchova
 */
@RestController
@RequestMapping("/pokemons")
public class PokemonRestController {

    private PokemonFacade pokemonFacade;

    private TrainerFacade trainerFacade;

    public PokemonRestController(PokemonFacade pokemonFacade, TrainerFacade trainerFacade) {
        this.pokemonFacade = pokemonFacade;
        this.trainerFacade = trainerFacade;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public long createPokemon(@RequestBody PokemonCreateDTO pokemon) {
        return pokemonFacade.createPokemon(pokemon);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deletePokemon(@PathVariable long id) {
        pokemonFacade.deletePokemon(id);
    }

    @RequestMapping(value = "{id}/change-level/{level}", method = RequestMethod.PUT)
    public void changeLevel(@PathVariable long id, @PathVariable int level) {
        PokemonChangeLevelDTO dto = new PokemonChangeLevelDTO();
        dto.setId(id);
        dto.setLevel(level);
        pokemonFacade.changeLevel(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PokemonDTO get(@PathVariable long id) {
        return pokemonFacade.getPokemonById(id);
    }

    @RequestMapping(value = "/get-by-name/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PokemonDTO> getByName(@PathVariable String name) {
        return pokemonFacade.getPokemonByName(name);
    }

    @RequestMapping(value = "/get-by-nickname/{nickname}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PokemonDTO> getByNickname(@PathVariable String nickname) {
        return pokemonFacade.getPokemonByNickname(nickname);
    }

    @RequestMapping(value = "/get-by-trainer/{trainerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PokemonDTO> getByTrainer(@PathVariable long trainerId) {
        TrainerDTO trainer = trainerFacade.findTrainerById(trainerId);
        return pokemonFacade.getPokemonByTrainer(trainer);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PokemonDTO> getAll() {
        return pokemonFacade.getAllPokemons();
    }

}
