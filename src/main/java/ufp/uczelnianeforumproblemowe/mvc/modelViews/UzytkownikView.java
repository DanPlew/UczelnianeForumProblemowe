package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UzytkownikView implements Serializable {

    @NotBlank(message = "Pole jest puste")
    @Size(min = 5, max = 20, message ="Login musi zawierac od 5 do 20 znaków")
    private String login;

    @NotBlank(message = "Pole jest puste")
    @Size(min = 5, max = 20, message ="Haslo musi zawierac od 5 do 20 znaków")
    private String haslo;

    @NotBlank(message = "Pole jest puste")
    @Size(min = 3, max = 20, message ="Imie musi zawierac od 5 do 20 znaków")
    private String imie;

    @NotBlank(message = "Pole jest puste")
    @Size(min = 5, max = 40, message ="Email uczelniany musi zawierac od 5 do 40 znaków")
    @Pattern(regexp = "s\\d+@pjwstk.edu.pl", message = "Email musi zaczynac sie na s i konczyc na @pjwstk.edu.pl")
    private String emailUczelniany;

    private String emailPrywatny;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = zredukowanieSpacji(login);
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = zredukowanieSpacji(haslo);
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = zredukowanieSpacji(imie);
    }

    public String getEmailUczelniany() {
        return emailUczelniany;
    }

    public void setEmailUczelniany(String emailUczelniany) {
        this.emailUczelniany = emailUczelniany;
    }

    public String getEmailPrywatny() {
        return emailPrywatny;
    }

    public void setEmailPrywatny(String emailPrywatny) {
        this.emailPrywatny = emailPrywatny;
    }

    private String zredukowanieSpacji(String string){
        if(string != null) string = string.replaceAll("\\s+", "");
        return string;
    }
}
