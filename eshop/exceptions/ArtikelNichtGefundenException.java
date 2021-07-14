package eshop.exceptions;
/**
 * Klasse zum Schutz vor Weiterverarbeitung nicht existierender Artikel.Exception
 *
 * @author Jonas, Jana, Dabin
 * @extends Exception
 * - Import aus der Java Bibliotheck
 */
public class ArtikelNichtGefundenException extends Exception{
    /**
     * Konstruktor
     */
    public ArtikelNichtGefundenException() {
        super("Artikel nicht gefunden");
    }
    
}