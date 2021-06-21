package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.logic.plikService.PlikService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;

import java.util.List;

@Controller
public class ProfilController {

    private final UzytkownikService uzytkownikService;
    private final PlikService plikService;

    public ProfilController(@Autowired UzytkownikService uzytkownikService,
                            @Autowired PlikService plikService) {
        this.uzytkownikService = uzytkownikService;
        this.plikService = plikService;
    }

    @GetMapping("/profil/{id}")
    public String pobierzProfilUzytkownika(@PathVariable("id") long id, Model model){
        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat zalogowanego uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        // Do pokazania info na temat wybranego uzytkownika
        Uzytkownik ogladanyUzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieId(id);
        ogladanyUzytkownik.getRanga();
        model.addAttribute("ogladanyUzytkownik", ogladanyUzytkownik);

        List<Plik> plikiUzytkownika = plikService.sciagnijPlikiUzytkownika(id);
        model.addAttribute("plikiUzytkownika", plikiUzytkownika);

        return "Profil";
    }
}
