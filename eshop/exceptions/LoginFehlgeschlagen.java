package eshop.exceptions;

public class LoginFehlgeschlagen extends Exception{

    public LoginFehlgeschlagen(){
        super("Email oder Passwort stimmt nicht.");
    }

}
