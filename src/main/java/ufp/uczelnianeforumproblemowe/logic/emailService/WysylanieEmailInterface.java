package ufp.uczelnianeforumproblemowe.logic.emailService;

public interface WysylanieEmailInterface {
    void wyslij(String doKogo, String wiadomosc);
    String zbudujMaila(String name, String link);
}
