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
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.WatekView;

import java.util.List;

@Controller
public class StronaGlownaController {

    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;

    public StronaGlownaController(@Autowired UzytkownikService uzytkownikService,
                                  @Autowired WatekService watekService) {
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
    }

    @GetMapping
    public String home(Model model){

        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
//        uzytkownik.setBierzacyWydzial(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("uzytkownik", uzytkownik);

        // Pokazanie watkow glownych na podstawie ostatnio odwiedzanego forum.
        List<Watek> watekList = watekService.pobierzWszystkieWatkiGlowneNaPodstawieWydzialu(uzytkownik.getBierzacyWydzial());
        model.addAttribute("watekLista", watekList);

        // Nie ma na stronie glownej tematow
        List<Temat> tematLista = null;
        model.addAttribute("tematLista", tematLista);

        // Dodawanie watku
        WatekView watekView = new WatekView();
        watekView.setIdRodzica(-1);
        model.addAttribute("watekView", watekView);
        return "Index";
    }
}
