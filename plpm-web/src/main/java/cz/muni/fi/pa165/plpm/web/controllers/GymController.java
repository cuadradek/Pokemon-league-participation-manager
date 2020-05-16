package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.GymCreateDTO;
import cz.muni.fi.pa165.plpm.dto.GymDTO;
import cz.muni.fi.pa165.plpm.enums.PokemonType;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import cz.muni.fi.pa165.plpm.service.facade.GymFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

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

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("gyms", gymFacade.findAllGyms());

        return "gym/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {
        GymDTO gym = gymFacade.findGymById(id);
        model.addAttribute("gym", gym);
        model.addAttribute("badges", badgeFacade.getBadgesByGymId(id));

        return "gym/view";
    }

    @GetMapping("/edit/{id}")
    public String getForEdit(@PathVariable long id, Model model) {
        //TODO check if user can edit?
        GymDTO gymForm = gymFacade.findGymById(id);
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
        redirectAttributes.addFlashAttribute("alert_success", "Successful update.");
        return "redirect:" + uriBuilder.path("/gym/edit" + (id == null ? "" : ("/" + id))).toUriString();
    }

    @GetMapping("/delete/{id}")
    public String remove(@PathVariable long id, Model model) {
        //TODO check if user can delete?
        GymDTO gym = gymFacade.findGymById(id);
        gymFacade.removeGym(gym);
        return "gym/edit";
    }

    @GetMapping("/create")
    public String create(Model model) {
        //TODO check if user can create?
        GymDTO gymForm = new GymDTO();
        model.addAttribute("editForm", gymForm);
        model.addAttribute("types", PokemonType.values());
        return "gym/edit";
    }
}
