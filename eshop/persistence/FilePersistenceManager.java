package eshop.persistence;

import eshop.valueobjects.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FilePersistenceManager implements PersistenceManager {
    private BufferedReader reader = null;
    private PrintWriter writer = null;

    public void openForReading(String datei) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(datei));
    }

    public void openForWriting(String datei) throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
    }

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
     * Methode zum Einlesen der Buchdaten aus einer externen Datenquelle.
     *
     * @return Buch-Objekt, wenn Einlesen erfolgreich, false null
     */
    public Artikel ladeArtikel() throws IOException {
        // Titel einlesen
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

        // neues Buch-Objekt anlegen und zurückgeben
        return new Artikel(name, preis, bestand);
    }

    public Massengutartikel ladeMassengutartikel() throws IOException {
        // Titel einlesen
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
     * Methode zum Schreiben der Buchdaten in eine externe Datenquelle. Das
     * Verfügbarkeitsattribut wird in der Datenquelle (Datei) als "t" oder "f"
     * codiert abgelegt.
     *
     * @param artikel Buch-Objekt, das gespeichert werden soll
     * @return true, wenn Schreibvorgang erfolgreich, false sonst
     */
    // Artikel
    // String name, double preis, int bestand

    public boolean speichereArtikel(Artikel artikel) {
        // Titel, Nummer und Verfügbarkeit schreiben
        schreibeZeile(artikel.getName());
        // schreibeZeile(Integer.valueOf(b.getNummer()).toString());
        schreibeZeile(artikel.getPreis() + "");
        schreibeZeile(artikel.getBestand() + "");
        return true;
    }

    public boolean speichereMassengutartikel(Artikel artikel) {
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

    public boolean speichereKunden(Kunden kunde) {
        schreibeZeile(kunde.getName());
        schreibeZeile(kunde.getAdresse());
        schreibeZeile(kunde.getPasswort());
        schreibeZeile(kunde.getEmail());
        return true;
    }

    public Mitarbeiter ladeMitarbeiter() throws IOException {
        String name = liesZeile();
        if (name == null) {
            return null;
        }
        String email = liesZeile();
        String passwort = liesZeile();

        return new Mitarbeiter(name, email, passwort);
    }

    public boolean speichereLog(String logMsg) {
        schreibeZeile(logMsg + "");
        return true;
    }

    public Log ladeLog() throws IOException {
        String logMsg = liesZeile();
        if (logMsg == null) {
            return null;
        }
        return new Log(logMsg);
    }

    public boolean speichereMitarbeiter(Mitarbeiter mitarbeiter) {
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

    public void speichereEreignis(Ereignis ereignis) {
        schreibeZeile(ereignis.getDatum() + "");
        schreibeZeile(ereignis.getArtikelId() + "");
        schreibeZeile(ereignis.getAnzahl() + "");
        schreibeZeile(ereignis.getEreignisMsg() + "");
        schreibeZeile(ereignis.getPersId() + "");
    }

    /*
     * Private Hilfsmethoden
     */

    private String liesZeile() throws IOException {
        if (reader != null)
            return reader.readLine();
        else
            return "";
    }

    private void schreibeZeile(String daten) {
        if (writer != null)
            writer.println(daten);
    }
}
