package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

public interface UzytkownikServiceInterface {
    Uzytkownik znajdzUzytkownikaNaPodstawieImienia(String imie);
    Uzytkownik znajdzUzytkownikaNaPodstawieLoginu(String login);
    Uzytkownik znajdzUzytkownikaNaPodstawieMailaPrywatnego(String emailPrywatny);
    Uzytkownik znajdzUzytkownikaNaPodstawieMailaUczelnianego(String emailUczelniany);
    void zapiszUzytkownika(Uzytkownik uzytkownik);
    Integer pobierzWszystkichUzytkownikowNaPodstawieWydzialu(WydzialEnum wydzialEnum);
}
