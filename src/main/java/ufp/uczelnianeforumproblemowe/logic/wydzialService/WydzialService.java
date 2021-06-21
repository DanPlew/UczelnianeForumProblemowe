package ufp.uczelnianeforumproblemowe.logic.wydzialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WydzialRepository;

import java.util.List;

@Service
public class WydzialService implements WydzialServiceInterface{

    private final WydzialRepository wydzialRepository;

    public WydzialService(@Autowired WydzialRepository wydzialRepository) {
        this.wydzialRepository = wydzialRepository;
    }

    @Override
    public Wydzial znajdzWydzialNaPodstawieNazwy(WydzialEnum nazwaWydzialu) {
        return wydzialRepository.findByNazwa(nazwaWydzialu);
    }

    @Override
    public void zapiszWydzial(Wydzial wydzial) {
        wydzialRepository.save(wydzial);
    }

    @Override
    public List<Wydzial> pobierzWszystkieWydzialy() {
        return wydzialRepository.findAll();
    }

    @Override
    public Wydzial pobierzWydzialNaPodstawieId(long id){
        return wydzialRepository.findById(id).orElse(null);
    }
}
