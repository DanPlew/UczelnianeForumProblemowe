package ufp.uczelnianeforumproblemowe.logic.watekService;

import ufp.uczelnianeforumproblemowe.jpa.models.Watek;

import java.util.List;

public interface WatekServiceInterface {
    Watek znajdzWatekNaPodstawieNazwy(String nazwaWatku);
    void zapiszWatek(Watek watek);
    List<Watek> pobierzWszystkieWydzialy();
}
