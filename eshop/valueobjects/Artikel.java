package eshop.valueobjects;

/**
 * Klasse zum passivem Erstellen von Artiekeln.
 *
 * @author Jonas, Jana, Dabina
 * @implements Java Interface für das Vergleichen - Verwaltung der Artikel in
 *             List/Vector mit Generics - außerdem Einsatz von Interfaces (List)
 *             - Import aus der Java Bibliotheck
 */
public class Artikel implements Comparable<Artikel> {
    // Attribute definieren
    private int produktID = 0;
    private static int num;
    private String name;
    private double preis;
    private int bestand;

    // KOnstruktor
    public Artikel(String name, double preis, int bestand) {
        this.produktID = num++;
        this.name = name;
        this.preis = preis;
        this.bestand = bestand;
    }

    public Artikel(int produktID, String name, double preis, int bestand) {
        this.produktID = produktID;
        this.name = name;
        this.preis = preis;
        this.bestand = bestand;
    }

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

    // Methode gibt es shon, wird nur überschrieben
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
