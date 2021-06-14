package ufp.uczelnianeforumproblemowe.jpa.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TokenWeryfikacyjny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String token;

    @Column(updatable = false)
    @Basic(optional = false)
    private LocalDateTime dataWygasniecia;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

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
}
