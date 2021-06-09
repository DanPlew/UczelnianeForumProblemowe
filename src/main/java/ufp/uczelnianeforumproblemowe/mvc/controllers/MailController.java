package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;

@RestController
@RequestMapping(path = "api/v1/registration")
public class MailController {

    private final UzytkownikService uzytkownikService;

    public MailController(@Autowired UzytkownikService uzytkownikService){
        this.uzytkownikService = uzytkownikService;
    }

    @PostMapping
    public void register(@RequestBody UzytkownikView request) {
        try {
            uzytkownikService.rejestracja(request);
        }catch (Exception e) {
        }
    }

    @GetMapping(path = "confirm")
    public RedirectView confirm(@RequestParam("token") String token) {
        try {
            uzytkownikService.sprawdzenieTokena(token);
            return new RedirectView("/login");
        }catch (IllegalStateException e){
            return new RedirectView("/wygasniecie");
        }
    }
}
