package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;

import java.util.List;

@Repository
public interface WatekRepository extends JpaRepository<Watek, Long> {
    Watek findByNazwa(String nazwaWatku);

    @Query(value = "select watek from Watek watek inner join Wydzial wydzial on wydzial.id = watek.wydzial.id where wydzial.nazwa = ?1 and watek.parentWatek is null ")
    List<Watek> pobierzWszystkieWatkiGlowne(WydzialEnum wydzialEnum);

    @Query(value = "select watek from Watek watek inner join Wydzial wydzial on wydzial.id = watek.wydzial.id where wydzial.nazwa = ?1 and watek.parentWatek.id = ?2")
    List<Watek> pobierzWszystkiePodWatki(WydzialEnum wydzialEnum, long idRodzica);
}
