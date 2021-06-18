package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;

import java.util.List;

@Controller
public class StronaGlownaController {

    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;

    public StronaGlownaController(@Autowired UzytkownikService uzytkownikService,
                                  @Autowired WatekService watekService,
                                  @Autowired TematService tematService) {
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
    }

    @GetMapping
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        uzytkownik.setBierzacyWydzial(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("uzytkownik", uzytkownik);

        List<Watek> watekList = watekService.pobierzWszystkieWatkiGlowneNaPodstawieWydzialu(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("watekLista", watekList);
        return "StronaGlowna";
    }

    @GetMapping("/podWatek/usun/{id}")
    public String usunWatek(@PathVariable("id") long id, Model model){
        watekService.usunWatek(id);
        return "redirect:/";
    }
}
