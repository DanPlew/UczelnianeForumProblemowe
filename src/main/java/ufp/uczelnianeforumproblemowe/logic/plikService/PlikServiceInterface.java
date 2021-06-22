package ufp.uczelnianeforumproblemowe.logic.plikService;

import org.springframework.web.multipart.MultipartFile;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;

import java.io.IOException;
import java.util.List;

public interface PlikServiceInterface {
    void zapiszPlik(MultipartFile[] multipartFile, long id)throws IOException;
    Plik sciagnijPlik(long id);
    List<Plik> sciagnijPlikiUzytkownika(long id);
    void usunPlik(Plik plik);
}
