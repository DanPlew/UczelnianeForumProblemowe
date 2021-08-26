package ufp.uczelnianeforumproblemowe.jpa.models;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
public class Zgloszenie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private java.sql.Date dataZgloszenia;

    @Column(length = 2000)
    private String powodZgloszenia;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "oskarzony_id")
    private Uzytkownik oskarzony;

    public Zgloszenie(){}

    public Zgloszenie(String powodZgloszenia, Uzytkownik uzytkownik, Uzytkownik oskarzony){
        this.powodZgloszenia = powodZgloszenia;
        this.uzytkownik = uzytkownik;
        this.oskarzony = oskarzony;
        this.dataZgloszenia = Date.valueOf(LocalDate.now());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDataZgloszenia() {
        return dataZgloszenia;
    }

    public void setDataZgloszenia(){
        this.dataZgloszenia = Date.valueOf(LocalDate.now());
    }

    public String getPowodZgloszenia() {
        return powodZgloszenia;
    }

    public void setPowodZgloszenia(String powodZgloszenia) {
        this.powodZgloszenia = powodZgloszenia;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Uzytkownik getOskarzony() {
        return oskarzony;
    }

    public void setOskarzony(Uzytkownik oskarzony) {
        this.oskarzony = oskarzony;
    }
}
