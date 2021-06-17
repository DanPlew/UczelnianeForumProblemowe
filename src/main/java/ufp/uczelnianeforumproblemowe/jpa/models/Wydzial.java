package ufp.uczelnianeforumproblemowe.jpa.models;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;

import javax.persistence.*;
import java.util.List;

@Entity
public class Wydzial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private WydzialEnum nazwa;

    @OneToMany(mappedBy = "wydzial")
    private List<Uzytkownik> uzytkownikList;

    @OneToMany(mappedBy = "wydzial")
    private List<Watek> watki;

    public Wydzial() {}

    public Wydzial(WydzialEnum nazwa) {
        this.nazwa = nazwa;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WydzialEnum getNazwa() {
        return nazwa;
    }

    public void setNazwa(WydzialEnum nazwa) {
        this.nazwa = nazwa;
    }

    public List<Uzytkownik> getUzytkownikList() {
        return uzytkownikList;
    }

    public void setUzytkownikList(List<Uzytkownik> uzytkownikList) {
        this.uzytkownikList = uzytkownikList;
    }

    public List<Watek> getWatki() {
        return watki;
    }

    public void setWatki(List<Watek> watki) {
        this.watki = watki;
    }
}
