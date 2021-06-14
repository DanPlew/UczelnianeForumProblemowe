package ufp.uczelnianeforumproblemowe.logic.tokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;
import ufp.uczelnianeforumproblemowe.jpa.repositories.TokenWeryfikacyjnyRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenWeryfikacyjnyService implements TokenWeryfikacyjnyServiceInterface {

    @Value("${jdj.secure.token.validity}")
    private int walidacjaTokenaWSekundach;

    private final TokenWeryfikacyjnyRepository tokenWeryfikacyjnyRepository;

    public TokenWeryfikacyjnyService(@Autowired TokenWeryfikacyjnyRepository tokenWeryfikacyjnyRepository){
        this.tokenWeryfikacyjnyRepository = tokenWeryfikacyjnyRepository;
    }

    @Override
    public TokenWeryfikacyjny utworzTokenWeryfikacyjny() {
        String token = UUID.randomUUID().toString();

        TokenWeryfikacyjny tokenWeryfikacyjny = new TokenWeryfikacyjny();
        tokenWeryfikacyjny.setToken(token);
        tokenWeryfikacyjny.setDataWygasniecia(LocalDateTime.now().plusSeconds(getWalidacjaTokenaWSekundach()));

        return tokenWeryfikacyjny;
    }

    @Override
    @Transactional
    public void zapiszTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny, Uzytkownik uzytkownik) {
        tokenWeryfikacyjny.setUzytkownik(uzytkownik);
        tokenWeryfikacyjnyRepository.save(tokenWeryfikacyjny);
    }

    @Override
    @Transactional
    public void usunTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny) {
        tokenWeryfikacyjnyRepository.removeByToken(tokenWeryfikacyjny.getToken());
    }

    @Override
    @Transactional
    public TokenWeryfikacyjny znajdzToken(String token) {
        return tokenWeryfikacyjnyRepository.findByToken(token);
    }

    public int getWalidacjaTokenaWSekundach() {
        return walidacjaTokenaWSekundach;
    }
}
