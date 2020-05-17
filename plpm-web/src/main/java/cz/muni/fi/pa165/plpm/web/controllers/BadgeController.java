package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.BadgeCreateDTO;
import cz.muni.fi.pa165.plpm.dto.BadgeDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import cz.muni.fi.pa165.plpm.service.facade.GymFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Jakub Doczy
 */
@Controller
@RequestMapping("/badge")
public class BadgeController {

    @Autowired
    private BadgeFacade badgeFacade;

    @Autowired
    private GymFacade gymFacade;

    @Autowired
    private TrainerFacade trainerFacade;

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("badges", badgeFacade.getAllBadges());

        return "badge/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {
        BadgeDTO badgeDTO = badgeFacade.getBadgeById(id);
        model.addAttribute("badge", badgeDTO);
        model.addAttribute("trainersGym", gymFacade.findGymByTrainer(badgeDTO.getTrainer().getId()));

        return "badge/view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        BadgeCreateDTO badgeCreateDTO = new BadgeCreateDTO();

        model.addAttribute("badgeCreate", badgeCreateDTO);
        model.addAttribute("trainers", trainerFacade.findAllTrainers());
        model.addAttribute("gyms", gymFacade.findAllGyms());

        return "badge/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("badgeCreate") BadgeCreateDTO badgeForm,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes,
                         UriComponentsBuilder uriBuilder,
                         Locale locale) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMessages.add(fe.getField() + "_error");
            }
            redirectAttributes.addFlashAttribute("messages", errorMessages);
            return "redirect:" + uriBuilder.path("/badge/create").toUriString();
        }

        try {
            badgeFacade.createBadge(badgeForm);
        } catch (PlpmServiceException ex) {
            //bindingResult.addError(new FieldError("badge", "trainerId", ex.getMessage()));
            //model.addAttribute("trainer_error", true);
            // redirect needed so we get current badges
            redirectAttributes.addFlashAttribute("alert_warning", ex.getMessage());
            return "redirect:" + uriBuilder.path("/badge/create").toUriString();
        }

        redirectAttributes.addFlashAttribute("alert_success", "Successfully created new badge.");
        return "redirect:" + uriBuilder.path("/badge/list").toUriString();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        BadgeDTO badgeDTO = badgeFacade.getBadgeById(id);
        badgeFacade.deleteBadge(id);
        redirectAttributes.addFlashAttribute("alert_success", "Badge of trainer " + badgeDTO.getTrainer().getNickname() +
                " from " + badgeDTO.getGym().getCity() + "was successfully deleted.");
        return "redirect:" + uriBuilder.path("/badge/list").toUriString();
    }




}
