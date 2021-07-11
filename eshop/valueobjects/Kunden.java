package eshop.valueobjects;
/**
 * Klasse zur Repraesentation einzelner Kunden.
 *
 * @author Jonas, Jana, Dabin
 * @extends Person
 */
public class Kunden extends Person {
    //ergänzende Attribute
    private String adresse;
    private double guthaben;
    protected Warenkorb warenkorb;

    //Konstruktoren
    //Kunde mit Guthaben
    public Kunden(String name, String email, String passwort, String adresse, double guthaben){
        super(name, email, passwort);
        this.warenkorb = new Warenkorb();
        this.adresse = adresse;
        this.guthaben = guthaben;
    }
    //Kunde ohne Guthaben
    public Kunden(String name, String email, String passwort, String adresse){
        super(name, email, passwort);
        this.warenkorb = new Warenkorb();
        this.adresse = adresse;
    }
    // Methoden zum Setzen und Lesen der Kunden-Eigenschaften
    public void setAdresse(String adresse){
        this.adresse = adresse;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public void setGuthaben(double guthaben){ this.guthaben = guthaben; }

    public double getGuthaben(){
        return this.guthaben;
    }

    public Warenkorb getWarenkorb() {return this.warenkorb; }

}
