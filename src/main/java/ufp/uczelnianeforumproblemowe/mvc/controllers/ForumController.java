package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WatekRepository;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;
import ufp.uczelnianeforumproblemowe.logic.wydzialService.WydzialService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.WydzialView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ForumController {

    private final UzytkownikService uzytkownikService;
    private final WydzialService wydzialService;
    private final WatekService watekService;
    private final TematService tematService;

    public ForumController(@Autowired UzytkownikService uzytkownikService,
                           @Autowired WydzialService wydzialService,
                           @Autowired WatekService watekService,
                           @Autowired TematService tematService) {
        this.uzytkownikService = uzytkownikService;
        this.wydzialService = wydzialService;
        this.watekService = watekService;
        this.tematService = tematService;
    }

    @GetMapping("/zmianaForum")
    public String zmienForumHtml(Model model){

        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        List<Wydzial> wydzialLista = wydzialService.pobierzWszystkieWydzialy();
        wydzialLista.removeIf(wydzial -> wydzial.getNazwa() == uzytkownik.getBierzacyWydzial());
        model.addAttribute("wydzialy", wydzialLista);

        List<Integer> watkiWwydziale = new ArrayList<>();
        wydzialLista.forEach(wydzial -> watkiWwydziale.add(watekService.pobierzWszystkieWatkiNaPodstawieWydzialu(wydzial.getNazwa())));
        model.addAttribute("liczbaWatkowWWydziale", watkiWwydziale);

        List<Integer> uzytkownicyWWydziale = new ArrayList<>();
        wydzialLista.forEach(wydzial -> uzytkownicyWWydziale.add(uzytkownikService.pobierzWszystkichUzytkownikowNaPodstawieWydzialu(wydzial.getNazwa())));
        model.addAttribute("iloscUczniowWWydziale", uzytkownicyWWydziale);

        List<Integer> tematyWWydziale = new ArrayList<>();
        wydzialLista.forEach(wydzial -> tematyWWydziale.add(tematService.pobierzWszystkieTematyWedlugWydzialu(wydzial.getNazwa())));
        model.addAttribute("iloscTematowWWydziale", tematyWWydziale);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        model.addAttribute("obserwowaniLista", obserwowani);

        return "ForumChanger";
    }


    @GetMapping("/zmianaForum/{id}")
    public String zmienForum(@PathVariable(name = "id") long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        Wydzial wydzial = wydzialService.pobierzWydzialNaPodstawieId(id);
        uzytkownik.setBierzacyWydzial(wydzial.getNazwa());
        uzytkownikService.zapiszUzytkownika(uzytkownik);
        return "redirect:/";
    }
}
