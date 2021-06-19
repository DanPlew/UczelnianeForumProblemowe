package ufp.uczelnianeforumproblemowe.mvc.modelViews;

public class WatekView {

    private long id;
    private String nazwa;
    private long idRodzica;

    public WatekView(){}

    public WatekView(long id, String nazwa, int idRodzica) {
        this.id = id;
        this.nazwa = nazwa;
        this.idRodzica = idRodzica;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public long getIdRodzica() {
        return idRodzica;
    }

    public void setIdRodzica(long idRodzica) {
        this.idRodzica = idRodzica;
    }
}
