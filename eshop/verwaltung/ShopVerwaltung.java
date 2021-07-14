package eshop.verwaltung;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.exceptions.BestandZuGering;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.*;
/**
 * Klasse zum Verwalten des Shops.
 *
 * @author Jonas, Jana, Dabina
 *
 */
public class ShopVerwaltung implements EshopInterface {
    // Attribute
    private ArtikelVerwaltung artikelVerwaltung;
    private KundenVerwaltung kundenVerwaltung;
    private MitarbeiterVerwaltung mitarbeiterVerwaltung;
    private EreignisVerwaltung ereignisVerwaltung;

    /**
     * Konstruktor
     */
    public ShopVerwaltung() {
        artikelVerwaltung = new ArtikelVerwaltung();
        kundenVerwaltung = new KundenVerwaltung();
        mitarbeiterVerwaltung = new MitarbeiterVerwaltung();
        ereignisVerwaltung = new EreignisVerwaltung();
    }

    /**
     * Methode zum Finden des Artikels via ID
     *
     * @param id ID des Produktes
     * @return artikel Artikel, der gesucht wird
     * @throws ArtikelNichtGefundenException Fehler beim Finden
     */
    public Artikel getArtikelViaID(int id) throws ArtikelNichtGefundenException {
        Artikel artikel;
        artikel = artikelVerwaltung.getArtikelViaID(id);
        return artikel;


    }
    /**
     * Methode zum Reduzieren des Bestandes
     *
     * @param artikel welcher Artikel
     * @param entfernen Anzahl zu entfernenden Artikel
     */
    public void bestandReduzieren(int id, Artikel artikel, int entfernen) {
        EreignisVerwaltung.ereignisEinfuegen(artikel.getProduktID(), entfernen, "Bestand reduziert", id);
        artikelVerwaltung.bestandReduzieren(artikel, entfernen);
    }

    /**
     * Methode zum Ausgeben aller Artikel im Bestand
     *
     * @return artikelVerwaltung.gibAlleArtikel()
     */
    public List<Artikel> gibAlleArtikel() {
        return artikelVerwaltung.gibAlleArtikel();
    }
    /**
     * Methode zum Sortieren aller Artikel im Bestand nach dem Alphabet
     */
    public void artikelSortieren() {
        artikelVerwaltung.artikelSortieren();
    }
    /**
     * Methode zum Anlegen eines neuen Artikels
     *
     * @param name des Artikels
     * @param preis des Artikels
     * @param bestand wie viele werden angelegt
     * @throws ArtikelExistiertBereitsException Schutz vor doppelten Artikeln
     */
    public void artikelNeu(int id, String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        artikelVerwaltung.artikelNeu(id, name, preis, bestand);
    }

    /**
     * Methode zum Anlegen eines neuen Massengutartikels
     *
     * @param name des Massengutartikels
     * @param preis des Massengutartikels
     * @param bestand wie viele werden angelegt
     * @param packungsGroesse des Massengutartikels
     * @throws ArtikelExistiertBereitsException Schutz vor doppelten Massengutartikeln
     */
    public void massenartikelNeu(String name, double preis, int bestand, int packungsGroesse)
            throws ArtikelExistiertBereitsException {
        artikelVerwaltung.massenartikelNeu(name, preis, bestand, packungsGroesse);
    }

    /**
     * Methode zum Aendern des Artikelbestandes
     *
     * @param artikel welcher Artikel
     * @param newbestand die neue Anzahl
     */
    public void artikelBestandAendern(Artikel artikel, int newbestand) {
        artikelVerwaltung.artikelBestandAendern(artikel, newbestand);
    }
    /**
     * Methode zum Sortieren des Artikel via ID
     */
    public void produktIDSortieren() {
        artikelVerwaltung.produktIDSortieren();
    }

    public List<Artikel> sucheNachArtikel(String suchName) {
        return artikelVerwaltung.sucheNachArtikel(suchName);
    }

    // =================================
    // KundenVerwaltung

    /**
     * Methode zum Einloggen des Kunden
     *
     * @param passwort des Kunden
     * @param email des Kunden
     * @return kunde
     * @throws LoginFehlgeschlagen Fehler beim Login
     */
    public Kunden logInCustomer(String passwort, String email) throws LoginFehlgeschlagen {
        Kunden kunde;
        kunde = kundenVerwaltung.logInCustomer(passwort, email);
        return kunde;
    }
    /**
     * Methode zum Einloggen des Kunden
     *
     * @param passwort des Kunden
     * @param email des Kunden
     * @return kunde
     */
    public Kunden getCustomer(String passwort, String email) {
        Kunden kunde;
        kunde = kundenVerwaltung.getCustomer(passwort, email);
        return kunde;
    }
    /**
     * Methode zum Registrieren eines Kunden
     *
     * @param name Name des Kunden
     * @param passwort des Kunden
     * @param email des Kunden
     * @param adresse des Kunden
     */
    public void registerCustomer(String name, String email, String passwort, String adresse) {
        kundenVerwaltung.registerCustomer(name, email, passwort, adresse);
    }

    /**
     * Methode zum Anzeigen des GUthabens
     *
     * @param kunde Kunde
     * @return Guthaben
     */
    public double guthabenAnzeigen(Kunden kunde) {
        double g = kundenVerwaltung.guthabenAnzeigen(kunde);
        return g;
    }

