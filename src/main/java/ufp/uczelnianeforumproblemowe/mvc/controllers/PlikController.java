package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;
import ufp.uczelnianeforumproblemowe.logic.plikService.PlikService;

import java.io.IOException;

@CrossOrigin
@Controller
public class PlikController {

    private final PlikService plikService;

    public PlikController(@Autowired PlikService plikService) {
        this.plikService = plikService;
    }

    @PostMapping("/plik/add/{id}")
    public String zapiszPlik(@RequestParam("pliki") MultipartFile[] pliki,
                             @PathVariable("id") long id,
                             RedirectAttributes redirectAttributes){
        try {
            plikService.zapiszPlik(pliki, id);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("fileException","Nie udało się wgrać pliku..");
            return "redirect:/profil/" + id;
        }
        return "redirect:/profil/" + id;
    }

    @GetMapping("plik/delete/{id}")
    public String usunPlik(@PathVariable("id") long id){
        Plik plik = plikService.sciagnijPlik(id);
        plikService.usunPlik(plik);
        return "redirect:/profil/" + plik.getUzytkownik().getId();
    }

    @GetMapping("/plik/pobierz/{id}")
    public ResponseEntity<ByteArrayResource> pobierzPlik(@PathVariable("id") Long id){
        Plik plik = plikService.sciagnijPlik(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(plik.getRozszerzenie()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+plik.getNazwa()+"\"")
                .body(new ByteArrayResource(plik.getPlikByte()));
    }
}
