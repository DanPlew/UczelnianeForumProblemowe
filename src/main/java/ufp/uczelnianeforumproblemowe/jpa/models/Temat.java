package ufp.uczelnianeforumproblemowe.jpa.models;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
public class Temat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nazwa;
    private String opis;
    private java.sql.Date dataUtworzenia;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "watek_id")
    private Watek watek;

    public Temat() {
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
    }

    public Temat(String nazwa, String opis) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Date getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia(){
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Watek getWatek() {
        return watek;
    }

    public void setWatek(Watek watek) {
        this.watek = watek;
    }
}
