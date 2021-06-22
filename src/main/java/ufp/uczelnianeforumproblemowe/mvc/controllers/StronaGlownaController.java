package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WatekProcedureRepository;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.WatekView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class StronaGlownaController {

    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;

    private final WatekProcedureRepository watekProcedureRepository;

    public StronaGlownaController(@Autowired UzytkownikService uzytkownikService,
                                  @Autowired WatekService watekService,
                                  @Autowired WatekProcedureRepository watekProcedureRepository) {
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
        this.watekProcedureRepository = watekProcedureRepository;
    }

    @GetMapping
    public String home(Model model){

        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        // Pokazanie watkow glownych na podstawie ostatnio odwiedzanego forum.
        List<Watek> watekList = watekService.pobierzWszystkieWatkiGlowneNaPodstawieWydzialu(uzytkownik.getBierzacyWydzial());
        model.addAttribute("watekLista", watekList);

        // Pokazanie liczby podwątków danego wątku
        List<Integer> liczbaPodwatkow = new ArrayList<>();
        watekList.forEach(watek -> liczbaPodwatkow.add(watekProcedureRepository.pobierzWszystkiePodwatki(watek.getId()).get(0).intValue()));
        model.addAttribute("liczbaPodwatkow", liczbaPodwatkow);

        // Nie ma na stronie glownej tematow. Dzięki temu również sprawdzamy czy jesteśmy na głównej stronie czy też w wątkach.
        List<Temat> tematLista = null;
        model.addAttribute("tematLista", tematLista);

        // Dodawanie watku
        WatekView watekView = new WatekView();
        watekView.setIdRodzica(-1);
        model.addAttribute("watekView", watekView);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        obserwowani.forEach(uzytkownik1 -> System.out.println(uzytkownik1.getImie()));
        model.addAttribute("obserwowaniLista", obserwowani);
        return "Index";
    }
}
