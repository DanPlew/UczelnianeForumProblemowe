package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ufp.uczelnianeforumproblemowe.jpa.models.Obserwowani;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.repositories.ObserwowaniRepository;
import ufp.uczelnianeforumproblemowe.logic.plikService.PlikService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfilController {

    private final UzytkownikService uzytkownikService;
    private final PlikService plikService;
    private final ObserwowaniRepository obserwowaniRepository;

    public ProfilController(@Autowired UzytkownikService uzytkownikService,
                            @Autowired PlikService plikService,
                            @Autowired ObserwowaniRepository obserwowaniRepository) {
        this.uzytkownikService = uzytkownikService;
        this.plikService = plikService;
        this.obserwowaniRepository = obserwowaniRepository;
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
}
