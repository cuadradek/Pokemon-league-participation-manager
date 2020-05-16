package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.PokemonChangeTrainerDTO;
import cz.muni.fi.pa165.plpm.dto.PokemonDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerUpdateInfoDTO;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Veronika Loukotova
 */
@Controller

@RequestMapping("/pokemon")
public class PokemonController {
    @Autowired
    private PokemonFacade pokemonFacade;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false);
        binder.registerCustomEditor(Date.class, editor);
    }

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("pokemons", pokemonFacade.getAllPokemons());

        return "pokemon/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {
        PokemonDTO pokemonDTO = pokemonFacade.getPokemonById(id);
        model.addAttribute("pokemon", pokemonDTO);
        model.addAttribute("trainer", pokemonDTO.getTrainer());

        return "pokemon/view";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id, UriComponentsBuilder uriComponentsBuilder, RedirectAttributes redirectAttributes) {
        pokemonFacade.deletePokemon(id);
        redirectAttributes.addFlashAttribute("alert_success", "Pokemon was deleted.");

        return "redirect:" + uriComponentsBuilder.path("/pokemon/view").toUriString();
    }
}