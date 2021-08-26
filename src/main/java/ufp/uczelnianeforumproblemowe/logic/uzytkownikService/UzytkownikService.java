package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.repositories.UzytkownikRepository;

import java.util.List;

@Service
public class UzytkownikService implements UzytkownikServiceInterface ,SprawdzenieUzytkownikServiceInterface{

    private final UzytkownikRepository uzytkownikRepository;

    public UzytkownikService(@Autowired UzytkownikRepository uzytkownikRepository){
        this.uzytkownikRepository = uzytkownikRepository;
    }

    @Override
    public Uzytkownik znajdzUzytkownikaNaPodstawieImienia(String imie) {
        return uzytkownikRepository.findByImie(imie);
    }

    @Override
    public Uzytkownik znajdzUzytkownikaNaPodstawieLoginu(String login) {
        return uzytkownikRepository.findByLogin(login);
    }

    @Override
    public Uzytkownik znajdzUzytkownikaNaPodstawieMailaUczelnianego(String emailUczelniany) {
        return uzytkownikRepository.findByEmailUczelniany(emailUczelniany);
    }

    @Override
    public Uzytkownik znajdzUzytkownikaNaPodstawieMailaPrywatnego(String emailPrywatny){
        return uzytkownikRepository.findByEmailPrywatny(emailPrywatny);
    }

    @Override
    public Uzytkownik znajdzUzytkownikaNaPodstawieId(long id){
        return uzytkownikRepository.findById(id).orElse(null);
    }

    @Override
    public void zapiszUzytkownika(Uzytkownik uzytkownik){
        uzytkownikRepository.save(uzytkownik);
    }

    @Override
    public Integer pobierzWszystkichUzytkownikowNaPodstawieWydzialu(WydzialEnum wydzialEnum) {
        return uzytkownikRepository.pobierzLiczbeUzytkownikowNaPodstawieWydzialu(wydzialEnum);
    }

    @Override
    public List<Uzytkownik> pobierzListeZgloszonychOsob() {
        return uzytkownikRepository.pobierzListeZgloszonychOsob();
    }

    @Override
    public List<Uzytkownik> pobierzWszystkichUzytkownikow(){
        return uzytkownikRepository.findAll();
    }

    @Override
    public List<Uzytkownik> pobierzObserwujacych(long id){
        return uzytkownikRepository.pobierzObserwujacych(id);
    }



    @Override
    public boolean sprawdzenieKontaNaPodstawieLoginu(String login) {
        return uzytkownikRepository.findByLogin(login) != null ? true : false;
    }

    @Override
    public boolean sprawdzenieKontaNaPodstawieMailaUczelnianego(String emailUczelniany) {
        return uzytkownikRepository.findByEmailUczelniany(emailUczelniany) != null ? true : false;
    }

    @Override
    public boolean sprawdzenieKontaNaPodstawieMailaPrywatnego(String emailUczelniany) {
        return uzytkownikRepository.findByEmailPrywatny(emailUczelniany) != null ? true : false;
    }

    @Override
    public boolean sprawdzenieKontaNaPodstawieImienia(String imie) {
        return uzytkownikRepository.findByImie(imie) != null ? true : false;
    }
}
