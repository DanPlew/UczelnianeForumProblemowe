package ufp.uczelnianeforumproblemowe.jpa.models;

import javax.persistence.*;

@Entity
public class Obserwowani {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "obserwowany_id")
    private Uzytkownik obserwowany;

    public Obserwowani(){}

    public Obserwowani(Uzytkownik uzytkownik, Uzytkownik obserwowany) {
        this.uzytkownik = uzytkownik;
        this.obserwowany = obserwowany;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Uzytkownik getObserwowany() {
        return obserwowany;
    }

    public void setObserwowany(Uzytkownik obserwowany) {
        this.obserwowany = obserwowany;
    }
}
