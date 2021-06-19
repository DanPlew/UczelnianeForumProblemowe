package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.wydzialService.WydzialService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.WydzialView;

import java.util.List;

@Controller
public class ForumController {

    private final UzytkownikService uzytkownikService;
    private final WydzialService wydzialService;

    public ForumController(@Autowired UzytkownikService uzytkownikService,
                           @Autowired WydzialService wydzialService) {
        this.uzytkownikService = uzytkownikService;
        this.wydzialService = wydzialService;
    }

    @GetMapping("/zmianaForum")
    public String zmienForumHtml(Model model){
        // Do pokazania info na temat uzytkownika
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        List<Wydzial> wydzialLista = wydzialService.pobierzWszystkieWydzialy();
        wydzialLista.removeIf(wydzial -> wydzial.getNazwa() == uzytkownik.getBierzacyWydzial());
        model.addAttribute("wydzialy", wydzialLista);

        WydzialView wydzialView = new WydzialView();
        model.addAttribute("wydzialView", wydzialView);

        return "ForumChanger";
    }

    @PostMapping("/zmianaForum")
    public String zmienForum(@ModelAttribute("wydzialView") WydzialView wydzialView){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        uzytkownik.setBierzacyWydzial(wydzialView.getWydzialEnum());
        uzytkownikService.zapiszUzytkownika(uzytkownik);
        return "redirect:/";
    }
}
