package ufp.uczelnianeforumproblemowe.logic.uzytkownikService;

public interface SprawdzenieUzytkownikServiceInterface {
    boolean sprawdzenieKontaNaPodstawieLoginu(String login);
    boolean sprawdzenieKontaNaPodstawieMailaUczelnianego(String emailUczelniany);
    boolean sprawdzenieKontaNaPodstawieMailaPrywatnego(String emailUczelniany);
    boolean sprawdzenieKontaNaPodstawieImienia(String imie);
}
