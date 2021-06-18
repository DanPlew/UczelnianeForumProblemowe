package ufp.uczelnianeforumproblemowe.logic.tematService;

import ufp.uczelnianeforumproblemowe.jpa.models.Temat;

import java.util.List;

public interface TematServiceInterface {
    List<Temat> pobierzWszystkieTematyNaPodstawieWatku(long idWatku);
}
