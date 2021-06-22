package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostView {

    private long idTematu;

    @Size(max = 1000, message = "Wiadomość nie może być dłuższa niż 1000 znaków!")
    private String wiadomosc;

    private long idPliku;

    public long getIdTematu() {
        return idTematu;
    }

    public void setIdTematu(long idTematu) {
        this.idTematu = idTematu;
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public void setWiadomosc(String wiadomosc) {
        this.wiadomosc = wiadomosc;
    }

    public long getIdPliku() {
        return idPliku;
    }

    public void setIdPliku(long idPliku) {
        this.idPliku = idPliku;
    }
}
