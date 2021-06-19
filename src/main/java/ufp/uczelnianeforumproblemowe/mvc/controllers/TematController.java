package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufp.uczelnianeforumproblemowe.jpa.models.*;
import ufp.uczelnianeforumproblemowe.logic.postService.PostService;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.TematView;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.WatekView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TematController {

    private final TematService tematService;
    private final PostService postService;
    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;

    public TematController(@Autowired TematService tematService,
                           @Autowired PostService postService,
                           @Autowired UzytkownikService uzytkownikService,
                           @Autowired WatekService watekService) {
        this.tematService = tematService;
        this.postService = postService;
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
    }

    @GetMapping("/temat/{id}")
    public String ZwrocTemat(@PathVariable("id") long id, Model model){
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

    @PostMapping("/temat/add")
    public String dodajNowyTemat(@ModelAttribute @Valid TematView tematView, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wrongNameForTemat","Nazwa musi zawierać się od 3 do 20 znakow a opis do 100 znakow!");
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

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

        return "TematEdycja";
    }

    @PostMapping("/temat/update")
    public String zaktualizujTemat(@ModelAttribute("tematView")@Valid TematView tematView, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

//        if(bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("wrongNameForTemat","Nazwa musi zawierać się do 30 znakow.");
//            return "redirect:/temat/update/" + tematView.getIdRodzica();
//        }

        Temat temat = tematService.znajdzTematPoId(tematView.getId());
//        if(temat == null){
//            model.addAttribute("tematView", tematView);
//            redirectAttributes.addFlashAttribute("wrongIdTemat","Nie ma takiego tematu..");
//            return "redirect:/watek/update/" + watekView.getId();
//            return "redirect:/";
//        }


        // Jeśli nazwa jest pusta to mamy nie zmieniać nazwy
        if(!tematView.getNazwa().equals("")) temat.setNazwa(tematView.getNazwa());
        if(!tematView.getOpis().equals("")) temat.setOpis(tematView.getOpis());

        // Jeśli ktoś wpisał 0 to nic
        // Jeśli ktoś wpisał wartość to ma nam zmienic rodzica
        // Jeśli ktoś wpisał -1 to ma nam zmienic watek na glowny

        switch((int)tematView.getIdRodzica()){
            case 0:
                tematService.zapiszTemat(temat);
                return "redirect:/temat/update/" + tematView.getId();
            default:
                Watek rodzic = watekService.znajdzWatekNaPodstawieId(tematView.getIdRodzica());
                if (rodzic == null) {
                    model.addAttribute("tematView", tematView);
                    redirectAttributes.addFlashAttribute("wrongIdRodzicaTemat", "Nie ma takiego id rodzica..");
                }
                else {
                    temat.setWatek(rodzic);
                    tematService.zapiszTemat(temat);
                }
                return "redirect:/temat/update/" + tematView.getId();
        }
    }
}