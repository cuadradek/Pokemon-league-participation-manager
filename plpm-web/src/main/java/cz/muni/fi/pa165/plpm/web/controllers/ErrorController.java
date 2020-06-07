package cz.muni.fi.pa165.plpm.web.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling errors - creating custom error pages.
 */
@Controller
public class ErrorController {

    @GetMapping(value = "errors")
    public String renderErrorPage() {
        return "error";
    }
}