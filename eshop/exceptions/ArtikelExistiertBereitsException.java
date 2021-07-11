package eshop.exceptions;

import eshop.valueobjects.Artikel;
/**
 * Klasse zum Schutz von doppelten Eintraegen von bereits existierenden Artikeln.Exception
 *
 * @author Jonas, Jana, Dabin
 * @extends Exception
 * - Import aus der Java Bibliotheck
 *
 */
public class ArtikelExistiertBereitsException extends Exception{
    //Attribut
    private Artikel artikel;
    /**
     * Konstruktor
     *
     * @param artikel   Der bereits existierende Artikel
     * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
     */
    public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
        super("Der Artikel " + artikel.getName() + " mit der Nummer " + artikel.getProduktID() + " existiert bereits"
                + zusatzMsg);
        this.artikel = artikel;
    }

    public Artikel getArtikel() {
        return artikel;
    }
}
