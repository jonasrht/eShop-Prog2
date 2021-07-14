package eshop.valueobjects;

/**
 * Klasse zur Repraesentation einzenler Artikel.
 *
 * @author Jonas, Jana, Dabin
 * - Verwaltung der Artikel in List/Vector mit Generics
 * - außerdem Einsatz von Interfaces (List)
 * - Import aus der Java Bibliotheck
 * -implementiert Comparable<Artikel>, damit die Artikel der Liste verglichen werden kann
 */
public class Artikel implements Comparable<Artikel> {
    // Attribute definieren
    private int produktID = 0;
    private static int num;
    private String name;
    private double preis;
    private int bestand;

    /**
     * Konstruktor
     *
     * @param name Artikelname
     * @param preis vom Artikel
     * @param bestand wie viele Artikel
     */
    public Artikel(String name, double preis, int bestand) {
        this.produktID = num++;
        this.name = name;
        this.preis = preis;
        this.bestand = bestand;
    }
    /**
     * Konstruktor
     *
     * @param produktID ID des Produktes
     * @param name Artikelname
     * @param preis vom Artikel
     * @param bestand wie viele Artikel
     */
    public Artikel(int produktID, String name, double preis, int bestand) {
        this.produktID = produktID;
        this.name = name;
        this.preis = preis;
        this.bestand = bestand;
    }
    /**
     * Accessor-Methoden
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setBestand(int bestand) {
        this.bestand = bestand;
    }

    public int getBestand() {
        return this.bestand;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public double getPreis() {
        return this.preis;
    }

    // public void setProduktID(int produktID){
    // this.produktID = produktID;
    // }

    public int getProduktID() {
        return this.produktID;
    }

    /**
     * Standard-Methode wird überschrieben.
     * Methode wird immer automatisch aufgerufen, wenn ein Artikel-Objekt als String
     * benutzt wird (z.B. in println(Artikel);)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ("ID: " + produktID + " / Name: " + name + " / Preis: " + preis + "/ Bestand: " + bestand + "\n");
    }

    // Methode gibt es shon, wird nur überschrieben
    @Override
    public int compareTo(Artikel artikel) {
        return this.name.compareTo(artikel.getName());
    }
}
