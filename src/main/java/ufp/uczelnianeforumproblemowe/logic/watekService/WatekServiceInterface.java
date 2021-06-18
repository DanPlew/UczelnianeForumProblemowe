package ufp.uczelnianeforumproblemowe.logic.watekService;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;

import java.util.List;

public interface WatekServiceInterface {
    Watek znajdzWatekNaPodstawieNazwy(String nazwaWatku);
    Watek znajdzWatekNaPodstawieId(long id);
    void zapiszWatek(Watek watek);
    List<Watek> pobierzWszystkieWatki();
    List<Watek> pobierzWszystkieWatkiGlowneNaPodstawieWydzialu(WydzialEnum wydzialEnum);
    List<Watek> pobierzWszystkiePodWatkiNaPodstawieRodzica(WydzialEnum wydzialEnum, long idRodzica);
    void usunWatek(long id);
}
