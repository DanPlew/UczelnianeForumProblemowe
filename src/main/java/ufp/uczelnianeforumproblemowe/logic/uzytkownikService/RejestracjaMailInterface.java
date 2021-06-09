package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

public interface RejestracjaMailInterface {
    String zbudujMaila(String name, String link);
    void rejestracjaMaila(Uzytkownik uzytkownik, String token);
}
