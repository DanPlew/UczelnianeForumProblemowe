package ufp.uczelnianeforumproblemowe.jpa.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class TokenWeryfikacyjny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String token;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp dataZalozenia;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime dataWygasniecia;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    private boolean czyKontoAktywne;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getDataZalozenia() {
        return dataZalozenia;
    }

    public void setDataZalozenia(Timestamp dataZalozenia) {
        this.dataZalozenia = dataZalozenia;
    }

    public LocalDateTime getDataWygasniecia() {
        return dataWygasniecia;
    }

    public void setDataWygasniecia(LocalDateTime dataWygasniecia) {
        this.dataWygasniecia = dataWygasniecia;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public boolean isCzyKontoAktywne() {
        return czyKontoAktywne;
    }

    public void setCzyKontoAktywne(boolean czyKontoAktywne) {
        this.czyKontoAktywne = czyKontoAktywne;
    }


}
