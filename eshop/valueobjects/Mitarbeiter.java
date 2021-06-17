package eshop.valueobjects;
/**
 * Klasse zum passivem Erstellen von Mitarbeitern.
 *
 * @author Jonas, Jana, Dabina
 * @extends Person
 */
public class Mitarbeiter extends Person {

    //Konstruktor
    public Mitarbeiter(String name, String email, String passwort) {
        super(name, email, passwort);
    }
}
