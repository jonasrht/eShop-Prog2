package eshop.valueobjects;
//Imoport aus der Java Bibliothek
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Klasse zur Buchhaltung vom Asia Shop.
 *
 * @author Jonas, Jana, Dabina
 * - Verwaltung der Artikel in List/Vector mit Generics
 * - außerdem Einsatz von Interfaces (List)
 * - Import aus der Java Bibliotheck
 */
public class Log {
    private Artikel artikel;
    private int anzahlArtikel;
    private String zusatzMsg;
    private Kunden kunde;
    private Mitarbeiter mitarbeiter;
    private LocalDate datum;
    private List<Log> logListe = new ArrayList<Log>();
    private PersistenceManager pm = new FilePersistenceManager();

    public Log(Kunden kunde, Artikel artikel, String zusatzMsg){
        this.kunde = kunde;
        this.artikel = artikel;
        this.anzahlArtikel = anzahlArtikel;
        this.datum = LocalDate.now();
        this.zusatzMsg = zusatzMsg;
    }

    public Log(Mitarbeiter mitarbeiter, Artikel artikel, int anzahlArtikel, String zusatzMsg){
        this.mitarbeiter = mitarbeiter;
        this.artikel = artikel;
        this.anzahlArtikel = anzahlArtikel;
        this.datum = LocalDate.now();
        this.zusatzMsg = zusatzMsg;
    }


    public void logHinzufuegen(Log log) {
        logListe.add(log);
    }

    public String logAusgeben() {//TODO Seiten Ansicht falls zu viele Einträge
        String logEintrag = "";
        for (Log log : this.logListe) {
            logEintrag = logEintrag + log.toString() + "\n";
        }
        return logEintrag;
    }

    public void liesDaten() throws IOException {  //Methode mit Fehlerprüfung
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading("Log.txt");
        Log einLog;
        do {
            //Artikel-Objekt einlesen
            einLog = pm.ladeLog();
            if (einLog != null) {
                // Artiekl in Liste einfügen
                logListe.add(log);
            }
        } while (einLog != null);  //wenn nicht, dann close
    }

    public void schreibeDaten() throws IOException {
        //PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting("Log.txt");
        //Mitarbeiter wird im PersistenzManager gespeichert
        for (Log log: logListe){
            pm.speichereMitarbeiter(mitarbeiter);
        }
        //Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

//
//    public void LogOutputCustomer() {
//        //int sum = 0;
//        //sum = artikel.getPreis() * artikel.getPreis
//        //
//        //System.out.print("-" + datum + " | " + kunde + " > " + " [" + artikel.getProduktID() +  "] " + artikel.getName() + ": " + artikel.getAnzahl() + "x | " +  );
//        //System.out.println(" " + artikel.getName() + " für " + preis +"€");
//
//    }

    public Artikel getArtikel() {
        return artikel;
    }

    public int getAnzahlArtikel() {
        return anzahlArtikel;
    }

    public Kunden getKunde() {
        return kunde;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setArtikel(Artikel artikel) {
        this.artikel = artikel;
    }

    public void setAnzahlArtikel(int anzahlArtikel) {
        this.anzahlArtikel = anzahlArtikel;
    }

    public void setKunde(Kunden kunde) {
        this.kunde = kunde;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

//    @Override

    @Override
    public String toString() {
        return "Log{" +
                "artikel=" + artikel.getName() +
                ", anzahlArtikel=" + anzahlArtikel +
                ", zusatzMsg='" + zusatzMsg + '\'' +
                ", kunde=" + kunde.getName() +
                ", mitarbeiter=" + mitarbeiter.getName() +
                ", datum=" + datum +
                '}';
    }
}
