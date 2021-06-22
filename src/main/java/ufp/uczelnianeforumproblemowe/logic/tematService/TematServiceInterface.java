package ufp.uczelnianeforumproblemowe.logic.tematService;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

import java.util.List;

public interface TematServiceInterface {
    List<Temat> pobierzWszystkieTematyNaPodstawieWatku(long idWatku);
    void zapiszTemat(Temat temat);
    void usunTemat(Temat temat);
    Temat znajdzTematPoId(long id);
    Integer pobierzWszystkieTematyWedlugWydzialu(WydzialEnum wydzialEnum);
    List<Temat> getTematByUzytkownik(Uzytkownik uzytkownik);
    List<Temat> pobierzTematyGdzieWstawionoPost(long id);
}
