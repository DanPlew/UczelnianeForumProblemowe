package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;
import ufp.uczelnianeforumproblemowe.security.exceptions.UzytkownikAlreadyExistExeption;

public interface RejestracjaUzytkownikaInterface {
    void rejestracja(final UzytkownikView uzytkownikView) throws UzytkownikAlreadyExistExeption;
    boolean sprawdzenieKontaNaPodstawieLoginu(String login);
    boolean sprawdzenieKontaNaPodstawieMailaUczelnianego(String emailUczelniany);
}
