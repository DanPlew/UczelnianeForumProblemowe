package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;

import java.util.List;

@Repository
public interface TematRepository extends JpaRepository<Temat, Long> {

    @Query(value = "select temat from Temat temat inner join Watek watek on watek.id = temat.watek.id where watek.id = ?1")
    List<Temat> pobierzWszystkieTematyNaPodstawieWatku(long idWatku);
}
