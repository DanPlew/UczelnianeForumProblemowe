package ufp.uczelnianeforumproblemowe.jpa.models;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;
import ufp.uczelnianeforumproblemowe.mvc.modelViews.UzytkownikView;
import ufp.uczelnianeforumproblemowe.jpa.enums.RangaEnum;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Uzytkownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String login;

    private String haslo;

    private String imie;

    @Enumerated(EnumType.ORDINAL)
    private RangaEnum ranga;

    @Column(name = "czyKontoAktywne")
    private boolean aktywnoscKonta;

    @Column(name = "czyKontoZbanowane")
    private boolean czyZbanowany;

    @Column(unique = true)
    private String emailUczelniany;

    @Column(unique = true)
    private String emailPrywatny;

    @Column(unique = true)
    private String eska;

    private java.sql.Date dataRejestracji;

    @OneToMany(mappedBy = "uzytkownik")
    private List<TokenWeryfikacyjny> tokeny;

    @ManyToOne
    @JoinColumn(name = "wydzial_id")
    private Wydzial wydzial;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Watek> watki;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Temat> tematy;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Post> posty;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Plik> pliki;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Obserwowani> uzytkwonicy;

    @OneToMany(mappedBy = "obserwowany")
    private List<Obserwowani> obserwowani;

    @OneToMany(mappedBy = "uzytkownik")
    private List<Zgloszenie> zgloszenia;

    @OneToMany(mappedBy = "oskarzony")
    private List<Zgloszenie> oskarzenia;

    private WydzialEnum bierzacyWydzial;

    public Uzytkownik(){
        this.dataRejestracji = Date.valueOf(LocalDate.now());
        this.ranga = RangaEnum.UZYTKOWNIK;
        this.aktywnoscKonta = false;
        this.czyZbanowany = false;
    }

    public Uzytkownik(UzytkownikView uzytkownikView){
        this.login = uzytkownikView.getLogin();
        this.haslo = uzytkownikView.getHaslo();
        this.imie = uzytkownikView.getImie();
        this.emailPrywatny = uzytkownikView.getEmailPrywatny();

        setEmailUczelniany(uzytkownikView.getEmailUczelniany());

        this.dataRejestracji = Date.valueOf(LocalDate.now());
        this.ranga = RangaEnum.UZYTKOWNIK;
        this.aktywnoscKonta = false;
        this.czyZbanowany = false;
    }

    public Uzytkownik(String login, String haslo, String imie, RangaEnum ranga, String emailUczelniany, String emailPrywatny){
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.ranga = ranga;
        this.emailPrywatny = emailPrywatny;

        setEmailUczelniany(emailUczelniany);

        this.dataRejestracji = Date.valueOf(LocalDate.now());
        this.aktywnoscKonta = true;
        this.czyZbanowany = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public RangaEnum getRanga() {
        return ranga;
    }

    public void setRanga(RangaEnum ranga) {
        this.ranga = ranga;
    }

    public boolean isAktywnoscKonta() {
        return aktywnoscKonta;
    }

    public void setAktywnoscKonta(boolean aktywnoscKonta) {
        this.aktywnoscKonta = aktywnoscKonta;
    }

    public String getEmailUczelniany() {
        return emailUczelniany;
    }

    public void setEmailUczelniany(String emailUczelniany) {
        this.emailUczelniany = emailUczelniany;

        String[] tablicaString = emailUczelniany.split("@");
        this.eska = tablicaString[0];
    }

    public String getEmailPrywatny() {
        return emailPrywatny;
    }

    public void setEmailPrywatny(String emailPrywatny) {
        this.emailPrywatny = emailPrywatny;
    }

    public String getEska() {
        return eska;
    }

    public Date getDataRejestracji() {
        return dataRejestracji;
    }

    public void setDataRejestracji(Date dataRejestracji) {
        this.dataRejestracji = dataRejestracji;
    }

    public List<TokenWeryfikacyjny> getTokeny() {
        return tokeny;
    }

    public void setTokeny(List<TokenWeryfikacyjny> tokeny) {
        this.tokeny = tokeny;
    }

    public Wydzial getWydzial() {
        return wydzial;
    }

    public void setWydzial(Wydzial wydzial) {
        this.bierzacyWydzial = wydzial.getNazwa();
        this.wydzial = wydzial;
    }

    public List<Watek> getWatki() {
        return watki;
    }

    public void setWatki(List<Watek> watki) {
        this.watki = watki;
    }

    public List<Temat> getTematy() {
        return tematy;
    }

    public void setTematy(List<Temat> tematy) {
        this.tematy = tematy;
    }

    public WydzialEnum getBierzacyWydzial() {
        return bierzacyWydzial;
    }

    public void setBierzacyWydzial(WydzialEnum bierzacyWydzial) {
        this.bierzacyWydzial = bierzacyWydzial;
    }

    public List<Post> getPosty() {
        return posty;
    }

    public void setPosty(List<Post> posty) {
        this.posty = posty;
    }

    public List<Plik> getPliki() {
        return pliki;
    }

    public void setPliki(List<Plik> pliki) {
        this.pliki = pliki;
    }

    public List<Obserwowani> getUzytkwonicy() {
        return uzytkwonicy;
    }

    public void setUzytkwonicy(List<Obserwowani> uzytkwonicy) {
        this.uzytkwonicy = uzytkwonicy;
    }

    public List<Obserwowani> getObserwowani() {
        return obserwowani;
    }

    public void setObserwowani(List<Obserwowani> obserwowani) {
        this.obserwowani = obserwowani;
    }

    public List<Zgloszenie> getZgloszenia() {
        return zgloszenia;
    }

    public void setZgloszenia(List<Zgloszenie> zgloszenia) {
        this.zgloszenia = zgloszenia;
    }

    public List<Zgloszenie> getOskarzenia() {
        return oskarzenia;
    }

    public void setOskarzenia(List<Zgloszenie> oskarzenia) {
        this.oskarzenia = oskarzenia;
    }

    public boolean isCzyZbanowany() {
        return czyZbanowany;
    }

    public void setCzyZbanowany(boolean czyZbanowany) {
        this.czyZbanowany = czyZbanowany;
    }

    public int getLiczbaZgloszen(){
        return getOskarzenia().size();
    }
}
