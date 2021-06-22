package ufp.uczelnianeforumproblemowe.logic.plikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.repositories.PlikRepository;
import ufp.uczelnianeforumproblemowe.jpa.repositories.UzytkownikRepository;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;

import java.io.IOException;
import java.util.List;

@Service
public class PlikService implements PlikServiceInterface {

    private final PlikRepository plikRepository;
    private final UzytkownikService uzytkownikService;

    public PlikService(@Autowired PlikRepository plikRepository,
                       @Autowired UzytkownikService uzytkownikService) {
        this.plikRepository = plikRepository;
        this.uzytkownikService = uzytkownikService;
    }

    @Override
    public void zapiszPlik(MultipartFile[] multipartFile, long id)throws IOException{
        Uzytkownik uzytkownik = uzytkownikService.znajdzUzytkownikaNaPodstawieId(id);
        for (MultipartFile file : multipartFile){
            Plik plik = new Plik(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            plik.setUzytkownik(uzytkownik);
            plikRepository.save(plik);
        }
    }

    @Override
    public Plik sciagnijPlik(long id){
        return plikRepository.findById(id).orElse(null);
    }

    @Override
    public List<Plik> sciagnijPlikiUzytkownika(long id){
        return plikRepository.getAllByUzytkownikId(id);
    }

    @Override
    public void usunPlik(Plik plik){
        System.out.println(plik.getNazwa());
        plikRepository.delete(plik);
    }
}
