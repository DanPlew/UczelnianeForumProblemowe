package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

import java.util.List;

@Repository
public interface TematRepository extends JpaRepository<Temat, Long> {

    List<Temat> getTematByUzytkownik(Uzytkownik uzytkownik);

    @Query(value = "select temat from Temat temat inner join Watek watek on watek.id = temat.watek.id where watek.id = ?1")
    List<Temat> pobierzWszystkieTematyNaPodstawieWatku(long idWatku);

    @Query(value = "select count(temat.id) from Temat temat " +
            "inner join Watek watek on watek.id = temat.watek.id " +
            "inner join Wydzial wydzial on wydzial.id = watek.wydzial.id " +
            "where wydzial.nazwa = ?1")
    Integer pobierzWszystkieTematyWedlugWydzialu(WydzialEnum wydzialEnum);

    @Query(value = "select temat from Temat temat " +
            "inner join Post post on temat.id = post.temat.id " +
            "inner join Uzytkownik  uzytkownik on post.uzytkownik.id = uzytkownik.id " +
            "where uzytkownik.id = ?1")
    List<Temat> pobierzTematyGdzieWstawionoPost(long id);
}
