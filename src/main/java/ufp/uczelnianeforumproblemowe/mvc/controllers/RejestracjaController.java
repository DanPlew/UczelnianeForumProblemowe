package ufp.uczelnianeforumproblemowe.mvc.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;
import ufp.uczelnianeforumproblemowe.security.exceptions.UzytkownikAlreadyExistExeption;

import javax.validation.Valid;

@Controller
public class RejestracjaController {

    private UzytkownikService uzytkownikService;

    public RejestracjaController(@Autowired UzytkownikService uzytkownikService){
        this.uzytkownikService = uzytkownikService;
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
        return "Rejestracja";
    }

    @PostMapping("/rejestracja")
    public String Rejestracja(@ModelAttribute("uzytkownikView") @Valid UzytkownikView uzytkownikView, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("uzytkownikView", uzytkownikView);
            return "Rejestracja";
        }
        try{
            uzytkownikService.rejestracja(uzytkownikView);
        }catch (UzytkownikAlreadyExistExeption e){
            bindingResult.rejectValue("login", "Rejestracja.UzytkownikIstnieje", "Jest juz uzytkownik o takim loginie lub mailu.");
            model.addAttribute("uzytkownikView", uzytkownikView);
            return "Rejestracja";
        }
        return "/Login";
    }

    @GetMapping("/wygasniecie")
    public String wygasniecie(){
        return "TokenWygasl";
    }
}

