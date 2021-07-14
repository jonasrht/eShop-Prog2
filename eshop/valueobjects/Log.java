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
 * Klasse zum Speichern der Kundenndaten im Asia Shop.
 *
 * @author Jonas, Jana, Dabin
 * - Verwaltung der Daten in List/Vector mit Generics
 * - außerdem Einsatz von Interfaces (List)
 * - Import aus der Java Bibliotheck
 */
public class Log {
    private LocalDate datum;
    private String logMsg;
    private static List<String> logListe = new ArrayList<String>();
    private PersistenceManager pm = new FilePersistenceManager();

    /**
     * Konstruktor
     *
     * @param logMsg Log message
     */
    public Log(String logMsg){
        this.datum = LocalDate.now();
        this.logMsg = this.datum + " " + logMsg;
        logHinzufuegen(this);
        try {
            liesDaten();
            schreibeDaten();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Methode zum Festhalten eines Loggings.
     *
     * @param log Logging Daten
     */
    public void logHinzufuegen(Log log) {
        // Eintrag wird in die Log Liste festgehalten
        logListe.add(log.getLogMsg());
    }
    /**
     * Methode zum Ausgeben eines Loggings.
     *
     * @return logEintrag
     */
    public static String logAusgeben() {//TODO Seiten Ansicht falls zu viele Einträge
        String logEintrag = "";
        for (String log : logListe) {
            logEintrag = logEintrag + log + "\n";
        }
        return logEintrag;
    }
    /**
     * Methode zum Lesen eines Loggings.
     *
     * @throws IOException Fehlerpruefung
     */
    public void liesDaten() throws IOException {
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading("Log.txt");
        Log einLog;
        do {
            //Log-Objekt einlesen
            einLog = pm.ladeLog();
            if (einLog != null) {
                // Log in Liste einfügen
                logListe.add(einLog.getLogMsg());
            }
        } while (einLog != null);  //wenn nicht, dann close
    }
    /**
     * Methode zum Notieren eines Loggings.
     *
     * @throws IOException Fehlerprüfung
     */
    public void schreibeDaten() throws IOException {
        //PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting("Log.txt");
        //Log wird im PersistenzManager gespeichert
        for (String logMsg: logListe){
            pm.speichereLog(logMsg);
        }
        //Persistenz-Schnittstelle wieder schließen
        pm.close();
    }
    /**
     * Accessor-Methoden
     */
    public String getLogMsg() {return logMsg;}

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
