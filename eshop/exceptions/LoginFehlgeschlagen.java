package eshop.exceptions;
/**
 * Klasse zum Anzeigen eines Login-Fehlschlags.Exception
 *
 * @author Jonas, Jana, Dabina
 * @extends Exception
 */
public class LoginFehlgeschlagen extends Exception{
    /**
     * Konstruktor
     *
     */
    public LoginFehlgeschlagen(){
        super("Email oder Passwort stimmt nicht.");
    }

}
