package ufp.uczelnianeforumproblemowe.logic.rejestracjaService;

import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;

public interface RejestracjaServiceInterface {
    void rejestracja(UzytkownikView uzytkownikView) throws Exception;
    TokenWeryfikacyjny rejestracjaTokena(Uzytkownik uzytkownik);
    void rejestracjaMaila(Uzytkownik uzytkownik, String token) throws Exception;
}
