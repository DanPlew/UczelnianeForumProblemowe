package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufp.uczelnianeforumproblemowe.jpa.models.*;
import ufp.uczelnianeforumproblemowe.logic.plikService.PlikService;
import ufp.uczelnianeforumproblemowe.logic.postService.PostService;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.PostView;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.TematView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
public class TematController {

    private final TematService tematService;
    private final PostService postService;
    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;
    private final PlikService plikService;

    public TematController(@Autowired TematService tematService,
                           @Autowired PostService postService,
                           @Autowired UzytkownikService uzytkownikService,
                           @Autowired WatekService watekService,
                           @Autowired PlikService plikService) {
        this.tematService = tematService;
        this.postService = postService;
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
        this.plikService = plikService;
    }

    @GetMapping("/temat/{id}")
    public String ZwrocTemat(@PathVariable("id") long id, Model model){
        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        List<Post> postLista = postService.pobierzWszystkiePostyNaPodstawieTematu(id);
        model.addAttribute("postLista", postLista);

        // Temat potrzebny do pokazania w jakim jestesmy temacie
        Temat biezacyTemat = tematService.znajdzTematPoId(id);
        model.addAttribute("biezacyTemat", biezacyTemat);

        PostView postView = new PostView();
        model.addAttribute("postView", postView);

        List<Plik> pliki = plikService.sciagnijPlikiUzytkownika(uzytkownik.getId());
        model.addAttribute("pliki", pliki);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        obserwowani.forEach(uzytkownik1 -> System.out.println(uzytkownik1.getImie()));
        model.addAttribute("obserwowaniLista", obserwowani);

        return "Temat";
    }

    @PostMapping("/temat/add")
    public String dodajNowyTemat(@ModelAttribute @Valid TematView tematView, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wrongNameForTemat","Nazwa musi zawierać się od 5 do 50 znakow a opis do 200 znakow!");
            return "redirect:/watek/" + tematView.getIdRodzica();
        }

        if(tematView.getOpis().equals("")){
            redirectAttributes.addFlashAttribute("wrongNameForTemat","Opis nie może być pusty!");
            return "redirect:/watek/" + tematView.getIdRodzica();
        }

        if(tematView.getNazwa().equals("")){
            redirectAttributes.addFlashAttribute("wrongNameForTemat","Nazwa nie może być pusta!");
            return "redirect:/watek/" + tematView.getIdRodzica();
        }

        Temat temat = new Temat(tematView.getNazwa(), tematView.getOpis());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        temat.setUzytkownik(uzytkownik);
        Watek watek = watekService.znajdzWatekNaPodstawieId(tematView.getIdRodzica());
        temat.setWatek(watek);
        tematService.zapiszTemat(temat);
        return "redirect:/watek/" + tematView.getIdRodzica();
    }

    @GetMapping("temat/delete/{id}")
    public String usunTematHTML(@PathVariable("id") long id){
        Temat temat = tematService.znajdzTematPoId(id);
        if(temat != null) tematService.usunTemat(temat);
        return "redirect:/watek/" + temat.getWatek().getId();
    }

    @GetMapping("/temat/update/{id}")
    public String pobierzWatekEdycja(@PathVariable("id") long id, Model model){
        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        uzytkownik.setBierzacyWydzial(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("uzytkownik", uzytkownik);

        // Do którego wątku chcemy przypisać edytowany temat.
        Temat temat = tematService.znajdzTematPoId(id);
        List<Watek> wszystkieWatki = watekService.pobierzWszystkieWatki();
//        wszystkieWatki.removeIf(watek -> watek.getId() == temat.getWatek().getId());
        model.addAttribute("wszystkieWatki", wszystkieWatki);

        // Informacje na temat edytowanego Tematu.
        model.addAttribute("temat", temat);

        // Edycja tematu.
        TematView tematView = new TematView();
        model.addAttribute("tematView", tematView);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        obserwowani.forEach(uzytkownik1 -> System.out.println(uzytkownik1.getImie()));
        model.addAttribute("obserwowaniLista", obserwowani);

        return "TematEdycja";
    }

    @PostMapping("/temat/update")
    public String zaktualizujTemat(@ModelAttribute("tematView")@Valid TematView tematView, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        Temat temat = tematService.znajdzTematPoId(tematView.getId());

        if(temat == null){
            model.addAttribute("tematView", tematView);
            redirectAttributes.addFlashAttribute("tematNotFound","Nie ma takiego tematu..");
            return "redirect:/";
        }


        // Jeśli nazwa jest pusta to mamy nie zmieniać nazwy
        if(!tematView.getNazwa().equals("")) {
            temat.setNazwa(tematView.getNazwa());
        }
        if(!tematView.getOpis().equals("")) {
            temat.setOpis(tematView.getOpis());
        }

        // 0 to nic
        // wartość to ma nam zmienic rodzica
        // -1 to ma nam zmienic watek na glowny

        switch((int)tematView.getIdRodzica()){
            case 0:
                tematService.zapiszTemat(temat);
                return "redirect:/temat/update/" + tematView.getId();
            default:
                Watek rodzic = watekService.znajdzWatekNaPodstawieId(tematView.getIdRodzica());
                if (rodzic == null) {
                    model.addAttribute("tematView", tematView);
                    redirectAttributes.addFlashAttribute("wrongIdRodzicaTemat", "Nie ma takiego watku..");
                }
                else {
                    temat.setWatek(rodzic);
                    tematService.zapiszTemat(temat);
                }
                return "redirect:/temat/update/" + tematView.getId();
        }
    }

    @GetMapping("/aktywnosc")
    public String pobierzAktywnoscHtml(Model model){
        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        model.addAttribute("obserwowaniLista", obserwowani);

        List<Temat> listaTematow = tematService.getTematByUzytkownik(uzytkownik);
        List<Temat> listaTematowPost = tematService.pobierzTematyGdzieWstawionoPost(uzytkownik.getId());
        List<Temat> listaAktywnosci = new ArrayList<>(listaTematowPost);
        listaAktywnosci.removeAll(listaTematow);
        listaTematow.addAll(listaAktywnosci);
        model.addAttribute("listaTematow", listaTematow);

        return "Aktywnosc";
    }

}
