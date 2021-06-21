package ufp.uczelnianeforumproblemowe.mvc.modelViews;

public class PostView {

    private long idTematu;
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
