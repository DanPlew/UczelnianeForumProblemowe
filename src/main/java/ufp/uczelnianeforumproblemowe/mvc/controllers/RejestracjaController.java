package ufp.uczelnianeforumproblemowe.mvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.logic.rejestracjaService.RejestracjaService;
import ufp.uczelnianeforumproblemowe.logic.tokenService.TokenWeryfikacyjnyService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.logic.wydzialService.WydzialService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class RejestracjaController {

    private final RejestracjaService rejestracjaService;
    private final UzytkownikService uzytkownikService;
    private final TokenWeryfikacyjnyService tokenWeryfikacyjnyService;
    private final WydzialService wydzialService;

    public RejestracjaController(@Autowired UzytkownikService uzytkownikService,
                                 @Autowired TokenWeryfikacyjnyService tokenWeryfikacyjnyService,
                                 @Autowired RejestracjaService rejestracjaService,
                                 @Autowired WydzialService wydzialService){
        this.uzytkownikService = uzytkownikService;
        this.tokenWeryfikacyjnyService = tokenWeryfikacyjnyService;
        this.rejestracjaService = rejestracjaService;
        this.wydzialService = wydzialService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/rejestracja")
    public String pobierzStroneRejestracji(Model model){
        UzytkownikView uzytkownikView = new UzytkownikView();
        model.addAttribute("uzytkownikView", uzytkownikView);
        model.addAttribute("wydzialyLista", wydzialService.pobierzWszystkieWydzialy());
        return "Rejestracja";
    }

    @PostMapping("/rejestracja")
    public String Rejestracja(@ModelAttribute("uzytkownikView") @Valid UzytkownikView uzytkownikView, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        model.addAttribute("wydzialyLista", wydzialService.pobierzWszystkieWydzialy());
        if(bindingResult.hasErrors()){
            model.addAttribute("uzytkownikView", uzytkownikView);
            return "Rejestracja";
        }

        if(uzytkownikService.sprawdzenieKontaNaPodstawieLoginu(uzytkownikView.getLogin())){
            bindingResult.addError(new FieldError("uzytkownikView", "login", "Taki login jest już zajęty!"));
            model.addAttribute("uzytkownikView", uzytkownikView);
            return "Rejestracja";
        }

        if(uzytkownikService.sprawdzenieKontaNaPodstawieMailaUczelnianego(uzytkownikView.getEmailUczelniany())){
            bindingResult.addError(new FieldError("uzytkownikView", "emailUczelniany", "Taki email uczelniany jest już zajęty!"));
            model.addAttribute("uzytkownikView", uzytkownikView);
            return "Rejestracja";
        }

        if(uzytkownikView.getEmailPrywatny() != null && uzytkownikService.sprawdzenieKontaNaPodstawieMailaPrywatnego(uzytkownikView.getEmailPrywatny())) {
            bindingResult.addError(new FieldError("uzytkownikView", "emailPrywatny", "Taki email jest już zajęty!"));
            model.addAttribute("uzytkownikView", uzytkownikView);
            return "Rejestracja";
        }

        if(uzytkownikService.sprawdzenieKontaNaPodstawieImienia(uzytkownikView.getImie())) {
            bindingResult.addError(new FieldError("uzytkownikView", "imie", "Takie imie jest już zajęte!"));
            model.addAttribute("uzytkownikView", uzytkownikView);
            return "Rejestracja";
        }

        // Podwójna weryfikacja hasła 29:08

        try{
            rejestracjaService.rejestracja(uzytkownikView);
            redirectAttributes.addFlashAttribute("successRegistrationMessage","Rejestracja powiodła się, email weryfikacyjny został wysłany!");
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("failedRegistrationMessage","Coś poszło nie tak.. Nie udało się wysłać maila..");
        }

        return "redirect:/login";
    }

    @GetMapping("/aktywacjaKonta")
    public String aktywacjaKontaPrzezLink(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes){
        TokenWeryfikacyjny tokenWeryfikacyjny = tokenWeryfikacyjnyService.znajdzToken(token);
        if(tokenWeryfikacyjny == null){
            model.addAttribute("wiadomosc", "Nie ma Twojego tokenu weryfikacyjnego w bazie..");
        }
        else{
            Uzytkownik uzytkownik = tokenWeryfikacyjny.getUzytkownik();
            if(!uzytkownik.isAktywnoscKonta()){
                LocalDateTime dataWygasniecia = tokenWeryfikacyjny.getDataWygasniecia();
                if (dataWygasniecia.isBefore(LocalDateTime.now())){

                    model.addAttribute("wiadomosc", "Token weryfikacyjny wygasł..");
                }
                else{
                    uzytkownik.setAktywnoscKonta(true);
                    uzytkownikService.zapiszUzytkownika(uzytkownik);
                    redirectAttributes.addFlashAttribute("successActivationMessage", "Aktywowałeś konto pomyślnie!");
                    return "redirect:/login";
                }
            }
            else{
                model.addAttribute("wiadomosc", "Twoje konto zostało już aktywowane!");
            }
        }
        return "AktywacjaWiadomosc";
    }

    @GetMapping("/wyslanieTokenu")
    public String pobierzStroneWyslanieTokenu(Model model){
        UzytkownikView uzytkownikView = new UzytkownikView();
        model.addAttribute("uzytkownikView", uzytkownikView);
        return "WyslanieTokenu";
    }

    @PostMapping("/wyslanieTokenu")
    public String wyslanieTokenu(@ModelAttribute("uzytkownikView") UzytkownikView uzytkownikView, RedirectAttributes redirectAttributes){
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieMailaPrywatnego(uzytkownikView.getEmailPrywatny());
        if(uzytkownik != null){
            try{
                TokenWeryfikacyjny tokenWeryfikacyjny = rejestracjaService.rejestracjaTokena(uzytkownik);
                rejestracjaService.rejestracjaMaila(uzytkownik, tokenWeryfikacyjny.getToken());
                redirectAttributes.addFlashAttribute("successSendingToken","Token został wysłany na " + uzytkownikView.getEmailPrywatny());
            }catch (Exception e){
                redirectAttributes.addFlashAttribute("failerSendingToken","Nie udało się wysłać tokenu..");
            }
        }
        else redirectAttributes.addFlashAttribute("failerSendingToken","Nie ma takiego maila w naszej bazie danych..");
        return "redirect:/wyslanieTokenu";
    }
}

