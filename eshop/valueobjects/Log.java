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
    private LocalDate datum;
    private String logMsg;
    private static List<String> logListe = new ArrayList<String>();
    private PersistenceManager pm = new FilePersistenceManager();


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

    public void logHinzufuegen(Log log) {
        logListe.add(log.getLogMsg());
    }

    public static String logAusgeben() {//TODO Seiten Ansicht falls zu viele Einträge
        String logEintrag = "";
        for (String log : logListe) {
            logEintrag = logEintrag + log + "\n";
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
                logListe.add(einLog.getLogMsg());
            }
        } while (einLog != null);  //wenn nicht, dann close
    }

    public void schreibeDaten() throws IOException {
        //PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting("Log.txt");
        //Mitarbeiter wird im PersistenzManager gespeichert
        for (String logMsg: logListe){
            pm.speichereLog(logMsg);
        }
        //Persistenz-Schnittstelle wieder schließen
        pm.close();
    }

    public String getLogMsg() {return logMsg;}

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
