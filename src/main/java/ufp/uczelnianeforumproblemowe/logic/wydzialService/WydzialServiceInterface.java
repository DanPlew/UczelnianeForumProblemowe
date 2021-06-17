package ufp.uczelnianeforumproblemowe.logic.wydzialService;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;

import java.util.List;

public interface WydzialServiceInterface {
    Wydzial znajdzWydzialNaPodstawieNazwy(WydzialEnum nazwaWydzialu);
    void zapiszWydzial(Wydzial wydzial);
    List<Wydzial> pobierzWszystkieWydzialy();
}
