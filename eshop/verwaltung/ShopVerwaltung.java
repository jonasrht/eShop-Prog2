package eshop.verwaltung;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.BestandZuGering;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.*;

public class ShopVerwaltung implements EshopInterface {

    private ArtikelVerwaltung artikelVerwaltung;
    private KundenVerwaltung kundenVerwaltung;
    private MitarbeiterVerwaltung mitarbeiterVerwaltung;

    public ShopVerwaltung() {
        artikelVerwaltung = new ArtikelVerwaltung();
        kundenVerwaltung = new KundenVerwaltung();
        mitarbeiterVerwaltung = new MitarbeiterVerwaltung();
    }

    // ArtikelVerwaltung

    public Artikel getArtikelViaID(int id) {
        Artikel artikel;
        artikel = artikelVerwaltung.getArtikelViaID(id);
        return artikel;
    }

    public void bestandReduzieren(Artikel artikel, int entfernen) {
        artikelVerwaltung.bestandReduzieren(artikel, entfernen);
    }

    public List<Artikel> gibAlleArtikel() {
        return artikelVerwaltung.gibAlleArtikel();
    }

    public void artikelSortieren() {
        artikelVerwaltung.artikelSortieren();
    }

    public void artikelNeu(String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        artikelVerwaltung.artikelNeu(name, preis, bestand);
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
        double g = kundenVerwaltung.guthabenAnzeigen(kunde);
        return g;
    }

    public void guthabenAufladen(Kunden kunde, double aufladen) {
        kundenVerwaltung.guthabenAufladen(kunde, aufladen);
    }

    public boolean valPassword(String password) {
        boolean val = kundenVerwaltung.valPassword(password);
        return val;
    }

    public boolean checkPass(String password) {
        boolean check = kundenVerwaltung.checkPass(password);
        return check;
    }

    // =================================
    // MitarbeiterVerwaltung
    public Mitarbeiter logInEmployee(String passwort, String email) throws LoginFehlgeschlagen {
        Mitarbeiter mitarbeiter = mitarbeiterVerwaltung.logInEmployee(passwort, email);
        return mitarbeiter;
    }

    public Mitarbeiter getEmployee(String passwort, String email) {
        Mitarbeiter mitarbeiter;
        mitarbeiter = mitarbeiterVerwaltung.getEmployee(passwort, email);
        return mitarbeiter;
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

    public void artikelInWarenkorb(int id, Artikel artikel, int anzahl) throws BestandZuGering {
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
