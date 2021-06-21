package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;
import ufp.uczelnianeforumproblemowe.logic.plikService.PlikService;

import java.io.IOException;

@Controller
public class PlikController {

    private final PlikService plikService;

    public PlikController(@Autowired PlikService plikService) {
        this.plikService = plikService;
    }

    @PostMapping("/plik/add/{id}")
    public String zapiszPlik(@RequestParam("pliki") MultipartFile[] pliki,
                             @PathVariable("id") long id){
        try {
            plikService.zapiszPlik(pliki, id);
        }catch (IOException e){

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
