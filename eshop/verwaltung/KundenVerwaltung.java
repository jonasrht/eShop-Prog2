package eshop.verwaltung;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.exceptions.BestandZuGering;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.valueobjects.*;
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;

/**
 * Klasse zum Verwalten der Kunden.
 *
 * @author Jonas, Jana, Dabina
 */
public class KundenVerwaltung {
    // Attribute
    // Persistenz_Schnittstelle der Kunden in einem Array anlegen
    private List<Kunden> kundenListe = new ArrayList<Kunden>();
    private PersistenceManager pm = new FilePersistenceManager();

    /**
     * Konstruktor
     *
     */
    public KundenVerwaltung() {
        // Fehlerbehandlung
        try {
            // führt Methode aus
            liesDaten();
            // wenn Fehler, dann Fehlerausgabe
        } catch (IOException e) {
            e.printStackTrace();
        }
        kundenListe.add(new Kunden("Dabby", "1", "1", "Bremen 1", 5.50));
    }

    /**
     * Methode zur Datenerfassung.
     *
     * @throws IOException Fehlerpruefung
     */
    public void liesDaten() throws IOException { // Methode mit Fehlerprüfung
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading("Kunden.txt");
        Kunden einKunde;
        do {
            // Artikel-Objekt einlesen
            einKunde = pm.ladeKunde();
            if (einKunde != null) {
                // Artiekl in Liste einfügen
                kundenListe.add(einKunde);
            }
        } while (einKunde != null); // wenn nicht, dann close
    }

    /**
     * Methode zum Schreiben der Kunden in eine externe Datei.
     *
     * @throws IOException Fehlerpruefung
     */
    public void schreibeDaten() throws IOException {
        // PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting("Kunden.txt");
        // Mitarbeiter wird im PersistenzManager gespeichert
        for (Kunden kunde : kundenListe) {
            pm.speichereKunden(kunde);
        }
        // Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    /**
     * Methode zum Einloggen der Kunden
     *
     * @param passwort Passwort vom Kunden
     * @param email    E-Mail vom Kunden
     * @return erfolgreiches oder gescheitertes Einloggen
     * @throws LoginFehlgeschlagen Fehler beim Login
     */
    public Kunden logInCustomer(String passwort, String email) throws LoginFehlgeschlagen {
        // durchläuft die Array-Liste und sucht nach Kunden
        for (Kunden kunde : kundenListe) {
            // prüft, ob die übergebenen Parameter mit den gespeicherten Paramtern
            // übereinstimmen
            if ((email.equals(kunde.getEmail()) && (passwort.equals(kunde.getPasswort())))) {
                return kunde;
            }
        }
        return null;
    }

    /**
     * Methode zum Finden der Kunden
     *
     * @param passwort Passwort vom Kunden
     * @param email    E-Mail vom Kunden
     * @return Mitarbeiter oder nichts
     */
    public Kunden getCustomer(String passwort, String email) {
        // durchläuft die Array-Liste und sucht nach Kunde
        for (Kunden kunde : kundenListe) {
            // prüft, ob die übergebenen Parameter mit den gespeicherten Paramtern
            // übereinstimmen
            if ((email.equals(kunde.getEmail()) && (passwort.equals(kunde.getPasswort())))) {
                return kunde;
            }
        }
        // Suche fehlgeschlagen, nichts wird zurückgegeben
        return null;
    }

    /**
     * Methode zum Registrieren von Kunden
     *
     * @param name     Name vom Kunden
     * @param passwort Passwort vom Kunden
     * @param email    E-Mail vom Kunden
     * @param adresse  Adresse vom Kunden
     */
    public void registerCustomer(String name, String email, String passwort, String adresse) {
        // schenken 15 Euro
        double guthaben = 15;
        // Mitarbeiter werden in der Array-Liste mit den übergebenen Parametern
        // hinzugefügt
        kundenListe.add(new Kunden(name, email, passwort, adresse, guthaben));
    }

    /**
     * Methode zum Anzeigen des Guthabens der Kunden
     * 
     * @param kunde Kunde
     * @return Guthaben des Kunden
     */
    public double guthabenAnzeigen(Kunden kunde) {
        // Methode wird aufgerufen und zurückgegeben
        return kunde.getGuthaben();
    }

    /**
     * Methode zum Aufalden des Guthabens der Kunden
     *
     * @param kunde    Kunde
     * @param aufladen Betrag, der aufgeladen wird vom Kunden
     */
    public void guthabenAufladen(Kunden kunde, double aufladen) {
        // Parameter des jetzigen Guthabens wird definiert
        double currentguthaben;
        // durchläuft die Array-Liste und sucht nach Kunde
        for (Kunden kunde1 : kundenListe) {
            if (kunde1.equals(kunde)) {
                // jetzige Guthaben wird dem Parameter übergeben
                currentguthaben = kunde.getGuthaben();
                // addierter Guthaben wird dem jetzigen Guthaben raufaddiert
                kunde.setGuthaben(currentguthaben + aufladen);
            }
        }
        System.out.println("Aufladen erfolgreich, Guthaben beträgt jetzt:");
        System.out.println(kunde.getGuthaben() + "€");

    }

    /**
     * Methode zum Prüfen eines sicheren Passworts (Länge, Ziffer, Groß- und
     * Kleinbuchstaben)
     * 
     * @param password Passwort vom Kunden
     * @return true or false
     */
    public boolean valPassword(String password) {
        // sobald die Passwortlänge mehr als sieben Zeichen entspricht
        if (password.length() > 7) {
            // ...wird das Passwort auf weitere Anforderungen untersucht, Methode
            // checkPass() wird aufgerufen
            if (checkPass(password)) {
                System.out.println(password + " ist okay");
                return true;
            } else {
                return false;
            }
            // soblad sie weniger als acht Zeichen hat, wird sie nicht akzeptiert
        } else {
            System.out.println(password + "Too short!");
            return false;
        }
    }

    /**
     * Methode zum Prüfen eines sicheren Passworts anhand von mindestens einem Groß
     * und Klein -buchstaben sowie einer Zahl.
     * 
     * @param password Passwort vom Kunden
     * @return true or false
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

    /**
     * Methode zum Finden des Kunden via ID.
     *
     * @param id des Kunden
     * @return kunde or null
     */
    public Kunden getKundeViaID(int id) {
        for (Kunden kunde : kundenListe) {
            if (id == kunde.getPersonID()) {
                return kunde;
            }
        }
        return null; // keinen Kunden gefunden
    }

    // ==============================
    // Warenkorb
    // ==============================
    /**
     * Methode zum Anzeigen des Warenkorbs.
     *
     * @param id des Kunden
     */
    public void warenkorbAnzeigen(int id) {
        Kunden kunde = getKundeViaID(id);
        kunde.getWarenkorb().warenkorbAnzeigen();
    }


    /**
     * Methode zum Legen der Artiekl in den Warenkorb.
     *
     * @param id des Kunden
     * @param artikel die der Kunde in den Warenkorb hinzufuegt
     * @param anzahl wie viele Artikel
     * @throws BestandZuGering darf nicht mehr kaufen als wir haben
     */
    public void artikelInWarenkorb(int id, Artikel artikel, int anzahl) throws BestandZuGering, ArtikelNichtGefundenException {
        Kunden kunde = getKundeViaID(id);
        kunde.getWarenkorb().artikelInWarenkorb(artikel, anzahl);
    }

}
