package eshop.persistence;

import eshop.valueobjects.*;
import java.io.IOException;
/**
 * Interface Persistenz Schnittstelle.Daten in einem nichtfluechtigen Speicher abspeichern (typischerweise in einer Datenbank), sodass sie auch ueber einen Programmneustart/Rechnerneustart hinweg noch unveraendert vorhanden sind
 *
 * @author Jonas, Jana, Dabin
 * @throws IOException
 */
public interface PersistenceManager {

    public void openForReading(String datenquelle) throws IOException;

    public void openForWriting(String datenquelle) throws IOException;

    public boolean close();

    /**
     * Methode zum Einlesen der Artiekl aus einer externen Datenquelle.
     *
     * @return Artikel-Objekt, wenn Einlesen erfolgreich, false null
     */
    public Artikel ladeArtikel() throws IOException;
    public Massengutartikel ladeMassengutartikel() throws IOException;

    /**
     * Methode zum Schreiben der Artiekldaten in eine externe Datenquelle.
     *
     * @param -Objekt, das gespeichert werden soll
     * @return true, wenn Schreibvorgang erfolgreich, false sonst
     */
    Kunden ladeKunde() throws IOException;
    Mitarbeiter ladeMitarbeiter() throws IOException;
    boolean speichereArtikel(Artikel artikel) throws IOException;
    boolean speichereMassengutartikel(Artikel artikel) throws IOException;
    boolean speichereKunden(Kunden kunden) throws IOException;
    boolean speichereMitarbeiter(Mitarbeiter mitarbeiter) throws IOException;
    Log ladeLog() throws IOException;
    boolean speichereLog(String logMsg) throws IOException;

    /*
     * Wenn später mal eine Kundenverwaltung ergänzt wird:
     * 
     * public Kunde ladeKunde() throws IOException;
     * 
     * public boolean speichereKunde(Kunde k) throws IOException;
     * 
     */
}
