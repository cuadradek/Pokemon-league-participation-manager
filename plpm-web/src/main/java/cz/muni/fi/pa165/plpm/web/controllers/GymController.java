package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.GymCreateDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import cz.muni.fi.pa165.plpm.service.facade.GymFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.security.Principal;

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

        model.addAttribute("gym", gym);
        model.addAttribute("badges", badgeFacade.getBadgesByGymId(id));

        return "gym/view";
    }

    @GetMapping("/edit/{id}")
    public String getForEdit(@PathVariable long id,
                             Model model,
                             UriComponentsBuilder uriBuilder,
                             RedirectAttributes redirectAttribute,
                             Principal principal) {
        TrainerDTO trainer = principal == null ? null : trainerFacade.findTrainerByNickname(principal.getName());
        GymDTO gymForm = gymFacade.findGymById(id);

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
            if (gymForm.getId() == null) {
                GymCreateDTO create = new GymCreateDTO();
                create.setCity(gymForm.getCity());
                create.setTrainerId(gymForm.getLeader().getId());
                create.setType(gymForm.getType());
                id = gymFacade.createGym(create);
            } else {
                gymFacade.updateGym(gymForm);
            }
        } catch (Exception ex) {
            bindingResult.addError(new FieldError("gym", "leader.id", ex.getMessage()));
            model.addAttribute("leader_error", true);
            return "gym/edit";
        }
        redirectAttributes.addFlashAttribute("alert_success", "Successfully saved.");
        return "redirect:" + uriBuilder.path("/gym/edit" + (id == null ? "" : ("/" + id))).toUriString();
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

    @GetMapping("/create")
    public String create(Model model) {
        GymDTO gymForm = new GymDTO();
        model.addAttribute("editForm", gymForm);
        model.addAttribute("types", PokemonType.values());
        return "gym/edit";
    }
}
