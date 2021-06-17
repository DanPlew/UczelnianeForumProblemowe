package ufp.uczelnianeforumproblemowe.logic.emailService;

public interface WysylanieEmailInterface {
    void wyslij(String doKogo, String wiadomosc)throws Exception;
    String zbudujMaila(String name, String link);
}
