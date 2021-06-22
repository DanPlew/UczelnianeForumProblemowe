package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ufp.uczelnianeforumproblemowe.jpa.models.Obserwowani;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

public interface ObserwowaniRepository extends JpaRepository<Obserwowani, Long> {
    Obserwowani getObserwowaniByUzytkownikAndObserwowany(Uzytkownik uzytkownik, Uzytkownik obserwowany);
}
