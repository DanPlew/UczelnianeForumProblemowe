package ufp.uczelnianeforumproblemowe.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;
import ufp.uczelnianeforumproblemowe.jpa.models.Post;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.logic.plikService.PlikService;
import ufp.uczelnianeforumproblemowe.logic.postService.PostService;
import ufp.uczelnianeforumproblemowe.logic.tematService.TematService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.PostView;

@Controller
public class PostController {

    private final PostService postService;
    private final UzytkownikService uzytkownikService;
    private final TematService tematService;
    private final PlikService plikService;

    public PostController(@Autowired PostService postService,
                          @Autowired UzytkownikService uzytkownikService,
                          @Autowired TematService tematService,
                          @Autowired PlikService plikService) {
        this.postService = postService;
        this.uzytkownikService = uzytkownikService;
        this.tematService = tematService;
        this.plikService = plikService;
    }

    @PostMapping("/post/add")
    public String dodajPost(@ModelAttribute("postView")PostView postView, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("wrongPost","Wiadomość jest za długa.. Max 1000 znaków.");
            return "redirect:/temat/" + postView.getIdTematu();
        }

        if(postView.getWiadomosc().equals("")){
            redirectAttributes.addFlashAttribute("wrongPost","Wiadomość nie może być pusta!");
            return "redirect:/temat/" + postView.getIdTematu();
        }

        Post post = new Post();
        post.setWiadomosc(postView.getWiadomosc());
        Plik plik = plikService.sciagnijPlik(postView.getIdPliku());
        post.setPlik(plik);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieLoginu(auth.getName());
        post.setUzytkownik(uzytkownik);

        Temat temat = tematService.znajdzTematPoId(postView.getIdTematu());
        post.setTemat(temat);

        postService.zapiszPost(post);

        return "redirect:/temat/" + postView.getIdTematu();
    }

    @GetMapping("post/delete/{id}")
    public String usunPost(@PathVariable("id") long id){
        Post post = postService.pobierzPostWedlugId(id);
        postService.usunPost(post);

        return "redirect:/temat/" + post.getTemat().getId();
    }
}
