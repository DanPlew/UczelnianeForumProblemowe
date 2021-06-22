package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

import java.util.List;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {
    Uzytkownik findByLogin(String login);
    Uzytkownik findByEmailUczelniany(String emailUczelniany);
    Uzytkownik findByEmailPrywatny(String emailPrywatny);
    Uzytkownik findByImie(String imie);

    @Query(value = "select count(uzytkownik.login) from Uzytkownik uzytkownik inner join Wydzial wydzial on wydzial.id = uzytkownik.wydzial.id where wydzial.nazwa = ?1")
    Integer pobierzWszystkichUzytkownikowNaPodstawieWydzialu(WydzialEnum wydzialEnum);

    @Query(value = "select uzytkownik from Uzytkownik uzytkownik " +
            "inner join Obserwowani obserwowani on uzytkownik.id = obserwowani.obserwowany.id " +
            "where obserwowani.uzytkownik.id = ?1")
    List<Uzytkownik> pobierzObserwujacych(long id);
}