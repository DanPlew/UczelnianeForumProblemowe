package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;
import ufp.uczelnianeforumproblemowe.logic.wydzialService.WydzialService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.WatekView;

import java.util.List;

@Controller
public class WatekController {

    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;
    private final TematService tematService;
    private final WydzialService wydzialService;

    public WatekController(@Autowired UzytkownikService uzytkownikService,
                           @Autowired WatekService watekService,
                           @Autowired TematService tematService,
                           @Autowired WydzialService wydzialService) {
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
        this.tematService = tematService;
        this.wydzialService = wydzialService;
    }

    @GetMapping("/watek/{id}")
    public String pobierzWatekIZwrocIndex(@PathVariable("id") long id, Model model){
        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        // Do pokazania info na temat uzytkownika
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        model.addAttribute("uzytkownik", uzytkownik);

        // Pokazanie watków na podstawie id rodzica.
        List<Watek> watekList = watekService.pobierzWszystkiePodWatkiNaPodstawieRodzica(uzytkownik.getBierzacyWydzial(), id);
        model.addAttribute("watekLista", watekList);

        // Pokazanie tematów danego wątku
        List<Temat> tematLista = tematService.pobierzWszystkieTematyNaPodstawieWatku(id);
        model.addAttribute("tematLista", tematLista);

        // Obiekt widoku wątku gdybyśmy chcieli utworzyć nowy wątek
        WatekView watekView = new WatekView();
        watekView.setIdRodzica(id);
        model.addAttribute("watekView", watekView);

        return "Index";
    }

    @GetMapping("/watek/delete/{id}")
    public String usunWatekIZwrocIndex(@PathVariable("id") long id){
        Watek watek = watekService.znajdzWatekNaPodstawieId(id);
        watekService.usunWatek(id);
        if(watek.getParentWatek() == null) return "redirect:/";
        else return "redirect:/watek/" + watek.getParentWatek().getId();
    }

    @GetMapping("/watek/update/{id}")
    public String pobierzWatekEdycja(@PathVariable("id") long id, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        uzytkownik.setBierzacyWydzial(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("uzytkownik", uzytkownik);

        List<Watek> wszystkieWatki = watekService.pobierzWszystkieWatki();
        wszystkieWatki.removeIf(watek -> watek.getId() == id);
        model.addAttribute("wszystkieWatki", wszystkieWatki);

        Watek watek = watekService.znajdzWatekNaPodstawieId(id);
        model.addAttribute("watek", watek);

        WatekView watekView = new WatekView();
        model.addAttribute("watekView", watekView);

        return "WatekEdycja";
    }

    @PostMapping("/watek/update")
    public String zaktualizujWatek(@ModelAttribute("watekView") WatekView watekView, Model model, RedirectAttributes redirectAttributes){

        Watek watek = watekService.znajdzWatekNaPodstawieId(watekView.getId());
        if(watek == null){
            model.addAttribute("watelView", watekView);
            redirectAttributes.addFlashAttribute("wrongIdWatek","Nie ma takiego watku..");
            return"redirect:/podWatek/update/" + watekView.getId();
        }

        if(watekView.getNazwa() != "") watek.setNazwa(watekView.getNazwa());

        // Jeśli ktoś wpisał 0 to nic
        // Jeśli ktoś wpisał wartość to ma nam przekierować na pod rodzica
        // Jeśli ktoś wpisał -1 to ma nam przekierować na główną
        if(watekView.getIdRodzica() == -1) watek.setParentWatek(null);
        else if(watekView.getIdRodzica() != 0){
            Watek rodzic = watekService.znajdzWatekNaPodstawieId(watekView.getIdRodzica());
            if (rodzic == null) {
                model.addAttribute("watelView", watekView);
                redirectAttributes.addFlashAttribute("wrongIdRodzicaWatek", "Nie ma takiego id rodzica..");
                return "redirect:/podWatek/update/" + watekView.getId();
            }
            else watek.setParentWatek(rodzic);
        }
        watekService.zapiszWatek(watek);
        return "redirect:/";
    }

    @PostMapping("watek/add")
    public String dodajWatek(@ModelAttribute("nowyWatek")WatekView watekView){
        Watek watek = new Watek(watekView.getNazwa());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        watek.setUzytkownik(uzytkownik);
        Wydzial wydzial = wydzialService.znajdzWydzialNaPodstawieNazwy(uzytkownik.getBierzacyWydzial());
        watek.setWydzial(wydzial);
        if(watekView.getIdRodzica() == -1) {
            watek.setParentWatek(null);
            watekService.zapiszWatek(watek);
            return "redirect:/";
        }
        else {
            Watek watekRodzic = watekService.znajdzWatekNaPodstawieId(watekView.getIdRodzica());
            watek.setParentWatek(watekRodzic);
            watekService.zapiszWatek(watek);
            return "redirect:/watek/" + watekView.getIdRodzica();
        }
    }
}
