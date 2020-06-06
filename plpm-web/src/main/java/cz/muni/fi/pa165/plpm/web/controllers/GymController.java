package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.*;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import cz.muni.fi.pa165.plpm.service.facade.GymFacade;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * @author Karolína Kolouchová
 */
@Controller
@RequestMapping("/gym")
public class GymController {

    @Autowired
    private GymFacade gymFacade;

    @Autowired
    private BadgeFacade badgeFacade;

    @Autowired
    private PokemonFacade pokemonFacade;

    @Autowired
    private TrainerFacade trainerFacade;

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("gyms", gymFacade.findAllGyms());

        return "gym/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model, Principal principal) {
        TrainerDTO trainer = principal == null ? null : trainerFacade.findTrainerByNickname(principal.getName());
        GymDTO gym = gymFacade.findGymById(id);

        if (trainer != null && (trainer.isAdmin() || trainer.getId().equals(gym.getLeader().getId()))) {
            model.addAttribute("canEdit", true);
        }

        List<GymDTO> beatenGyms = trainer == null ? emptyList(): badgeFacade.getBeatenGyms(trainer.getId());
        if (trainer != null && !trainer.getId().equals(gym.getLeader().getId()) && !beatenGyms.contains(gym)) {
            model.addAttribute("canAttack", true);
        }

        model.addAttribute("gym", gym);
        model.addAttribute("badges", badgeFacade.getBadgesByGymId(id));

