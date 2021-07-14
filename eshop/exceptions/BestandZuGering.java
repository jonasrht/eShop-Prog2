package eshop.exceptions;

import eshop.valueobjects.Artikel;
/**
 * Klasse zum Schutz des Kaufens von Artikeln, die nicht im Besitz sind.Exception
 *
 * @author Jonas, Jana, Dabina
 * @extends Exception
 * - Import aus der Java Bibliotheck
 */
public class BestandZuGering extends Exception{
    // Attribute
    private Artikel artikel;
    /**
     * Konstruktor
     *
     * @param artikel   Der bereits existierende Artikel
     * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
     */
    public BestandZuGering(Artikel artikel, String zusatzMsg){
        super("Bestand des Artikels: " + artikel.getName() + " ist zu gering. Bestand: " + artikel.getBestand());
        this.artikel = artikel;
    }
    public BestandZuGering(Artikel artikel){
        super("Bestand des Artikels: " + artikel.getName() + " ist zu gering. Bestand: " + artikel.getBestand());
        this.artikel = artikel;
    }

    public Artikel getArtikel() {
        return artikel;
    }

}
