package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;

@Repository
public interface TokenWeryfikacyjnyRepository extends JpaRepository<TokenWeryfikacyjny, Long> {
    TokenWeryfikacyjny findByToken(String token);
    boolean removeByToken(String token);
}
