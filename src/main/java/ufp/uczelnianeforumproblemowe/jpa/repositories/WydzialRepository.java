package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;

public interface WydzialRepository extends JpaRepository<Wydzial, Long> {
    Wydzial findByNazwa(WydzialEnum nazwaWydzialu);
}
