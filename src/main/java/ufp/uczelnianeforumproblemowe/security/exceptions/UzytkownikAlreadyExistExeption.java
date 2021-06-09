package ufp.uczelnianeforumproblemowe.security.exceptions;

public class UzytkownikAlreadyExistExeption extends Exception{
    public UzytkownikAlreadyExistExeption(String errorMessage){
        super(errorMessage);
    }
}
