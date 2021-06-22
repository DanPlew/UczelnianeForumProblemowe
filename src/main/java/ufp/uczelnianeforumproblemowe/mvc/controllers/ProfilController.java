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
import ufp.uczelnianeforumproblemowe.jpa.models.Obserwowani;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Zgloszenie;
import ufp.uczelnianeforumproblemowe.jpa.repositories.ObserwowaniRepository;
import ufp.uczelnianeforumproblemowe.jpa.repositories.ZgloszenieRepository;
import ufp.uczelnianeforumproblemowe.logic.plikService.PlikService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.ZgloszenieView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfilController {

    private final UzytkownikService uzytkownikService;
    private final PlikService plikService;
    private final ObserwowaniRepository obserwowaniRepository;
    private final ZgloszenieRepository zgloszenieRepository;

    public ProfilController(@Autowired UzytkownikService uzytkownikService,
                            @Autowired PlikService plikService,
                            @Autowired ObserwowaniRepository obserwowaniRepository,
                            @Autowired ZgloszenieRepository zgloszenieRepository) {
        this.uzytkownikService = uzytkownikService;
        this.plikService = plikService;
        this.obserwowaniRepository = obserwowaniRepository;
        this.zgloszenieRepository = zgloszenieRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
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

        // Lista obserwowanych
        List<Uzytkownik> obserwowani = uzytkownikService.pobierzObserwujacych(uzytkownik.getId());
        model.addAttribute("obserwowaniLista", obserwowani);

        boolean czyWZnajomych = false;
        for(int i=0;i<obserwowani.size();i++){
            if(obserwowani.get(i).getId() == ogladanyUzytkownik.getId()){
                czyWZnajomych = true;
                break;
            }
        }
        model.addAttribute("czyWZnajomych", czyWZnajomych);

        ZgloszenieView zgloszenieView = new ZgloszenieView();
        model.addAttribute("zgloszenieView", zgloszenieView);

        return "Profil";
    }

    @GetMapping("/obserwowani/add/{id}")
    public String dodajUzytkownikaDoObserwowanych(@PathVariable("id") long id){
        //Uzytkownik do obserwowania
        Uzytkownik uzytkownikDoObserwowania = uzytkownikService.znajdzUzytkownikaNaPodstawieId(id);

        //Zalogowany uzytkownik
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());

        Obserwowani obserwowani = new Obserwowani(uzytkownik, uzytkownikDoObserwowania);
        obserwowaniRepository.save(obserwowani);

        return "redirect:/profil/" + uzytkownikDoObserwowania.getId();
    }

    @GetMapping("/obserwowani/delete/{id}")
    public String usunUzytkownikaZObserwujacych(@PathVariable("id") long id){
        //Uzytkownik do obserwowania
        Uzytkownik uzytkownikDoObserwowania = uzytkownikService.znajdzUzytkownikaNaPodstawieId(id);

        //Zalogowany uzytkownik
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());

        Obserwowani obserwowani = obserwowaniRepository.getObserwowaniByUzytkownikAndObserwowany(uzytkownik, uzytkownikDoObserwowania);
        obserwowaniRepository.delete(obserwowani);

        return "redirect:/profil/" + id;
    }

    @PostMapping("/zgloszenie/add")
    public String zglosUzytkownika(@ModelAttribute("zgloszenieView") ZgloszenieView zgloszenieView, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wrongZgloszenie","Zgloszenie nie  moze być dłuższe niż 2000 znaków!");
            return "redirect:/profil/" + zgloszenieView.getIdOskarzony();
        }

        if(zgloszenieView.getWiadomoscZgloszenia().equals("")){
            redirectAttributes.addFlashAttribute("wrongZgloszenie","Pole jest wymagane!");
            return "redirect:/profil/" + zgloszenieView.getIdOskarzony();
        }

        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieId(zgloszenieView.getIdUzytkownik());
        Uzytkownik oskarzony = uzytkownikService.znajdzUzytkownikaNaPodstawieId(zgloszenieView.getIdOskarzony());

        Zgloszenie zgloszenie = new Zgloszenie(zgloszenieView.getWiadomoscZgloszenia(), uzytkownik, oskarzony);
        zgloszenieRepository.save(zgloszenie);

        return "redirect:/profil/" + oskarzony.getId();
    }
}
