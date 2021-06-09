package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

public interface RejestracjaTokenaInterface {
    void rejestracjaTokena(Uzytkownik uzytkownik);
    void sprawdzenieTokena(String token) throws IllegalStateException;
}
