package ufp.uczelnianeforumproblemowe.logic.tematService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.repositories.TematRepository;

import java.util.List;

@Service
public class TematService implements TematServiceInterface{

    private final TematRepository tematRepository;

    public TematService(@Autowired TematRepository tematRepository) {
        this.tematRepository = tematRepository;
    }

    @Override
    public List<Temat> pobierzWszystkieTematyNaPodstawieWatku(long idWatku) {
        return tematRepository.pobierzWszystkieTematyNaPodstawieWatku(idWatku);
    }

    @Override
    public void zapiszTemat(Temat temat){
        tematRepository.save(temat);
    }

    @Override
    public void usunTemat(Temat temat) {
        tematRepository.delete(temat);
    }

    @Override
    public Temat znajdzTematPoId(long id) {
        return tematRepository.findById(id).orElse(null);
    }
}
