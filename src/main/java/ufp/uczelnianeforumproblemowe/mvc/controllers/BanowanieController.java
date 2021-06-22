package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Zgloszenie;
import ufp.uczelnianeforumproblemowe.jpa.repositories.ZgloszenieRepository;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BanowanieController {

    private final ZgloszenieRepository zgloszenieRepository;
    private final UzytkownikService uzytkownikService;

    public BanowanieController(@Autowired ZgloszenieRepository zgloszenieRepository,
                               @Autowired UzytkownikService uzytkownikService) {
        this.zgloszenieRepository = zgloszenieRepository;
        this.uzytkownikService = uzytkownikService;
    }

    @GetMapping("/administrator/listaUzytkownikow")
    public String pobierzListeUzytkownikow(Model model){

        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        obserwowani.forEach(uzytkownik1 -> System.out.println(uzytkownik1.getImie()));
        model.addAttribute("obserwowaniLista", obserwowani);

        List<Uzytkownik> listaUzytkownikow = uzytkownikService.pobierzWszystkichUzytkownikow();
        List<Uzytkownik> listaUzytkownikowSorted = listaUzytkownikow.stream().sorted(Comparator.comparing(Uzytkownik::getLiczbaZgloszen).reversed()).collect(Collectors.toList());
        model.addAttribute("uzytkownikList", listaUzytkownikowSorted);

        List<Zgloszenie> zgloszenia = zgloszenieRepository.findAll();
        model.addAttribute("listaZgloszen", zgloszenia);

        return "Uzytkownicy";
    }
}
