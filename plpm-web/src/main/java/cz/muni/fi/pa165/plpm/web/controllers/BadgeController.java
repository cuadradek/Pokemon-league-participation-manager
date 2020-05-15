package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jakub Doczy
 */
@Controller
@RequestMapping("/badge")
public class BadgeController {

    @Autowired
    private BadgeFacade badgeFacade;

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("badges", badgeFacade.getAllBadges());

        return "badge/list";
    }

}
