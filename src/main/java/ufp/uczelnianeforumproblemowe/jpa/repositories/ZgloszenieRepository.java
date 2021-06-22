package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.models.Zgloszenie;


@Repository
public interface ZgloszenieRepository extends JpaRepository<Zgloszenie, Long> {

    @Query(value = "select count(zgloszenie.oskarzony.id) from Zgloszenie zgloszenie " +
            "where zgloszenie.oskarzony.id = ?1")
    Integer liczbaZgloszenOskarzonego(long id);
}
