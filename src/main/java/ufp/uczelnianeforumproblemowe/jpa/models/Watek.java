package ufp.uczelnianeforumproblemowe.jpa.models;


import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(name = "procedura", procedureName = "PobierzWszystkiePodwatkiDanegoWatku",
                parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "idNadwatku", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "suma", type = Integer.class)})
)
public class Watek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nazwa;
    private java.sql.Date dataUtworzenia;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @ManyToOne
    @JoinColumn(name = "wydzial_id")
    private Wydzial wydzial;

    @ManyToOne
    @JoinColumn(name = "parentWatek_id")
    private Watek parentWatek;

    @OneToMany(mappedBy="parentWatek", cascade = CascadeType.REMOVE)
    private List<Watek> podWatki;

    @OneToMany(mappedBy = "watek", cascade = CascadeType.REMOVE)
    private List<Temat> tematy;

    public Watek(){
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
    }

    public Watek(String nazwa) {
        this.nazwa = nazwa;
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

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Wydzial getWydzial() {
        return wydzial;
    }

    public void setWydzial(Wydzial wydzial) {
        this.wydzial = wydzial;
    }

    public List<Watek> getPodWatki() {
        return podWatki;
    }

    public void setPodWatki(List<Watek> podWatki) {
        this.podWatki = podWatki;
    }

    public Watek getParentWatek() {
        return parentWatek;
    }

    public void setParentWatek(Watek parentWatek) {
        this.parentWatek = parentWatek;
    }

    public Date getDataUtworzenia() {
        return dataUtworzenia;
    }

    public void setDataUtworzenia() {
        this.dataUtworzenia = Date.valueOf(LocalDate.now());
    }

    public List<Temat> getTematy() {
        return tematy;
    }

    public void setTematy(List<Temat> tematy) {
        this.tematy = tematy;
    }
}