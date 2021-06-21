package ufp.uczelnianeforumproblemowe.logic.watekService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
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
    public Watek znajdzWatekNaPodstawieId(long id) {
        return watekRepository.findById(id).orElse(null);
    }

    @Override
    public void zapiszWatek(Watek watek) {
        watekRepository.save(watek);
    }

    @Override
    public List<Watek> pobierzWszystkieWatki() {
        return watekRepository.findAll();
    }

    @Override
    public List<Watek> pobierzWszystkieWatkiGlowneNaPodstawieWydzialu(WydzialEnum wydzialEnum){
        return watekRepository.pobierzWszystkieWatkiGlowneNaPodstawieWydzialu(wydzialEnum);
    }

    @Override
    public List<Watek> pobierzWszystkiePodWatkiNaPodstawieRodzica(WydzialEnum wydzialEnum, long idRodzica) {
        return watekRepository.pobierzWszystkiePodWatkiNaPodstawieRodzica(wydzialEnum, idRodzica);
    }

    @Override
    public void usunWatek(long id) {
        Watek watek = znajdzWatekNaPodstawieId(id);
        if(watek != null) watekRepository.delete(watek);
    }

    @Override
    public Integer pobierzWszystkieWatkiNaPodstawieWydzialu(WydzialEnum wydzialEnum) {
        return watekRepository.pobierzWszystkieWatkiNaPodstawieWydzialu(wydzialEnum);
    }
}
