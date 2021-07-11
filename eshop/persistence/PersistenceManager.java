package eshop.persistence;

import eshop.valueobjects.*;
import java.io.IOException;
/**
 * Interface Persistenz Schnittstelle.Daten (Artikel- und Kundendaten) in einem nichtfluechtigen Speicher abspeichern (typischerweise in einer Datenbank), sodass sie auch ueber einen Programmneustart/Rechnerneustart hinweg noch unveraendert vorhanden sind.
 *
 * @author Jonas, Jana, Dabin
 * - Import aus der Java Bibliotheck
 */
public interface PersistenceManager {

    public void openForReading(String datenquelle) throws IOException;

    public void openForWriting(String datenquelle) throws IOException;

    public boolean close();

    public Artikel ladeArtikel() throws IOException;
    public Massengutartikel ladeMassengutartikel() throws IOException;
    public Kunden ladeKunde() throws IOException;
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
