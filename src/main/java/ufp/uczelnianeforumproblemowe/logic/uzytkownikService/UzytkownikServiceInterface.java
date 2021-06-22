package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

import java.util.List;

public interface UzytkownikServiceInterface {
    Uzytkownik znajdzUzytkownikaNaPodstawieImienia(String imie);
    Uzytkownik znajdzUzytkownikaNaPodstawieId(long id);
    Uzytkownik znajdzUzytkownikaNaPodstawieLoginu(String login);
    Uzytkownik znajdzUzytkownikaNaPodstawieMailaPrywatnego(String emailPrywatny);
    Uzytkownik znajdzUzytkownikaNaPodstawieMailaUczelnianego(String emailUczelniany);

    void zapiszUzytkownika(Uzytkownik uzytkownik);

    Integer pobierzWszystkichUzytkownikowNaPodstawieWydzialu(WydzialEnum wydzialEnum);
    List<Uzytkownik> pobierzListeZgloszonychOsob();
    List<Uzytkownik> pobierzWszystkichUzytkownikow();
    List<Uzytkownik> pobierzObserwujacych(long id);
}
