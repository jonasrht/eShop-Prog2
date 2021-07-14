package eshop.interfaces;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.exceptions.BestandZuGering;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Ereignis;
import eshop.valueobjects.Kunden;
import eshop.valueobjects.Mitarbeiter;

public interface EshopInterface {

    // Artikel Verwaltung
    Artikel getArtikelViaID(int id) throws ArtikelNichtGefundenException;

    void bestandReduzieren(int id, Artikel artikel, int entfernen); // x

    List<Artikel> gibAlleArtikel() throws IOException; // x

    void artikelSortieren(); // x

    void artikelNeu(int id, String name, double preis, int bestand) throws ArtikelExistiertBereitsException; // x

    void massenartikelNeu(String name, double preis, int bestand, int packungsGroesse) // x
            throws ArtikelExistiertBereitsException;

    void artikelBestandAendern(Artikel artikel, int newbestand); // x

    void produktIDSortieren(); // x

    List<Artikel> sucheNachArtikel(String suchName);

    // Kunden Verwaltung
    Kunden logInCustomer(String passwort, String email) throws LoginFehlgeschlagen; // x

    Kunden getCustomer(String passwort, String email); // x

    void registerCustomer(String name, String email, String passwort, String adresse); // x

    // double guthabenAnzeigen(Kunden kunde);

    // void guthabenAufladen(Kunden kunde, double aufladen);

    boolean valPassword(String password); // x

    boolean checkPass(String password); // x

    // Mitarbeiter Verwaltung
    Mitarbeiter logInEmployee(String passwort, String email) throws LoginFehlgeschlagen; // x

    Mitarbeiter getEmployee(String passwort, String email); // x

    void registerEmployee(String name, String email, String passwort); // x

    // Warenkorb
    List<String> warenkorbAnzeigen(int id);

    void artikelInWarenkorb(int id, Artikel artikel, int anzahl) throws BestandZuGering, ArtikelNichtGefundenException;

    void artikelInWarenkorbLeeren(int id);

    void anzahlArtikelAendern(int id, Artikel artikel, int anzahl);

    // Rechnung
    List<String> rechnungErstellen(int id);

    void verbindungsAbbruch();

    List<String> getEreignisList();

}