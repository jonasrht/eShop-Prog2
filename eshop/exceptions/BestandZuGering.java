package eshop.exceptions;

import eshop.valueobjects.Artikel;
/**
 * Klasse zum Schutz des Kaufens von Artikeln, die nicht im Besitz sind.
 *
 * @author Jonas, Jana, Dabina
 * @extends Exception
 * - Import aus der Java Bibliotheck
 */
public class BestandZuGering extends Exception{
    private Artikel artikel;

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
