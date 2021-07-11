package eshop.verwaltung;

//Imoport aus der Java Bibliothek
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eshop.exceptions.LoginFehlgeschlagen;
import eshop.valueobjects.*;
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;

/**
 * Klasse zum Verwalten von Mitarbeitern.
 *
 * @author Jonas, Jana, Dabina
 * - Verwaltung der Mitarbeiter in List/Vector mit Generics
 * - außerdem Einsatz von Interfaces (List)
 * - Import aus der Java Bibliotheck
 */
public class MitarbeiterVerwaltung {
    // Attribute
    // Persistenz_Schnittstelle der Mitarbeiter in einem Array anlegen
    private List<Mitarbeiter> mitarbeiterListe = new ArrayList<Mitarbeiter>();
    // erstellt Persistenz-Manager
    private PersistenceManager pm = new FilePersistenceManager();

    /**
     * Methode zur Erkennung von schon bestehenden Mitarbeitern
     *
     * @throws IOException
     */
    public MitarbeiterVerwaltung() {
        // Fehlerbehandlung
        try {
            // führt Methode aus
            liesDaten();
            // wenn Fehler, dann Fehlerausgabe
        } catch (IOException e) {
            e.printStackTrace();
        }
        // mitarbeiterListe.add(new Mitarbeiter("Jana", "1", "1"));
    }

    /**
     * Methode zur Datenerfassung
     *
     * @throws IOException
     */
    public void liesDaten() throws IOException { // Methode mit Fehlerprüfung
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading("Mitarbeiter.txt");
        Mitarbeiter einMitarbeiter;
        do {
            // Mitarbeiter-Objekt einlesen und auf Überschneidung prüfen
            einMitarbeiter = pm.ladeMitarbeiter();
            if (einMitarbeiter != null) {
                // Artiekl in Liste einfügen
                mitarbeiterListe.add(einMitarbeiter);
            }
        } while (einMitarbeiter != null); // wenn nicht, dann close
    }

    /**
     * Methode zum Schreiben der Mitarbeiter in eine Datei.
     *
     * @throws IOException
     */
    public void schreibeDaten() throws IOException {
        // PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting("Mitarbeiter.txt");
        // Mitarbeiter wird im PersistenzManager gespeichert
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            pm.speichereMitarbeiter(mitarbeiter);
        }
        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    // TODO: Exception throw
    /**
     * Methode zum Einloggen der Mitarbeiter
     *
     * @param passwort, Passwort vom Mitarbeiter
     * @param email,    E-Mail vom Mitarbeiter
     * @return erfolgreiches oder gescheitertes Einloggen
     */
    public Mitarbeiter logInEmployee(String passwort, String email) throws LoginFehlgeschlagen {
        // durchläuft die Array-Liste und sucht nach Mitarbeiter
        System.out.println("P: " + passwort + " E: " + email);
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            // prüft, ob die übergebenen Parameter mit den gespeicherten Paramtern
            // übereinstimmen
            if ((email.equals(mitarbeiter.getEmail()) && (passwort.equals(mitarbeiter.getPasswort())))) {
                System.out.println("match found");
                return mitarbeiter;
            }
        }
        return null;
    }

    /**
     * Methode zum Finden der Mitarbeiter
     *
     * @param passwort, Passwort vom Mitarbeiter
     * @param email,    E-Mail vom Mitarbeiter
     * @return Mitarbeiter oder nichts
     */
    public Mitarbeiter getEmployee(String passwort, String email) {
        // durchläuft die Array-Liste und sucht nach Mitarbeiter
        for (Mitarbeiter mitarbeiter : mitarbeiterListe) {
            // prüft, ob die übergebenen Parameter mit den gespeicherten Paramtern
            // übereinstimmen
            if ((email.equals(mitarbeiter.getEmail()) && (passwort.equals(mitarbeiter.getPasswort())))) {
                return mitarbeiter;
            }
        }
        // Suche fehlgeschlagen, nichts wird zurückgegeben
        return null;
    }

    /**
     * Methode zum Registrieren von Mitarbeitern
     *
     * @param name,     Name vom Mitarbeiter
     * @param passwort, Passwort vom Mitarbeiter
     * @param email,    E-Mail vom Mitarbeiter
     */
    public void registerEmployee(String name, String email, String passwort) {
        // Mitarbeiter werden in der Array-Liste mit den übergebenen Parametern
        // hinzugefügt
        mitarbeiterListe.add(new Mitarbeiter(name, email, passwort));
    }

    /**
     * Methode zum Prüfen eines sicheren Passworts (Länge, Ziffer, Groß- und
     * Kleinbuchstaben)
     * 
     * @param password, Passwort vom Mitarbeiter
     * @return Richtigkeit
     */
    public boolean valPassword(String password) {
        // sobald die Passwortlänge mehr als sieben Zeichen entspricht
        if (password.length() > 7) {
            // ...wird das Passwort auf weitere Anforderungen untersucht, Methode
            // checkPass() wird aufgerufen
            if (checkPass(password)) {
                return true;
            } else {
                return false;
            }
            // soblad sie weniger als acht Zeichen hat, wird sie nicht akzeptiert
        } else {
            System.out.println("Too short!");
            return false;
        }
    }

    /**
     * Methode zum Prüfen eines sicheren Passworts anhand von mindestens einem Groß
     * und Klein -buchstaben sowie einer Zahl
     * 
     * @param password, Passwort vom Mitarbeiter
     * @return Richtigkeit
     */
    public boolean checkPass(String password) {
        // Variablen definieren und vorsichtshalber auf false setzen
        boolean hasNum = false;
        boolean hasCap = false;
        boolean hasLow = false;
        char c;
        // durchläuft Array
        for (int i = 0; i < password.length(); i++) {
            // das zurzeit durchlaufende Index wird dem Parameter c übergeben
            c = password.charAt(i);
            // checkt für jeden Index:
            // ...ob es sich um eine Ziffer handelt
            if (Character.isDigit(c)) {
                hasNum = true;
                // ...ob es sich um einen Großbuchstaben handelt
            } else if (Character.isUpperCase(c)) {
                hasCap = true;
                // ...ob es sich um einen Kleinbuchstaben handelt
            } else if (Character.isLowerCase(c)) {
                hasLow = true;
            }
            // treten alle drei Fälle auf, wird true ausgegeben
            if (hasNum && hasCap && hasLow) {
                return true;
            }
        }
        return false;
    }

}