    /**
     * Methode zum Aufladen des Guthabens
     *
     * @param kunde Kunde
     * @param aufladen Betrag,der zum Guthaben addiert wird
     */
    public void guthabenAufladen(Kunden kunde, double aufladen) {
        kundenVerwaltung.guthabenAufladen(kunde, aufladen);
    }

    /**
     * Methode zum Pruefen eines validen Passworts
     *
     * @param password Passwort
     * @return true or false
     */
    public boolean valPassword(String password) {
        boolean val = kundenVerwaltung.valPassword(password);
        return val;
    }
    /**
     * Methode zum Pruefen eines gueltigen Passworts
     *
     * @param password Passwort
     * @return true or false
     */
    public boolean checkPass(String password) {
        boolean check = kundenVerwaltung.checkPass(password);
        return check;
    }

    // =================================
    // MitarbeiterVerwaltung

    /**
     * Methode zum Einloggen eines Mitarbeiters
     *
     * @param passwort des Mitarbeiters
     * @param email des Mitarbeiters
     * @throws LoginFehlgeschlagen Fehler beim Login
     * @return mitarbeiter or null
     */
    public Mitarbeiter logInEmployee(String passwort, String email) throws LoginFehlgeschlagen {
        Mitarbeiter mitarbeiter = mitarbeiterVerwaltung.logInEmployee(passwort, email);
        return mitarbeiter;
    }
    /**
     * Methode zum Ausgeben eines Mitarbeiters
     *
     * @param passwort des Mitarbeiters
     * @param email des Mitarbeiters
     * @return mitarbeiter or null
     */
    public Mitarbeiter getEmployee(String passwort, String email) {
        Mitarbeiter mitarbeiter;
        mitarbeiter = mitarbeiterVerwaltung.getEmployee(passwort, email);
        return mitarbeiter;
    }
    /**
     * Methode zum Einloggen eines Mitarbeiters
     *
     * @param name Name des Mitarbeiters
     * @param email des Kunden
     * @param passwort des Kunden
     */
    public void registerEmployee(String name, String email, String passwort) {
        mitarbeiterVerwaltung.registerEmployee(name, email, passwort);
    }

    // ==============================
    // Speicherung
    /**
     * Methode zum Schreiben der Mitarbeiter in eine externe Datei.
     *
     * @throws IOException Fehlerpruefung
     */
    public void schreibeMitarbeiter() throws IOException {
        mitarbeiterVerwaltung.schreibeDaten();
    }
    /**
     * Methode zum Schreiben der Kunden in eine externe Datei.
     *
     * @throws IOException Fehlerpruefung
     */
    public void schreibeKunden() throws IOException {
        kundenVerwaltung.schreibeDaten();
    }
    /**
     * Methode zum Schreiben der Artikel in eine Datei.
     *
     * @throws IOException Feherpruefung
     */
    public void schreibeArtikel() throws IOException {
        artikelVerwaltung.schreibeDaten();
    }

    // =============================
    // Warenkorb
    /**
     * Methode zum Anzeigen des Warenkorbs.
     *
     * @param id ID des Kunden
     * @return warenkorb
     */
    public List<String> warenkorbAnzeigen(int id) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(id);
        // String warenkorb = kunde.getWarenkorb().getArtikelImKorb().toString();
        List<String> warenkorb = kunde.getWarenkorb().warenkorbAnzeigen();
        return warenkorb;
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
        kundenVerwaltung.artikelInWarenkorb(id, artikel, anzahl);
    }
    /**
     * Methode zum Leeren Warenkorb.
     *
     * @param id ID des Kunden
     */
    public void artikelInWarenkorbLeeren(int id) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(id);
        kunde.getWarenkorb().artikelInWarenkorbLeeren();
    }
    /**
     * Methode Aendern der Artikelanzahl.
     *
     * @param id ID des Kunden
     * @param artikel Produkt
     * @param anzahl der neue Wert
     */
    public void anzahlArtikelAendern(int id, Artikel artikel, int anzahl) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(id);
        kunde.getWarenkorb().anzahlArtikelAendern(artikel, anzahl);
    }

    /**
     * Methode zum Erstellen der Rechnung.
     *
     * @param persId ID des Kunden
     * @return rechnung
     */
    public List<String> rechnungErstellen(int persId) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(persId);
        Rechnung rechnung1 = new Rechnung(kunde, kunde.getWarenkorb().getArtikelImKorb());
        List<String> rechnung = rechnung1.rechnungErstellen();
        kunde.getWarenkorb().artikelInWarenkorbLeeren();
        return rechnung;
    }
    // =============================

    /**
     * Methode zum Speichern der Daten und Verbindungsabbruch.
     */
    public void verbindungsAbbruch() {
        try {
            kundenVerwaltung.schreibeDaten();
            mitarbeiterVerwaltung.schreibeDaten();
            artikelVerwaltung.schreibeDaten();
            ereignisVerwaltung.schreibeDaten();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // =============================
    // EreignisVerwaltung
    // =============================

    public List<String> getEreignisList() {
        List<String> ereignisListStr = new ArrayList<String>();
        List<Ereignis> ereignisList = ereignisVerwaltung.getEreignisList();
        for (Ereignis ereignis: ereignisList) {
            String ereignisStr = ereignis.toString();
            ereignisListStr.add(ereignisStr);
        }
        return ereignisListStr;
    }
}
