package cz.muni.fi.pa165.plpm.web.controllers;

import cz.muni.fi.pa165.plpm.dto.BadgeDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerAuthenticateDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerChangePasswordDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerCreateDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerDTO;
import cz.muni.fi.pa165.plpm.dto.TrainerUpdateInfoDTO;
import cz.muni.fi.pa165.plpm.entity.Trainer;
import cz.muni.fi.pa165.plpm.exceptions.PlpmServiceException;
import cz.muni.fi.pa165.plpm.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.plpm.service.facade.BadgeFacade;
import cz.muni.fi.pa165.plpm.service.facade.GymFacade;
import cz.muni.fi.pa165.plpm.service.facade.PokemonFacade;
import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private TrainerFacade trainerFacade;

    @Autowired
    private GymFacade gymFacade;

    @Autowired
    private PokemonFacade pokemonFacade;

    @Autowired
    private BadgeFacade badgeFacade;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false);
        binder.registerCustomEditor(Date.class, editor);
    }

    @GetMapping(value = "/list")
    public String list(Model model) {
        model.addAttribute("trainers", trainerFacade.findAllTrainers());

        return "trainer/list";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model, Principal principal) {
        TrainerDTO trainerDTO = trainerFacade.findTrainerById(id);

        model.addAttribute("trainer", trainerDTO);
        model.addAttribute("gym", gymFacade.findGymByTrainer(id));
        model.addAttribute("pokemons", pokemonFacade.getPokemonByTrainer(trainerDTO));
        model.addAttribute("badges", badgeFacade.getBadgesByTrainerId(id));

        if (principal.getName().equals(trainerDTO.getNickname()))
            model.addAttribute("viewSelf", true);

        return "trainer/view";
    }

//    @GetMapping("/list/filter")
//    public String listByNickname(@RequestParam(required = false) String nickname,
//                                 @RequestParam(required = false) String firstName,
//                                 @RequestParam(required = false) String lastName,
//                                 Model model) {
//        List<TrainerDTO> trainers = new ArrayList<>();
//        if (nickname != null) trainers.add(trainerFacade.findTrainerByNickname(nickname));
//        if (firstName != null) trainers
//        TrainerDTO trainerDTO = trainerFacade.findTrainerByNickname(nickname);
//        if (trainerDTO != null) trainers.add(trainerDTO);
//
//        model.addAttribute("trainers", trainers);
//        return "trainer/list";
//    }

//    @GetMapping("/listt")
//    public String listByFirstName(@RequestParam String firstName, Model model) {
//        model.addAttribute("trainers", trainerFacade.findTrainerByFirstName(firstName));
//        return "trainer/list";
//    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable long id, UriComponentsBuilder uriBuilder, RedirectAttributes redirectAttributes) {
        trainerFacade.deleteTrainer(id);
        redirectAttributes.addFlashAttribute("alert_success", "Trainer was deleted.");

        return "redirect:" + uriBuilder.path("/trainer/view").toUriString();
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new TrainerAuthenticateDTO());
        return "trainer/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") TrainerAuthenticateDTO loginForm,
                        BindingResult bindingResult,
                        Model model,
                        UriComponentsBuilder uriBuilder,
                        RedirectAttributes redirectAttributes) {
        model.addAttribute("loginForm", new TrainerAuthenticateDTO());
        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "trainer/login";
        }

        if (trainerFacade.authenticate(loginForm)) {
            redirectAttributes.addFlashAttribute("alert_success", "Successful login.");
            return "redirect:" + uriBuilder.path("/").toUriString();
        } else {
            model.addAttribute("alert_warning", "Unsuccessful login.");
            return "trainer/login";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("trainerForm", new TrainerCreateDTO());
        return "trainer/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("trainerForm") TrainerCreateDTO trainerForm,
                           BindingResult bindingResult,
                           Model model,
                           UriComponentsBuilder uriBuilder,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "trainer/register";
        }

        try {
            trainerFacade.createTrainer(trainerForm);
        } catch (PlpmServiceException ex) {
            bindingResult.addError(new FieldError("trainer", "nickname", ex.getMessage()));
            model.addAttribute("nickname_error", true);
            return "trainer/register";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Successful registration. You can login now.");
        return "redirect:" + uriBuilder.path("/trainer/login").toUriString();
    }

    @GetMapping("/edit")
    public String editSelf(Model model, Principal principal) {
        TrainerChangePasswordDTO passwordForm = new TrainerChangePasswordDTO();
        Long id = trainerFacade.findTrainerByNickname(principal.getName()).getId();
        passwordForm.setId(id);
        model.addAttribute("passwordForm", passwordForm);

        return edit(model, id);
    }

    @GetMapping("/edit/{id}")
    public String editAnotherUser(@PathVariable long id, Model model) {
        model.addAttribute("editAnother", true);

        return edit(model, id);
    }

    private String edit(Model model, long id) {
        TrainerUpdateInfoDTO editForm = new TrainerUpdateInfoDTO();
        TrainerDTO trainerDTO = trainerFacade.findTrainerById(id);
        editForm.setId(trainerDTO.getId());
        editForm.setBirthDate(trainerDTO.getBirthDate());
        editForm.setFirstName(trainerDTO.getFirstName());
        editForm.setLastName(trainerDTO.getLastName());
        editForm.setNickname(trainerDTO.getNickname());
        model.addAttribute("editForm", editForm);
        return "trainer/edit";
    }

    @PostMapping(value = {"/edit", "/edit/{id}"})
    public String edit(@Valid @ModelAttribute("editForm") TrainerUpdateInfoDTO editForm,
                       BindingResult bindingResult,
                       @PathVariable(required = false) Long id,
                       Model model,
                       RedirectAttributes redirectAttributes,
                       UriComponentsBuilder uriBuilder) {
        if (id != null) model.addAttribute("editAnother", true);

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
            }
            return "trainer/edit";
        }

        try {
            trainerFacade.updateTrainerInfo(editForm);
        } catch (PlpmServiceException ex) {
            bindingResult.addError(new FieldError("trainer", "nickname", ex.getMessage()));
            model.addAttribute("nickname_error", true);
            return "trainer/edit";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Successful update.");
        return "redirect:" + uriBuilder.path("/trainer/edit" + (id == null ? "" : ("/" + id))).toUriString();
    }



    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        TrainerChangePasswordDTO passwordForm = new TrainerChangePasswordDTO();
        passwordForm.setId(trainerFacade.findTrainerByNickname(principal.getName()).getId());
        model.addAttribute("passwordForm", passwordForm);
        return "trainer/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute("passwordForm") TrainerChangePasswordDTO passwordForm,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 UriComponentsBuilder uriBuilder) {

        if (bindingResult.hasErrors()) {
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                return "trainer/change-password";
            }
        }

        if (!trainerFacade.changePassword(passwordForm)) {
            bindingResult.addError(new FieldError("trainer", "oldPassword", "Password is incorrect."));
            model.addAttribute("oldPassword_error", true);
            return "trainer/change-password";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Successful update.");
        return "redirect:" + uriBuilder.path("/trainer/change-password").toUriString();
    }
}
