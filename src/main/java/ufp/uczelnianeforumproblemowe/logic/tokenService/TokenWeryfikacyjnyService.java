package ufp.uczelnianeforumproblemowe.logic.tokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;
import ufp.uczelnianeforumproblemowe.jpa.repositories.TokenWeryfikacyjnyRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenWeryfikacyjnyService implements TokenWeryfikacyjnyServiceInterface {

    //private static final BytesKeyGenerator GENERATOR_TOKENOW = KeyGenerators.secureRandom();
    //private static final Charset ascii = Charset.forName("US-ASCII");

    @Value("${jdj.secure.token.validity}")
    private int walidacjaTokenaWSekundach;

    private TokenWeryfikacyjnyRepository tokenWeryfikacyjnyRepository;

    public TokenWeryfikacyjnyService(@Autowired TokenWeryfikacyjnyRepository tokenWeryfikacyjnyRepository){
        this.tokenWeryfikacyjnyRepository = tokenWeryfikacyjnyRepository;
    }

    @Override
    public TokenWeryfikacyjny utworzTokenWeryfikacyjny() {
        //String token = new String(Base64.encodeBase64URLSafe(GENERATOR_TOKENOW.generateKey()));

        String tokenTestowy = UUID.randomUUID().toString();

        TokenWeryfikacyjny tokenWeryfikacyjny = new TokenWeryfikacyjny();
        tokenWeryfikacyjny.setToken(tokenTestowy);
        tokenWeryfikacyjny.setDataWygasniecia(LocalDateTime.now().plusSeconds(getWalidacjaTokenaWSekundach()));
        this.zapiszTokenWeryfikacyjny(tokenWeryfikacyjny);
        return tokenWeryfikacyjny;
    }

    @Override
    public void zapiszTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny) {
        tokenWeryfikacyjnyRepository.save(tokenWeryfikacyjny);
    }

    @Override
    public void usunTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny) {
        tokenWeryfikacyjnyRepository.removeByToken(tokenWeryfikacyjny.getToken());
    }

    @Override
    public TokenWeryfikacyjny znajdzToken(String token) {
        return tokenWeryfikacyjnyRepository.findByToken(token);
    }

    public int getWalidacjaTokenaWSekundach() {
        return walidacjaTokenaWSekundach;
    }
}
