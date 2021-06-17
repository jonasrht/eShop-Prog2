package eshop.valueobjects;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
/**
 * Klasse zum Verwalten der Rechnungen.
 *
 * @author Jonas, Jana, Dabina
 * - Verwaltung der Artikel in List/Vector mit Generics
 * - außerdem Einsatz von Interfaces (List)
 * - Import aus der Java Bibliotheck
 */
public class Rechnung {
    //Attribute für Kunde, Datum, die gekauften Artikel, Stückzahl und Preisinformation sowie den Gesamtpreis.
    private Kunden kunde;
    private List<HashMap<Artikel, Integer>> artikelListe;
    private LocalDate datum;
    //Konstruktor
    public Rechnung(Kunden kunde, List<HashMap<Artikel, Integer>> artikelListe){
        this.kunde = kunde;
        this.artikelListe = artikelListe;
        this.datum = LocalDate.now();
    }
    /**
     * Methode zum Erstellen von Rechnungen
     */
    public void rechnungErstellen() {
        //Gesamtpreis anfangs auf null setzen
        double gesamtPreis = 0;
        //damit es sexy aussieht
        System.out.println("+-------------------------------------------------+");
        System.out.println("|                    Rechnung                     |");
        System.out.println("Name: " + kunde.getName() +"\nE-Mail: " + kunde.getEmail() + "\nRechnungsadresse: " + kunde.getAdresse() + "\nShop ID: " + kunde.getPersonID() + "\nDatum: "+ datum);
        System.out.println();
        //in der Hashmap wird durch die ganzen Artikel durchiteriert
        for (HashMap<Artikel, Integer> array : this.artikelListe){
            //setzt dort ein keyset ab
            for(Artikel artikel : array.keySet()){
                Integer anzahl = array.get(artikel);

                // Bestand aktualisieren
                int aktuellerBestand = artikel.getBestand();
                int neuerBestand = aktuellerBestand - anzahl;
                artikel.setBestand(neuerBestand);

                // Log Objekt erstellen
                Log log = new Log(kunde, artikel, anzahl);

                // Preis für die Anzahl der Artikel berechnen
                double preis = artikel.getPreis() * anzahl;
                // Preis zu dem Gesamtpreis addieren
                gesamtPreis = gesamtPreis + preis;
                System.out.println("|\tID [" + artikel.getProduktID() + "] " + " Anzahl: " + anzahl + "x " + artikel.getName() + " für " + preis +"€ |");
            }
        }
        System.out.println("+-------------------------------------------------+");
        System.out.println("Gesamtpreis:    " + gesamtPreis + "€");
    }

    public List<HashMap<Artikel, Integer>> getArtikel() {
        return artikelListe;
    }

    @Override
    public String toString() {
        return "Rechnung:   " +
                "kunde: " + kunde.getName() +
                ", artikel  " +
                "Datum: " + datum;
    }
}
