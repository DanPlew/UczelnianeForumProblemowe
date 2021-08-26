package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import javax.validation.constraints.Size;
import java.io.Serializable;

public class TematView implements Serializable {

    private long id;

    private long idRodzica;

    @Size(min = 5, max = 50, message ="Nazwa musi zawierac od 5 do 50 znaków!")
    private String nazwa;

    @Size(max = 200, message ="Opis musi zawierac do 200 znaków!")
    private String opis;

    public TematView() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdRodzica() {
        return idRodzica;
    }

    public void setIdRodzica(long idRodzica) {
        this.idRodzica = idRodzica;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
