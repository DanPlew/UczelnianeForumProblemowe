package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ufp.uczelnianeforumproblemowe.jpa.models.Post;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.logic.postService.PostService;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;

import java.util.List;

@Controller
public class TematController {

    private final TematService tematService;
    private final PostService postService;
    private final UzytkownikService uzytkownikService;

    public TematController(@Autowired TematService tematService,
                           @Autowired PostService postService,
                           @Autowired UzytkownikService uzytkownikService) {
        this.tematService = tematService;
        this.postService = postService;
        this.uzytkownikService = uzytkownikService;
    }

    @GetMapping("/temat/{id}")
    public String pobierzTematIZwrocTemat(@PathVariable("id") long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        uzytkownik.setBierzacyWydzial(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("uzytkownik", uzytkownik);

        List<Temat> tematLista = tematService.pobierzWszystkieTematyNaPodstawieWatku(id);
        model.addAttribute("tematLista", tematLista);

        List<Post> postLista = postService.pobierzWszystkiePostyNaPodstawieTematu(id);
        model.addAttribute("postLista", postLista);

        return "Temat";
    }
}
