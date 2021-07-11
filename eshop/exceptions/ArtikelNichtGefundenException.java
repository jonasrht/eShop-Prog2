package eshop.exceptions;
/**
 * Klasse zum Schutz vor Weiterverarbeitung nicht existierender Artikel.
 *
 * @author Jonas, Jana, Dabin
 * @extends Exception
 * - Import aus der Java Bibliotheck
 */
public class ArtikelNichtGefundenException extends Exception{

    public ArtikelNichtGefundenException() {
        super("Artikel nicht gefunden");
    }
    
}