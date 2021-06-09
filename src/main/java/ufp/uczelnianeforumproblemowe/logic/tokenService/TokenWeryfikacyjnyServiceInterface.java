package ufp.uczelnianeforumproblemowe.logic.tokenService;

import ufp.uczelnianeforumproblemowe.jpa.models.TokenWeryfikacyjny;

public interface TokenWeryfikacyjnyServiceInterface {
    void zapiszTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny);
    void usunTokenWeryfikacyjny(TokenWeryfikacyjny tokenWeryfikacyjny);
    TokenWeryfikacyjny znajdzToken(String token);
    TokenWeryfikacyjny utworzTokenWeryfikacyjny();
}
