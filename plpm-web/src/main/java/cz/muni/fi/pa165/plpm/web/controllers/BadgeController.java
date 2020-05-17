package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.BadgeDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import cz.muni.fi.pa165.plpm.service.facade.GymFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
