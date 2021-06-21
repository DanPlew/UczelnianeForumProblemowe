package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TematView implements Serializable {

    private long id;

    private long idRodzica;

    @NotBlank(message = "Pole jest wymagane!")
    @Size(min = 3, max = 20, message ="Nazwa musi zawierac od 3 do 20 znaków!")
    private String nazwa;

    @Size(max = 100, message ="Opis musi zawierac do 100 znaków!")
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
