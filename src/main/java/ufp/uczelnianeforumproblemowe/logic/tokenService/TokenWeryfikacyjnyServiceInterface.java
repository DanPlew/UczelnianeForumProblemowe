package ufp.uczelnianeforumproblemowe.logic.tokenService;

import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;
import ufp.uczelnianeforumproblemowe.jpa.models.Uzytkownik;

public interface TokenWeryfikacyjnyServiceInterface {
    void zapiszTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny, Uzytkownik uzytkownik);
    void usunTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny);
    TokenWeryfikacyjny znajdzToken(String token);
    TokenWeryfikacyjny utworzTokenWeryfikacyjny();

}
