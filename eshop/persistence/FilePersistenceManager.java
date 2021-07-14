package eshop.persistence;

import eshop.valueobjects.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;

/**
 * Klasse zum Verwalten der FilePersistenceManager Schnittstelle.Realisierung einer "Mockup"-Schnittstelle zur persistenten Speicherung von Daten in Dateien.Bedeutet: einige wenige Artikel werden "generiert" und zurueckgegeben.
 *
 * @author Jonas, Jana, Dabina
 * @implements PersistenceManager
 * - Import aus der Java Bibliotheck
 */
public class FilePersistenceManager implements PersistenceManager {
    private BufferedReader reader = null; // Eingabe zeilenweise lesen und das Ergebnis in einen primitiven Typ umwandeln
    private PrintWriter writer = null;  // Druckt formatierte Darstellungen von Objekten in einen Textausgabestream
    /**
     * Methode zum Öffnen fürs Lesen der Datei.
     *
     * @param datei Datei, die eingelesen wird
     * @throws FileNotFoundException nicht gefunden
     */
    public void openForReading(String datei) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(datei));
    }
    /**
     * Methode zum Öffnen fürs Bearbeiten der Datei.
     *
     * @param datei Datei, die eingelesen wird
     * @throws IOException Fehlerpruefung
     */
    public void openForWriting(String datei) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
    }
    /**
     * Methode zum Schließen des Writers und Readers.
     *
     * @return true, falss erfolgreich, sonst false
     */
    public boolean close() {
        if (writer != null)
            writer.close();

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                return false;
            }
        }

        return true;
    }

    /**
     * Methode zum Einlesen der Artikeldaten aus einer externen Datenquelle.
     *
     * @throws IOException Fehlerpruefung
     * @return Artikel-Objekt, wenn Einlesen erfolgreich, sonst null
     */
    public Artikel ladeArtikel() throws IOException {
        // Name einlesen
        String name = liesZeile();
        if (name == null) {
            // keine Daten mehr vorhanden
            return null;
        }
        // Nummer einlesen ...
        String preisString = liesZeile();
        // ... und von String in int konvertieren
        double preis = Double.parseDouble(preisString);

        String bestandString = liesZeile();
        // Codierung des Bestandstatus in boolean umwandeln
        int bestand = Integer.parseInt(bestandString);

        // neues Artikel-Objekt anlegen und zurückgeben
        return new Artikel(name, preis, bestand);
    }

    /**
     * Methode zum Einlesen der Massengutartikeldaten aus einer externen Datenquelle.
     *
     * @throws IOException Fehlerpruefung
     * @return Massengutartikel-Objekt, wenn Einlesen erfolgreich, sonst null
     */
    public Massengutartikel ladeMassengutartikel() throws IOException {
        // Name einlesen
        String name = liesZeile();
        if (name == null) {
            // keine Daten mehr vorhanden
            return null;
        }
        // Nummer einlesen ...
        String preisString = liesZeile();
        // ... und von String in int konvertieren
        double preis = Double.parseDouble(preisString);
        String bestandString = liesZeile();
        // Codierung des Ausleihstatus in boolean umwandeln
        int bestand = Integer.parseInt(bestandString);
        String packungGrString = liesZeile();
        int packungGr = Integer.parseInt(packungGrString);
        String fromFile = "";

        return new Massengutartikel(name, preis, bestand /packungGr, packungGr, fromFile);
    }

    /**
     * Methode zum Schreiben der Artikeldaten in eine externe Datenquelle. Das
     * Verfügbarkeitsattribut wird in der Datenquelle (Datei) als "t" oder "f"
     * codiert abgelegt.
     *
     * @param artikel Artikel-Objekt, das gespeichert werden soll
     * @throws IOException Fehlerpruefung
     * @return true, wenn Schreibvorgang erfolgreich, sonst false
     */
    // Artikel
    // String name, double preis, int bestand

    public boolean speichereArtikel(Artikel artikel) throws IOException {
        // Titel, Nummer und Verfügbarkeit schreiben
        schreibeZeile(artikel.getName());
        // schreibeZeile(Integer.valueOf(b.getNummer()).toString());
        schreibeZeile(artikel.getPreis() + "");
        schreibeZeile(artikel.getBestand() + "");
        return true;
    }
    /**
     * Methode zum Schreiben der Massengutartikeldaten in eine externe Datenquelle.
     *
     * @param artikel Massengutartikel-Objekt, das gespeichert werden soll
     * @throws IOException Fehlerpruefung
     * @return true, wenn Schreibvorgang erfolgreich, sonst false
     */
    public boolean speichereMassengutartikel(Artikel artikel) throws IOException {
        // Titel, Nummer und Verfügbarkeit schreiben
        schreibeZeile(artikel.getName());
        // schreibeZeile(Integer.valueOf(b.getNummer()).toString());
        schreibeZeile(artikel.getPreis() + "");
        schreibeZeile(artikel.getBestand() + "");
        if (artikel instanceof Massengutartikel) {
            schreibeZeile(((Massengutartikel) artikel).getPackungsGroeße() + "");
        }
        return true;
    }
    /**
     * Methode zum Einlesen der Kundendaten aus einer externen Datenquelle.
     *
     * @throws IOException Fehlerpruefung
     * @return Kunde-Objekt, wenn Einlesen erfolgreich, sonst null
     */
    public Kunden ladeKunde() throws IOException {
        String name = liesZeile();
        if (name == null) {
            return null;
        }
        String email = liesZeile();
        String passwort = liesZeile();
        String adresse = liesZeile();

        return new Kunden(name, email, passwort, adresse);
    }
    /**
     * Methode zum Schreiben der Kundendaten in eine externe Datenquelle.
     *
     * @param kunde Kunden-Objekt, das gespeichert werden soll
     * @throws IOException Fehlerpruefung
     * @return true, wenn Schreibvorgang erfolgreich, sonst false
     */
    public boolean speichereKunden(Kunden kunde) throws IOException {
        schreibeZeile(kunde.getName());
        schreibeZeile(kunde.getAdresse());
        schreibeZeile(kunde.getPasswort());
        schreibeZeile(kunde.getEmail());
        return true;
    }
    /**
     * Methode zum Einlesen der Mitarbeiterdaten aus einer externen Datenquelle.
     *
     * @throws IOException Fehlerpruefung
     * @return Mitarbeiter-Objekt, wenn Einlesen erfolgreich, sonst null
     */
    public Mitarbeiter ladeMitarbeiter() throws IOException {
        String name = liesZeile();
        if (name == null) {
            return null;
        }
        String email = liesZeile();
        String passwort = liesZeile();

        return new Mitarbeiter(name, email, passwort);
    }
    /**
     * Methode zum Schreiben der Logdaten in eine externe Datenquelle.
     *
     * @param logMsg Log-Objekt, das gespeichert werden soll
     * @throws IOException Fehlerpruefung
     * @return true, wenn Schreibvorgang erfolgreich, sonst false
     */
    public boolean speichereLog(String logMsg) throws IOException {
        schreibeZeile(logMsg + "");
        return true;
    }
    /**
     * Methode zum Einlesen der Logdaten aus einer externen Datenquelle.
     *
     * @throws IOException Fehlerpruefung
     * @return Log-Objekt, wenn Einlesen erfolgreich, sonst null
     */
    public Log ladeLog() throws IOException {
        String logMsg = liesZeile();
        if (logMsg == null) {
            return null;
        }
        return new Log(logMsg);
    }
    /**
     * Methode zum Schreiben der Mitarbeitergdaten in eine externe Datenquelle.
     *
     * @param mitarbeiter Log-Objekt, das gespeichert werden soll
     * @throws IOException Fehlerpruefung
     * @return true, wenn Schreibvorgang erfolgreich, sonst false
     */
    public boolean speichereMitarbeiter(Mitarbeiter mitarbeiter) throws IOException {
        schreibeZeile(mitarbeiter.getName());
        schreibeZeile(mitarbeiter.getEmail());
        schreibeZeile(mitarbeiter.getPasswort());
        return true;
    }

    public Ereignis ladeEreignis() throws IOException {
        // Datum einlesen
        String datumString = liesZeile();
        if (datumString != null) {


            // Artikelnummer einlesen
            String artikelNummerString = liesZeile();
            int artikelId = Integer.parseInt(artikelNummerString);

            // Anzahl einlesen
            String anzahlString = liesZeile();
            int anzahl = Integer.parseInt(anzahlString);

            // Ereignisart einlesen
            String ereignisMsg = liesZeile();

            // Personindex einlesen
            String personIndexString = liesZeile();
            int persId = Integer.parseInt(personIndexString);
            return new Ereignis(datumString, artikelId, anzahl, ereignisMsg, persId);
        }
        return null;
    }

    public void speichereEreignis(Ereignis ereignis) throws IOException {
        schreibeZeile(ereignis.getDatum() + "");
        schreibeZeile(ereignis.getArtikelId() + "");
        schreibeZeile(ereignis.getAnzahl() + "");
        schreibeZeile(ereignis.getEreignisMsg() + "");
        schreibeZeile(ereignis.getPersId() + "");
    }

    /*
     * Wenn später mal eine Kundenverwaltung ergänzt wird:
     * 
     * public Kunde ladeKunde() throws IOException { // TODO: Implementieren return
     * null; }
     * 
     * public boolean speichereKunde(Kunde k) throws IOException { // TODO:
     * Implementieren return false; }
     * 
     */

    /**
     * Methode zum Lesen der Zeile aus einer externen Datenquelle.
     *
     * @throws IOException Fehlerpruefung
     * @return Zeile, wenn Einlesen erfolgreich, sonst null
     */
    private String liesZeile() throws IOException {
        if (reader != null)
            return reader.readLine();
        else
            return "";
    }
    /**
     * Methode zum Schreiben der Zeile aus einer externen Datenquelle.
     */
    private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
    }
}
