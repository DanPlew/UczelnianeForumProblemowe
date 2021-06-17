package ufp.uczelnianeforumproblemowe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.Temat;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.models.Watek;
import ufp.uczelnianeforumproblemowe.jpa.models.Wydzial;
import ufp.uczelnianeforumproblemowe.jpa.repositories.TematRepository;
import ufp.uczelnianeforumproblemowe.jpa.repositories.UzytkownikRepository;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WatekRepository;
import ufp.uczelnianeforumproblemowe.jpa.repositories.WydzialRepository;
import ufp.uczelnianeforumproblemowe.jpa.enums.RangaEnum;

@SpringBootApplication
public class UczelnianeForumProblemoweApplication {

    public static void main(String[] args) {
        SpringApplication.run(UczelnianeForumProblemoweApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UzytkownikRepository uzytkownikRepository, PasswordEncoder passwordEncoder, WydzialRepository wydzialRepository, WatekRepository watekRepository, TematRepository tematRepository){
        return args -> {
            Wydzial wydzial1 = new Wydzial(WydzialEnum.Informatyka);
            Wydzial wydzial2 = new Wydzial(WydzialEnum.Grafika);
            Wydzial wydzial3 = new Wydzial(WydzialEnum.Architektura_Wnętrz);
            wydzialRepository.save(wydzial1);
            wydzialRepository.save(wydzial2);
            wydzialRepository.save(wydzial3);

            Uzytkownik uzytkownik1 = new Uzytkownik("Daniel", passwordEncoder.encode("qwerty"), "Danielson", RangaEnum.ADMINISTRATOR, "s17198@pjwstk.edu.pl", "dpplewinski@gmail.com");
            Uzytkownik uzytkownik2 = new Uzytkownik("Hubert", passwordEncoder.encode("qwerty"), "Hubert", RangaEnum.MODERATOR, "s16607@pjwstk.edu.pl", "hubster@gmail.com");

            uzytkownik1.setWydzial(wydzial1);
            uzytkownik2.setWydzial(wydzial2);

            uzytkownikRepository.save(uzytkownik1);
            uzytkownikRepository.save(uzytkownik2);

            Watek watek1 = new Watek("Projekty");
            Watek watek2 = new Watek("Podprojekty");
            Watek watek3 = new Watek("Pomoc");
            Watek watek4 = new Watek("Szukanie");
            watek4.setUzytkownik(uzytkownik1);
            watek4.setWydzial(wydzial1);
            watek1.setUzytkownik(uzytkownik1);
            watek1.setWydzial(wydzial1);
            watek2.setUzytkownik(uzytkownik1);
            watek2.setWydzial(wydzial1);
            watek3.setUzytkownik(uzytkownik1);
            watek3.setWydzial(wydzial1);
            watek2.setParentWatek(watek1);
            watek3.setParentWatek(watek1);
            watekRepository.save(watek4);
            watekRepository.save(watek1);
            watekRepository.save(watek2);
            watekRepository.save(watek3);

            Temat temat1 = new Temat("Java", "Bardzo fajny poradnik dotyczacy Javy :)");
            temat1.setWatek(watek1);
            temat1.setUzytkownik(uzytkownik1);
            tematRepository.save(temat1);

            Temat temat2 = new Temat("Eklips", "Nienawidze tego :(");
            temat2.setWatek(watek2);
            temat2.setUzytkownik(uzytkownik1);
            tematRepository.save(temat2);
        };
    }
}

// TODO 1: Zrobić strone główną.
// TODO 2: Zrobić podstrony dla wątków. (Specjalnie dla moderatora i użytkownika.
// TODO 3: Zrobić profil użytkownika.
// TODO 4: Panel Administracyjny dla banowania ludzi.
