package eshop.valueobjects;

/**
 * Klasse zur Verwaltung von Massengutartikeln vom Asia Shop.
 *
 * @author Jonas, Jana, Dabin
 */

public class Massengutartikel extends Artikel {
    // Attribute
    private int packungsGroeße;

    /**
     * Konstruktor
     *
     * @param name Produktname
     * @param preis Preis des Produktes
     * @param bestand Anzahl des Produktes
     * @param packungsGroeße Groeße der Verpackung
     */
    public Massengutartikel(String name, double preis, int bestand, int packungsGroeße){
        super(name+ " (" + packungsGroeße +" stk. )", preis, bestand*packungsGroeße);
        this.packungsGroeße = packungsGroeße;
    }

    public Massengutartikel(String name, double preis, int bestand, int packungsGroeße, String fromFile){
        super(name, preis, bestand*packungsGroeße);
        this.packungsGroeße = packungsGroeße;
    }
    /**
     * Accessor-Methoden
     */
    public int getPackungsGroeße() {
        return packungsGroeße;
    }

    public void setPackungsGroeße(int packungsGroeße) {
        this.packungsGroeße = packungsGroeße;
    }
}