        return "gym/view";
    }

    @GetMapping("/create")
    public String getForCreate(Model model,
                               UriComponentsBuilder uriBuilder,
                               RedirectAttributes redirectAttribute,
                               Principal principal) {
        TrainerDTO trainer = principal == null ? null : trainerFacade.findTrainerByNickname(principal.getName());

        if (trainer == null || !trainer.isAdmin()) {
            redirectAttribute.addFlashAttribute("alert_warning", "Only admin can create gym.");
            return "redirect:" + uriBuilder.path("/gym/list").toUriString();
        }

        GymDTO gym = new GymDTO();
        model.addAttribute("editForm", gym);
        model.addAttribute("types", PokemonType.values());
        return "gym/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("editForm") GymDTO gymForm,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder) {
        if (StringUtils.isBlank(gymForm.getCity())) {
            bindingResult.addError(new FieldError("gym", "city", "City cannot be empty."));
            model.addAttribute("city_error", true);
            return "gym/edit";
        }

        GymCreateDTO create = new GymCreateDTO();
        create.setCity(gymForm.getCity());
        create.setTrainerId(gymForm.getLeader().getId());
        create.setType(gymForm.getType());
        Long id;
        try {
            id = gymFacade.createGym(create);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("gym", "leader.id", ex.getMessage()));
            model.addAttribute("leader_error", true);
            return "gym/create";
        }
        redirectAttributes.addFlashAttribute("alert_success", "Successfully saved.");
        return "redirect:" + uriBuilder.path("/gym/edit/" + id).toUriString();
    }

    @GetMapping("/edit/{id}")
    public String getForEdit(@PathVariable long id,
                             Model model,
                             UriComponentsBuilder uriBuilder,
                             RedirectAttributes redirectAttribute,
                             Principal principal) {
        TrainerDTO trainer = principal == null ? null : trainerFacade.findTrainerByNickname(principal.getName());
        GymDTO gymForm = gymFacade.findGymById(id);

        if (gymForm == null) {
            redirectAttribute.addFlashAttribute("alert_warning", "Requested gym doesn't exist.");
            return "redirect:" + uriBuilder.path("/gym/list").toUriString();
        }

        if (trainer == null || (!trainer.isAdmin() && !trainer.getId().equals(gymForm.getLeader().getId()))) {
            redirectAttribute.addFlashAttribute("alert_warning", "Only admin and gym leader can edit gym.");
            return "redirect:" + uriBuilder.path("/gym/view/" + id).toUriString();
        }

        model.addAttribute("editForm", gymForm);
        model.addAttribute("types", PokemonType.values());
        return "gym/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("editForm") GymDTO gymForm,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {

        if (StringUtils.isBlank(gymForm.getCity())) {
            bindingResult.addError(new FieldError("gym", "city", "City cannot be empty."));
            model.addAttribute("city_error", true);
            return "gym/edit";
        }

        Long id = gymForm.getId();
        try {
            if (id == null) {
                redirectAttributes.addFlashAttribute("alert_warning", "Cannot edit non-existent gym.");
                return "redirect:" + uriBuilder.path("/gym/view/" + id).toUriString();
            }
            gymFacade.updateGym(gymForm);
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("gym", "leader.id", ex.getMessage()));
            model.addAttribute("leader_error", true);
            return "gym/edit";
        }
        redirectAttributes.addFlashAttribute("alert_success", "Successfully saved.");
        return "redirect:" + uriBuilder.path("/gym/edit/" + id).toUriString();
    }

    @PostMapping("/delete/{id}")
    public String remove(@PathVariable long id,
                         UriComponentsBuilder uriBuilder,
                         RedirectAttributes redirectAttributes) {
        GymDTO gym = gymFacade.findGymById(id);
        gymFacade.removeGym(gym);
        redirectAttributes.addFlashAttribute("alert_success", "Successfully deleted.");
        return "redirect:" + uriBuilder.path("/gym/list").toUriString();
    }

    @GetMapping("/battle/{attackedGymId}")
    public String battle(@PathVariable long attackedGymId, Model model, RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder, Principal principal) {
        TrainerDTO trainerDTO = (principal != null) ? trainerFacade.findTrainerByNickname(principal.getName()) : null;
        GymDTO ownedGym = (trainerDTO != null && !trainerDTO.isAdmin()) ? gymFacade.findGymByTrainer(trainerDTO.getId()) : null;

        if (trainerDTO == null) {
            redirectAttributes.addFlashAttribute("alert_warning", "Only registered trainer can attack gym.");
            return "redirect:" + uriBuilder.path("/gym/list").toUriString();
        }

        if (ownedGym != null && ownedGym.getId() == attackedGymId) {
            redirectAttributes.addFlashAttribute("alert_warning", "Gym leader cannot attack his/her own gym.");
            return "redirect:" + uriBuilder.path("/gym/list").toUriString();
        }

        Collection<GymDTO> beatenGyms = badgeFacade.getBeatenGyms(trainerDTO.getId());

        if (beatenGyms.contains(gymFacade.findGymById(attackedGymId))) {
            redirectAttributes.addFlashAttribute("alert_warning", "Trainer has already received badge from this gym.");
            return "redirect:" + uriBuilder.path("/gym/list").toUriString();
        }

        model.addAttribute("trainersPokemons", pokemonFacade.getPokemonByTrainer(trainerDTO));

        return "gym/battle";
    }

    @PostMapping("/battle/{attackedGymId}")
    public String attack(@PathVariable long attackedGymId, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes,
                             UriComponentsBuilder uriBuilder, Principal principal) {
        TrainerDTO trainerDTO = (principal != null) ? trainerFacade.findTrainerByNickname(principal.getName()) : null;

        if (trainerDTO == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Login error.");
            return "redirect:" + uriBuilder.path("/gym/battle/" + attackedGymId).toUriString();
        }

        if (trainerDTO.getActionPoints() <= 0) {
            redirectAttributes.addFlashAttribute("alert_warning", "Trainer has not enough points to attack gym.");
            return "redirect:" + uriBuilder.path("/gym/list/" + attackedGymId).toUriString();
        }

        String[] selectedPokemonIdsStr = request.getParameterValues("selected");

        if (selectedPokemonIdsStr == null || selectedPokemonIdsStr.length < 2 || selectedPokemonIdsStr.length > 5) {
            redirectAttributes.addFlashAttribute("alert_warning", "Please select 2 to 5 pokemons.");
            return "redirect:" + uriBuilder.path("/gym/battle/" + attackedGymId).toUriString();
        }

        long[] selectedPokemonIds = new long[selectedPokemonIdsStr.length];
        for (int i = 0; i < selectedPokemonIdsStr.length; i++) {
            try {
                selectedPokemonIds[i] = Long.parseLong(selectedPokemonIdsStr[i]);
            } catch (NumberFormatException e) {
                redirectAttributes.addFlashAttribute("alert_danger", "Internal error occurred!");
                return "redirect:" + uriBuilder.path("/gym/battle/" + attackedGymId).toUriString();
            }
        }

        if (gymFacade.attackGym(trainerDTO.getId(), selectedPokemonIds, attackedGymId)) {
            BadgeCreateDTO badgeCreateDTO = new BadgeCreateDTO();
            badgeCreateDTO.setGymId(attackedGymId);
            badgeCreateDTO.setTrainerId(trainerDTO.getId());
            Long newBadgeId = badgeFacade.createBadge(badgeCreateDTO);

            model.addAttribute("badgeId", newBadgeId);
            return "/gym/victory";
        }

        return "gym/loss";

    }














}
