package ufp.uczelnianeforumproblemowe.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long> {
    Uzytkownik findByLogin(String login);
    Uzytkownik findByEmailUczelniany(String emailUczelniany);

    @Transactional
    @Modifying
    @Query("update Uzytkownik user set user.aktywnoscKonta = true where user.login = ?1")
    void aktywowanieKonta(String login);
}