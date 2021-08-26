package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import javax.validation.constraints.Size;

public class ZgloszenieView {

    @Size(max = 2000, message = "Zgłoszenie max 2000 znaków!")
    private String wiadomoscZgloszenia;

    private long idUzytkownik;
    private long idOskarzony;

    public ZgloszenieView() {}

    public ZgloszenieView(String wiadomoscZgloszenia, long idUzytkownik, long idOskarzony) {
        this.wiadomoscZgloszenia = wiadomoscZgloszenia;
        this.idUzytkownik = idUzytkownik;
        this.idOskarzony = idOskarzony;
    }

    public String getWiadomoscZgloszenia() {
        return wiadomoscZgloszenia;
    }

    public void setWiadomoscZgloszenia(String wiadomoscZgloszenia) {
        this.wiadomoscZgloszenia = wiadomoscZgloszenia;
    }

    public long getIdUzytkownik() {
        return idUzytkownik;
    }

    public void setIdUzytkownik(long idUzytkownik) {
        this.idUzytkownik = idUzytkownik;
    }

    public long getIdOskarzony() {
        return idOskarzony;
    }

    public void setIdOskarzony(long idOskarzony) {
        this.idOskarzony = idOskarzony;
    }
}
