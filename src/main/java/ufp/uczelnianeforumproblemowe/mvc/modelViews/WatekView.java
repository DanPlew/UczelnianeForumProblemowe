package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class WatekView implements Serializable {

    private long id;

    @Size(max = 30, message ="Nazwa musi zawierac do 30 znaków!")
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
