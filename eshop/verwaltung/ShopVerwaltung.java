package eshop.verwaltung;

import java.io.IOException;
import java.util.ArrayList;
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

    private ArtikelVerwaltung artikelVerwaltung;
    private KundenVerwaltung kundenVerwaltung;
    private MitarbeiterVerwaltung mitarbeiterVerwaltung;
    private EreignisVerwaltung ereignisVerwaltung;

    public ShopVerwaltung() {
        artikelVerwaltung = new ArtikelVerwaltung();
        kundenVerwaltung = new KundenVerwaltung();
        mitarbeiterVerwaltung = new MitarbeiterVerwaltung();
        ereignisVerwaltung = new EreignisVerwaltung();
    }

    // ArtikelVerwaltung

    public Artikel getArtikelViaID(int id) throws ArtikelNichtGefundenException {
        Artikel artikel;
        artikel = artikelVerwaltung.getArtikelViaID(id);
        return artikel;


    }

    public void bestandReduzieren(int id, Artikel artikel, int entfernen) {
        artikelVerwaltung.bestandReduzieren(artikel, entfernen);
    }

    public List<Artikel> gibAlleArtikel() {
        return artikelVerwaltung.gibAlleArtikel();
    }

    public void artikelSortieren() {
        artikelVerwaltung.artikelSortieren();
    }

    public void artikelNeu(int id, String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        artikelVerwaltung.artikelNeu(id, name, preis, bestand);
    }

    public void massenartikelNeu(String name, double preis, int bestand, int packungsGroesse)
            throws ArtikelExistiertBereitsException {
        artikelVerwaltung.massenartikelNeu(name, preis, bestand, packungsGroesse);
    }

    public void artikelBestandAendern(Artikel artikel, int newbestand) {
        artikelVerwaltung.artikelBestandAendern(artikel, newbestand);
    }

    public void produktIDSortieren() {
        artikelVerwaltung.produktIDSortieren();
    }

    public List<Artikel> sucheNachArtikel(String suchName) {
        return artikelVerwaltung.sucheNachArtikel(suchName);
    }

    // =================================
    // KundenVerwaltung

    public Kunden logInCustomer(String passwort, String email) throws LoginFehlgeschlagen {
        Kunden kunde;
        kunde = kundenVerwaltung.logInCustomer(passwort, email);
        return kunde;
    }

    public Kunden getCustomer(String passwort, String email) {
        Kunden kunde;
        kunde = kundenVerwaltung.getCustomer(passwort, email);
        return kunde;
    }

    public void registerCustomer(String name, String email, String passwort, String adresse) {
        kundenVerwaltung.registerCustomer(name, email, passwort, adresse);
    }

    public double guthabenAnzeigen(Kunden kunde) {
        return kundenVerwaltung.guthabenAnzeigen(kunde);
    }

    public void guthabenAufladen(Kunden kunde, double aufladen) {
        kundenVerwaltung.guthabenAufladen(kunde, aufladen);
    }

    public boolean valPassword(String password) {
        return kundenVerwaltung.valPassword(password);
    }

    public boolean checkPass(String password) {
        return kundenVerwaltung.checkPass(password);
    }

    // =================================
    // MitarbeiterVerwaltung
    public Mitarbeiter logInEmployee(String passwort, String email) throws LoginFehlgeschlagen {
        return mitarbeiterVerwaltung.logInEmployee(passwort, email);
    }

    public Mitarbeiter getEmployee(String passwort, String email) {
        return  mitarbeiterVerwaltung.getEmployee(passwort, email);
    }

    public void registerEmployee(String name, String email, String passwort) {
        mitarbeiterVerwaltung.registerEmployee(name, email, passwort);
    }

    // ==============================
    // Speicherung

    public void schreibeMitarbeiter() throws IOException {
        mitarbeiterVerwaltung.schreibeDaten();
    }

    public void schreibeKunden() throws IOException {
        kundenVerwaltung.schreibeDaten();
    }

    public void schreibeArtikel() throws IOException {
        artikelVerwaltung.schreibeDaten();
    }

    // =============================
    // Warenkorb
    // =============================

    public List<String> warenkorbAnzeigen(int id) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(id);
        // String warenkorb = kunde.getWarenkorb().getArtikelImKorb().toString();
        List<String> warenkorb = kunde.getWarenkorb().warenkorbAnzeigen();
        return warenkorb;
    }

    public void artikelInWarenkorb(int id, Artikel artikel, int anzahl) throws BestandZuGering, ArtikelNichtGefundenException {
        kundenVerwaltung.artikelInWarenkorb(id, artikel, anzahl);
    }

    public void artikelInWarenkorbLeeren(int id) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(id);
        kunde.getWarenkorb().artikelInWarenkorbLeeren();
    }

    public void anzahlArtikelAendern(int id, Artikel artikel, int anzahl) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(id);
        kunde.getWarenkorb().anzahlArtikelAendern(artikel, anzahl);
    }

    public List<String> rechnungErstellen(int persId) {
        Kunden kunde = kundenVerwaltung.getKundeViaID(persId);
        Rechnung rechnung1 = new Rechnung(kunde, kunde.getWarenkorb().getArtikelImKorb());
        List<String> rechnung = rechnung1.rechnungErstellen();
        kunde.getWarenkorb().artikelInWarenkorbLeeren();
        return rechnung;
    }

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

    public void ereignisEinfuegen(int artikelId, int anzahl, String ereignisMsg, int persId) {
        ereignisVerwaltung.ereignisEinfuegen(artikelId, anzahl, ereignisMsg, persId);
    }
}
