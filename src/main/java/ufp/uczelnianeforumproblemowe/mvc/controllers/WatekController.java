package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;

import java.util.List;

@Controller
public class WatekController {

    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;
    private final TematService tematService;

    public WatekController(@Autowired UzytkownikService uzytkownikService,
                           @Autowired WatekService watekService,
                           @Autowired TematService tematService) {
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
        this.tematService = tematService;
    }

    @GetMapping("/podWatek/{id}")
    public String pobierzPodWatek(@PathVariable("id") long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        uzytkownik.setBierzacyWydzial(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("uzytkownik", uzytkownik);

        List<Watek> watekList = watekService.pobierzWszystkiePodWatkiNaPodstawieRodzica(uzytkownik.getWydzial().getNazwa(), id);
        model.addAttribute("watekLista", watekList);

        List<Temat> tematLista = tematService.pobierzWszystkieTematyNaPodstawieWatku(id);
        model.addAttribute("tematLista", tematLista);
        return "PodWatki";
    }
}
