package ufp.uczelnianeforumproblemowe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.repositories.UzytkownikRepository;
import ufp.uczelnianeforumproblemowe.security.rangi.RangaEnum;

@SpringBootApplication
public class UczelnianeForumProblemoweApplication {

    public static void main(String[] args) {
        SpringApplication.run(UczelnianeForumProblemoweApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UzytkownikRepository uzytkownikRepository, PasswordEncoder passwordEncoder){
        return args -> {
            Uzytkownik uzytkownik1 = new Uzytkownik("Daniel", passwordEncoder.encode("qwerty"), "Daniel", RangaEnum.ADMINISTRATOR, "s17198@pjwstk.edu.pl", "dpplewinski@gmail.com");
            uzytkownik1.setAktywnoscKonta(true);
            Uzytkownik uzytkownik2 = new Uzytkownik("Hubert", passwordEncoder.encode("qwerty"), "Hubert", RangaEnum.MODERATOR, "s16607@pjwstk.edu.pl", "hubster@gmail.com");
            uzytkownikRepository.save(uzytkownik1);
            uzytkownikRepository.save(uzytkownik2);
        };
    }
}

// TODO 5: Umieścić wszystkie tabele do bazy danych i stworzyć odpowiednie połączenia.
// TODO 6: Zrobić reszte podstron.
