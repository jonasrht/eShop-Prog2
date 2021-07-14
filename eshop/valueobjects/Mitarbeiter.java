package eshop.valueobjects;
/**
 * Klasse zur Repraesentation von Mitarbeitern.
 *
 * @author Jonas, Jana, Dabin
 * @extends Person
 */
public class Mitarbeiter extends Person {

    /**
     * Konstruktor
     *
     * @param name des Mitarbeiters
     * @param email des Mitarbeiters
     * @param passwort des Mitarbeiters
     */
    public Mitarbeiter(String name, String email, String passwort) {
        super(name, email, passwort);
    }
}
