package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class PostView implements Serializable {

    private long idTematu;

    @Size(max = 2000, message = "Wiadomość nie może być dłuższa niż 2000 znaków!")
    private String wiadomosc;

    private long idPliku;

    public PostView(){ }

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
