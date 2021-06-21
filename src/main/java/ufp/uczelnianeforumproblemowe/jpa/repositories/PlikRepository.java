package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.models.Plik;

import java.util.List;

@Repository
public interface PlikRepository extends JpaRepository<Plik, Long> {
    List<Plik> getAllByUzytkownikId(long id);
}
