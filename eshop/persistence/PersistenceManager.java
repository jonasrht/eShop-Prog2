package eshop.persistence;

import eshop.valueobjects.*;
import java.io.IOException;

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
     * @param Artikel-Objekt, das gespeichert werden soll
     * @return true, wenn Schreibvorgang erfolgreich, false sonst
     */
    public Kunden ladeKunde() throws IOException;
    public Mitarbeiter ladeMitarbeiter() throws IOException;
    boolean speichereArtikel(Artikel artikel) throws IOException;
    boolean speichereMassengutartikel(Artikel artikel) throws IOException;
    boolean speichereKunden(Kunden kunden) throws IOException;
    boolean speichereMitarbeiter(Mitarbeiter mitarbeiter) throws IOException;



    /*
     * Wenn später mal eine Kundenverwaltung ergänzt wird:
     * 
     * public Kunde ladeKunde() throws IOException;
     * 
     * public boolean speichereKunde(Kunde k) throws IOException;
     * 
     */
}
