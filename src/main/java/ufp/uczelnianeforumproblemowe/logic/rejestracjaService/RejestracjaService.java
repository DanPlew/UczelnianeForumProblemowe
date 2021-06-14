package ufp.uczelnianeforumproblemowe.logic.rejestracjaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.logic.emailService.EmailService;
import ufp.uczelnianeforumproblemowe.logic.tokenService.TokenWeryfikacyjnyService;
import ufp.uczelnianeforumproblemowe.logic.uzytkownikService.UzytkownikService;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;

@Service
public class RejestracjaService implements RejestracjaServiceInterface{

    private final UzytkownikService uzytkownikService;
    private final TokenWeryfikacyjnyService tokenWeryfikacyjnyService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public RejestracjaService(@Autowired UzytkownikService uzytkownikService,
                              @Autowired TokenWeryfikacyjnyService tokenWeryfikacyjnyService,
                              @Autowired EmailService emailService,
                              @Autowired PasswordEncoder passwordEncoder) {
        this.uzytkownikService = uzytkownikService;
        this.tokenWeryfikacyjnyService = tokenWeryfikacyjnyService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void rejestracja(UzytkownikView uzytkownikView){
        Uzytkownik uzytkownik = new Uzytkownik(uzytkownikView);
        uzytkownik.setHaslo(passwordEncoder.encode(uzytkownik.getHaslo()));
        uzytkownikService.zapiszUzytkownika(uzytkownik);

        TokenWeryfikacyjny tokenWeryfikacyjny = rejestracjaTokena(uzytkownik);
        rejestracjaMaila(uzytkownik, tokenWeryfikacyjny.getToken());
    }

    @Override
    public TokenWeryfikacyjny rejestracjaTokena(Uzytkownik uzytkownik){
        TokenWeryfikacyjny tokenWeryfikacyjny = tokenWeryfikacyjnyService.utworzTokenWeryfikacyjny();
        tokenWeryfikacyjnyService.zapiszTokenWeryfikacyjny(tokenWeryfikacyjny, uzytkownik);
        return tokenWeryfikacyjny;
    }

    @Override
    public void rejestracjaMaila(Uzytkownik uzytkownik, String token) {
        emailService.wyslij(uzytkownik.getEmailPrywatny(), token);
    }
}
