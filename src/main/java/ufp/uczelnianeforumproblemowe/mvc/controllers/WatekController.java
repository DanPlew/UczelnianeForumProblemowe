package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WatekProcedureRepository;
import ufp.uczelnianeforumproblemowe.logic.postService.PostService;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.watekService.WatekService;
import ufp.uczelnianeforumproblemowe.logic.wydzialService.WydzialService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.TematView;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.WatekView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WatekController {

    private final UzytkownikService uzytkownikService;
    private final WatekService watekService;
    private final TematService tematService;
    private final WydzialService wydzialService;
    private final WatekProcedureRepository watekProcedureRepository;
    private final PostService postService;

    public WatekController(@Autowired UzytkownikService uzytkownikService,
                           @Autowired WatekService watekService,
                           @Autowired TematService tematService,
                           @Autowired WydzialService wydzialService,
                           @Autowired WatekProcedureRepository watekProcedureRepository,
                           @Autowired PostService postService) {
        this.uzytkownikService = uzytkownikService;
        this.watekService = watekService;
        this.tematService = tematService;
        this.wydzialService = wydzialService;
        this.watekProcedureRepository = watekProcedureRepository;
        this.postService = postService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
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

        // Pokazanie liczby podwątków danego wątku
        List<Integer> liczbaPodwatkow = new ArrayList<>();
        watekList.forEach(watek -> liczbaPodwatkow.add(watekProcedureRepository.pobierzWszystkiePodwatki(watek.getId()).get(0).intValue()));
        model.addAttribute("liczbaPodwatkow", liczbaPodwatkow);

        // Pokazanie tematów danego wątku
        List<Temat> tematLista = tematService.pobierzWszystkieTematyNaPodstawieWatku(id);
        model.addAttribute("tematLista", tematLista);

        List<Integer> liczbaPostow = new ArrayList<>();
        tematLista.forEach(temat -> liczbaPostow.add(postService.pobierzWszystkiePostyNaPodstawieTematu(temat.getId()).size()));
        model.addAttribute("liczbaPostow", liczbaPostow);

        // Dodawanie watku
        WatekView watekView = new WatekView();
        watekView.setIdRodzica(id);
        model.addAttribute("watekView", watekView);

        // Dodawanie tematu
        TematView tematView = new TematView();
        tematView.setIdRodzica(id);
        model.addAttribute("tematView", tematView);

        // Opcja cofania, potrzebny był nam id rodzica nadwądku by się cofnąć.
        WatekView opcjaPowrotu = new WatekView();
        long idCofanie;
        Watek watek = watekService.znajdzWatekNaPodstawieId(id);
        if(watekService.znajdzWatekNaPodstawieId(id).getParentWatek() != null){
            idCofanie = watek.getParentWatek().getId();
            opcjaPowrotu.setIdRodzica(idCofanie);
        }
        else{
            idCofanie = -1;
            opcjaPowrotu.setIdRodzica(idCofanie);
        }
        opcjaPowrotu.setNazwaRodzica(watek.getNazwa());
        model.addAttribute("opcjaPowrotuWatekView", opcjaPowrotu);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        obserwowani.forEach(uzytkownik1 -> System.out.println(uzytkownik1.getImie()));
        model.addAttribute("obserwowaniLista", obserwowani);

        return "Index";
    }

    @GetMapping("/watek/delete/{id}")
    public String usunWatekIZwrocIndex(@PathVariable("id") long id){
        Watek watek = watekService.znajdzWatekNaPodstawieId(id);
        if(watek != null) watekService.usunWatek(id);

        // Jeśli usuwany wątek nie miał rodzica to mamy przejsc na strone glowna, w innym przypadku tam gdzie go usuwalismy.
        if(watek.getParentWatek() == null) return "redirect:/";
        else return "redirect:/watek/" + watek.getParentWatek().getId();
    }

    @GetMapping("/watek/update/{id}")
    public String pobierzWatekEdycja(@PathVariable("id") long id, Model model){
        // Do pokazywania odpowiednich elementów na końcie admina i moderatora
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String rola = auth.getAuthorities().toString();
        model.addAttribute("rola", rola);

        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        uzytkownik.setBierzacyWydzial(uzytkownik.getWydzial().getNazwa());
        model.addAttribute("uzytkownik", uzytkownik);

        // Do którego wątku chcemy przypisać edytowany wątek.
        List<Watek> wszystkieWatki = watekService.pobierzWszystkieWatki();
        wszystkieWatki.removeIf(watek -> watek.getId() == id);
        model.addAttribute("wszystkieWatki", wszystkieWatki);

        // Informacje na temat edytowanego wątku.
        Watek watek = watekService.znajdzWatekNaPodstawieId(id);
        model.addAttribute("watek", watek);

        // Edycja wątku.
        WatekView watekView = new WatekView();
        model.addAttribute("watekView", watekView);

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        obserwowani.forEach(uzytkownik1 -> System.out.println(uzytkownik1.getImie()));
        model.addAttribute("obserwowaniLista", obserwowani);

        return "WatekEdycja";
    }

    @PostMapping("/watek/update")
    public String zaktualizujWatek(@ModelAttribute("watekView")@Valid WatekView watekView, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wrongNameForWatek","Nazwa musi zawierać się do 50 znakow.");
            return "redirect:/watek/update/" + watekView.getId();
        }

        Watek watek = watekService.znajdzWatekNaPodstawieId(watekView.getId());
        if(watek == null){
            redirectAttributes.addFlashAttribute("watekNotFound","Nie ma takiego watku..");
            return "redirect:/";
        }

        // Jeśli nazwa jest pusta to mamy nie zmieniać nazwy
        if(!watekView.getNazwa().equals("")) watek.setNazwa(watekView.getNazwa());

        // 0 to nic
        // wartość to ma nam zmienic rodzica
        // -1 to ma nam zmienic watek na glowny

        switch((int)watekView.getIdRodzica()){
            case -1:
                watek.setParentWatek(null);
                watekService.zapiszWatek(watek);
                return "redirect:/watek/update/" + watekView.getId();
            case 0:
                watekService.zapiszWatek(watek);
                return "redirect:/watek/update/" + watekView.getId();
            default:
                Watek rodzic = watekService.znajdzWatekNaPodstawieId(watekView.getIdRodzica());
                if (rodzic == null) {
                    model.addAttribute("watelView", watekView);
                    redirectAttributes.addFlashAttribute("wrongIdRodzicaWatek", "Nie ma takiego id rodzica..");
                }
                else {
                    watek.setParentWatek(rodzic);
                    watekService.zapiszWatek(watek);
                }
                return "redirect:/watek/update/" + watekView.getId();
        }
    }

    @PostMapping("watek/add")
    public String dodajWatek(@ModelAttribute("watekView") @Valid WatekView watekView, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("failedToAddWatek", "Nazwa musi zawierac do 30 znaków!");
            if(watekView.getIdRodzica() == -1) return "redirect:/";
            else return "redirect:/watek/" + watekView.getIdRodzica();
        }

        if(watekView.getNazwa().equals("")){
            redirectAttributes.addFlashAttribute("failedToAddWatek", "Nazwa nie moze być pusta!");
            if(watekView.getIdRodzica() == -1) return "redirect:/";
            else return "redirect:/watek/" + watekView.getIdRodzica();
        }
        Watek watek = new Watek(watekView.getNazwa());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        watek.setUzytkownik(uzytkownik);

        Wydzial wydzial = wydzialService.znajdzWydzialNaPodstawieNazwy(uzytkownik.getBierzacyWydzial());
        watek.setWydzial(wydzial);


        // Jeśli tworzymy wątek na stronie głównej to przekieruj na strone główną
        if(watekView.getIdRodzica() == -1) {
            watek.setParentWatek(null);
            watekService.zapiszWatek(watek);
            return "redirect:/";
        }
        // W przeciwnym wyrazie przekieruj tam gdzie tworzymy wątek
        else {
            Watek watekRodzic = watekService.znajdzWatekNaPodstawieId(watekView.getIdRodzica());
            watek.setParentWatek(watekRodzic);
            watekService.zapiszWatek(watek);
            return "redirect:/watek/" + watekView.getIdRodzica();
        }
    }
}
