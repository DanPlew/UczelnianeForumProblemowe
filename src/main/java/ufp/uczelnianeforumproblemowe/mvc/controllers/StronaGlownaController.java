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
import ufp.uczelnianeforumproblemowe.jpa.repositories.TematRepository;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WatekRepository;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Controller
public class StronaGlownaController {

    private final UzytkownikService uzytkownikService;
    private final WatekRepository watekRepository;
    private final TematRepository tematRepository;

    public StronaGlownaController(@Autowired UzytkownikService uzytkownikService,
                                  @Autowired WatekRepository watekRepository,
                                  @Autowired TematRepository tematRepository) {
        this.uzytkownikService = uzytkownikService;
        this.watekRepository = watekRepository;
        this.tematRepository = tematRepository;
    }

    @GetMapping
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);
        List<Watek> watekList = watekRepository.pobierzWszystkieWatkiGlowne(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("watekLista", watekList);
        return "StronaGlowna";
    }

    @GetMapping("/podWatek/{id}")
    public String pobierzPodWatek(@PathVariable("id") long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);
        List<Watek> watekList = watekRepository.pobierzWszystkiePodWatki(uzytkownik.getWydzial().getNazwa(), id);
        model.addAttribute("watekLista", watekList);

        List<Temat> tematLista = tematRepository.pobierzWszystkieTematy(id);
        model.addAttribute("tematLista", tematLista);
        return "PodWatki";
    }
}

// Zrobić przesyłanie danych..
// /topic${allInstanceTheme.id}/0
