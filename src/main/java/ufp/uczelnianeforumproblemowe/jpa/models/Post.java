package ufp.uczelnianeforumproblemowe.jpa.models;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String wiadomosc;
    private java.sql.Date dataUtworzenia;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "temat_id")
    private Temat temat;

//    @OneToMany(mappedBy = "post")
//    List<Plik> pliki;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plik_id", referencedColumnName = "id")
    private Plik plik;

    public Post(){
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
    }

    public Post(String wiadomosc) {
        this.wiadomosc = wiadomosc;
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public void setWiadomosc(String wiadomosc) {
        this.wiadomosc = wiadomosc;
    }

    public Date getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia() {
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Temat getTemat() {
        return temat;
    }

    public void setTemat(Temat temat) {
        this.temat = temat;
    }

    public Plik getPlik() {
        return plik;
    }

    public void setPlik(Plik plik) {
        this.plik = plik;
    }

    //    public List<Plik> getPliki() {
//        return pliki;
//    }
//
//    public void setPliki(List<Plik> pliki) {
//        this.pliki = pliki;
//    }
}
