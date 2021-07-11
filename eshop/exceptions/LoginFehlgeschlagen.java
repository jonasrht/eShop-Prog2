package eshop.exceptions;
/**
 * Klasse zum Anzeigen eines Login-Fehlschlags.
 *
 * @author Jonas, Jana, Dabina
 * @extends Exception
 */
public class LoginFehlgeschlagen extends Exception{

    public LoginFehlgeschlagen(){
        super("Email oder Passwort stimmt nicht.");
    }

}
