package ufp.uczelnianeforumproblemowe.logic.watekService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WatekRepository;

import java.util.List;

@Service
public class WatekService implements WatekServiceInterface{

    private final WatekRepository watekRepository;

    public WatekService(@Autowired WatekRepository watekRepository) {
        this.watekRepository = watekRepository;
    }

    @Override
    public Watek znajdzWatekNaPodstawieNazwy(String nazwaWatku) {
        return watekRepository.findByNazwa(nazwaWatku);
    }

    @Override
    public void zapiszWatek(Watek watek) {
        watekRepository.save(watek);
    }

    @Override
    public List<Watek> pobierzWszystkieWydzialy() {
        return watekRepository.findAll();
    }
}
