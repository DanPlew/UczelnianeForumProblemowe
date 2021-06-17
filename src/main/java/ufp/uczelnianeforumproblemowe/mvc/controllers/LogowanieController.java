package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogowanieController {
    @GetMapping("/login")
    public String logowanie(){
        return "Login";
    }

}
