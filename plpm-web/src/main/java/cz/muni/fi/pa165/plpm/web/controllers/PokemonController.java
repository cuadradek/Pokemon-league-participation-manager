package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.dto.PokemonChangeTrainerDTO;
import cz.muni.fi.pa165.plpm.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.plpm.dto.PokemonDTO;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.apache.commons.lang3.StringUtils;
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
import java.security.Principal;
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

    @Autowired
    private TrainerFacade trainerFacade;

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
    public String view(@PathVariable long id, Model model, Principal principal) {
        PokemonDTO pokemonDTO = pokemonFacade.getPokemonById(id);
        model.addAttribute("pokemon", pokemonDTO);
        model.addAttribute("trainer", principal.getName());

        if (pokemonDTO.getTrainer() != null && principal.getName().equals(pokemonDTO.getTrainer().getNickname()))
            model.addAttribute("viewOwnPokemon", true);

        return "pokemon/view";
    }

    @GetMapping("/edit/{id}")
    public String getForEdit(@PathVariable long id, Model model) {
        PokemonDTO pokemonForm = pokemonFacade.getPokemonById(id);
        model.addAttribute("editForm", pokemonForm);
        model.addAttribute("types", PokemonType.values());
        return "pokemon/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("editForm") PokemonDTO pokemonForm,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "pokemon/edit";
        }

        Long id = pokemonForm.getId();
        if (id == null) {
            PokemonCreateDTO pokemonCreateDTO = new PokemonCreateDTO();
            pokemonCreateDTO.setName(pokemonForm.getName());
            pokemonCreateDTO.setNickname(pokemonForm.getNickname());
            pokemonCreateDTO.setType(pokemonForm.getType());
            id = pokemonFacade.createPokemon(pokemonCreateDTO);
        } else {
            pokemonFacade.updatePokemon(pokemonForm);
        }

        redirectAttributes.addFlashAttribute("alert_success", "Successful update.");
        return "redirect:" + uriBuilder.path("/pokemon/edit" + (id == null ? "" : ("/" + id))).toUriString();
    }

    @GetMapping("/create")
    public String create(Model model) {
        PokemonDTO pokemonForm = new PokemonDTO();
        model.addAttribute("editForm", pokemonForm);
        model.addAttribute("types", PokemonType.values());
        return "pokemon/edit";
    }

    @PostMapping("/catch/{id}")
    public String catchPokemon(@PathVariable long id,
                               UriComponentsBuilder uriBuilder,
                               RedirectAttributes redirectAttributes,
                               Principal principal) {

        PokemonChangeTrainerDTO pokemonChangeTrainerDTO = new PokemonChangeTrainerDTO();
        pokemonChangeTrainerDTO.setId(id);
        pokemonChangeTrainerDTO.setTrainer(trainerFacade.findTrainerByNickname(principal.getName()));
        try {
            pokemonFacade.changeTrainer(pokemonChangeTrainerDTO);
        } catch (PlpmServiceException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", ex.getMessage());
            return "redirect:" + uriBuilder.path("/pokemon/list").toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", "Congratulations! You've catched a Pokemon.");
        return "redirect:" + uriBuilder.path("/pokemon/list").toUriString();
    }

    @PostMapping("/train/{id}")
    public String trainPokemon(@PathVariable long id,
                               UriComponentsBuilder uriBuilder,
                               RedirectAttributes redirectAttributes,
                               Principal principal) {
        try {
            pokemonFacade.trainPokemon(id, principal.getName());
        } catch (PlpmServiceException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", ex.getMessage());
            return "redirect:" + uriBuilder.path("/pokemon/view/" + id).toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", "Pokemon successfully trained!");
        return "redirect:" + uriBuilder.path("/pokemon/view/" + id).toUriString();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        pokemonFacade.deletePokemon(id);
        redirectAttributes.addFlashAttribute("alert_success", "Pokemon was deleted.");

        return "redirect:" + uriBuilder.path("/pokemon/list").toUriString();
    }
}