package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@Controller
public class LogowanieController {
    @GetMapping("/login")
    public String logowanie(){
        return "Login";
    }

}
