package ufp.uczelnianeforumproblemowe.jpa.models;

import javax.persistence.*;

@Entity
public class Plik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nazwa;
    private String rozszerzenie;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @OneToOne(mappedBy = "plik")
    private Post post;

    @Lob
    private byte[] plikByte;

    public Plik(){}

    public Plik(String nazwa, String rozszerzenie, byte[] plikByte) {
        this.nazwa = nazwa;
        this.rozszerzenie = rozszerzenie;
        this.plikByte = plikByte;
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

    public String getRozszerzenie() {
        return rozszerzenie;
    }

    public void setRozszerzenie(String rozszerzenie) {
        this.rozszerzenie = rozszerzenie;
    }

    public byte[] getPlikByte() {
        return plikByte;
    }

    public void setPlikByte(byte[] plikByte) {
        this.plikByte = plikByte;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
}
