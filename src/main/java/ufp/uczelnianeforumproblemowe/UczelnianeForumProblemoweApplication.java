package ufp.uczelnianeforumproblemowe;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.jpa.models.*;
import ufp.uczelnianeforumproblemowe.jpa.repositories.*;
import ufp.uczelnianeforumproblemowe.jpa.enums.RangaEnum;

@SpringBootApplication
public class UczelnianeForumProblemoweApplication {

    public static void main(String[] args) {
        SpringApplication.run(UczelnianeForumProblemoweApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(UzytkownikRepository uzytkownikRepository, PasswordEncoder passwordEncoder, WydzialRepository wydzialRepository, WatekRepository watekRepository, TematRepository tematRepository, PostRepository postRepository, ObserwowaniRepository obserwowaniRepository){
        return args -> {
            Wydzial wydzial1 = new Wydzial(WydzialEnum.Informatyka);
            Wydzial wydzial2 = new Wydzial(WydzialEnum.Grafika);
            Wydzial wydzial3 = new Wydzial(WydzialEnum.Architektura_Wnętrz);
            wydzialRepository.save(wydzial1);
            wydzialRepository.save(wydzial2);
            wydzialRepository.save(wydzial3);

            Uzytkownik uzytkownik1 = new Uzytkownik("Admin", passwordEncoder.encode("qwerty"), "Daniel", RangaEnum.ADMINISTRATOR, "s17198@pjwstk.edu.pl", "daniel@gmail.com");
            Uzytkownik uzytkownik2 = new Uzytkownik("Moderator", passwordEncoder.encode("qwerty"), "Hubert", RangaEnum.MODERATOR, "s16607@pjwstk.edu.pl", "hubert@gmail.com");
            Uzytkownik uzytkownik3 = new Uzytkownik("Uzytkownik", passwordEncoder.encode("qwerty"), "Konstancja", RangaEnum.UZYTKOWNIK, "s171982@pjwstk.edu.pl", "konstancja@gmail.com");

            uzytkownik1.setWydzial(wydzial1);
            uzytkownik2.setWydzial(wydzial2);
            uzytkownik3.setWydzial(wydzial3);

            uzytkownikRepository.save(uzytkownik1);
            uzytkownikRepository.save(uzytkownik2);
            uzytkownikRepository.save(uzytkownik3);

            Obserwowani obserwowani = new Obserwowani(uzytkownik1, uzytkownik2);
            obserwowaniRepository.save(obserwowani);

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
            watek3.setParentWatek(watek4);
            watekRepository.save(watek4);
            watekRepository.save(watek1);
            watekRepository.save(watek2);
            watekRepository.save(watek3);

            Temat temat1 = new Temat("Java", "Bardzo fajny poradnik dotyczacy Javy :)");
            temat1.setWatek(watek1);
            temat1.setUzytkownik(uzytkownik1);
            tematRepository.save(temat1);

            Temat temat2 = new Temat("C++", "Bardzo fajny poradnik dotyczacy C++ :)");
            temat2.setWatek(watek2);
            temat2.setUzytkownik(uzytkownik1);
            tematRepository.save(temat2);

            Post post1 = new Post("    Lorem ipsum dolor sit amet consectetur adipisicing elit. Vitae tenetur ratione totam voluptas accusamus laboriosam maxime deserunt repudiandae saepe veniam officia earum dicta sapiente, ea molestias id ullam atque blanditiis?");
            Post post2 = new Post("    Lorem ipsum dolor sit amet consectetur adipisicing elit. Vitae tenetur ratione totam voluptas accusamus laboriosam maxime deserunt repudiandae saepe veniam officia earum dicta sapiente, ea molestias id ullam atque blanditiis?");
            Post post3 = new Post("    Lorem ipsum dolor sit amet consectetur adipisicing elit. Vitae tenetur ratione totam voluptas accusamus laboriosam maxime deserunt repudiandae saepe veniam officia earum dicta sapiente, ea molestias id ullam atque blanditiis?");

            post1.setTemat(temat1);
            post1.setUzytkownik(uzytkownik1);
            post2.setTemat(temat1);
            post2.setUzytkownik(uzytkownik1);
            post3.setTemat(temat2);
            post3.setUzytkownik(uzytkownik2);

            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
        };
    }
}
