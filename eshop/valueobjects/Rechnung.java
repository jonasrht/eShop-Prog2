package eshop.valueobjects;

import eshop.verwaltung.EreignisVerwaltung;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Klasse zum Verwalten der Rechnungen.
 *
 * @author Jonas, Jana, Dabina - Verwaltung der Artikel in List/Vector mit
 *         Generics - außerdem Einsatz von Interfaces (List) - Import aus der
 *         Java Bibliotheck
 */
public class Rechnung {
    // Attribute für Kunde, Datum, die gekauften Artikel, Stückzahl und
    // Preisinformation sowie den Gesamtpreis.
    private Kunden kunde;
    private List<HashMap<Artikel, Integer>> artikelListe;
    private LocalDate datum;

    // Konstruktor
    public Rechnung(Kunden kunde, List<HashMap<Artikel, Integer>> artikelListe) {
        this.kunde = kunde;
        this.artikelListe = artikelListe;
        this.datum = LocalDate.now();
    }

    /**
     * Methode zum Erstellen von Rechnungen
     */
    public List<String> rechnungErstellen() {
        // Gesamtpreis anfangs auf null setzen
        List<String> rechnung = new ArrayList<String>();
        double gesamtPreis = 0;
        // damit es sexy aussieht
        rechnung.add("+-------------------------------------------------+ ");
        rechnung.add("|                    Rechnung                     | ");
        rechnung.add("|\tName: " + kunde.getName());
        rechnung.add("|\tE-Mail: " + kunde.getEmail());
        rechnung.add("|\tRechnungsadresse: " + kunde.getAdresse());
        rechnung.add("|\tShop ID: " + kunde.getPersonID());
        rechnung.add("|\tDatum: " + datum);
        rechnung.add("|                                                 | ");
        // in der Hashmap wird durch die ganzen Artikel durchiteriert
        for (HashMap<Artikel, Integer> array : this.artikelListe) {
            // setzt dort ein keyset ab
            for (Artikel artikel : array.keySet()) {
                Integer anzahl = array.get(artikel);

                // Bestand aktualisieren
                if (artikel instanceof Massengutartikel) {
                    int aktuellerBestand = artikel.getBestand();
                    int packung = ((Massengutartikel) artikel).getPackungsGroeße();
                    int neuerBestand = aktuellerBestand - anzahl*packung;
                    artikel.setBestand(neuerBestand);
                } else {
                    int aktuellerBestand = artikel.getBestand();
                    int neuerBestand = aktuellerBestand - anzahl;
                    artikel.setBestand(neuerBestand);
                }

                EreignisVerwaltung.ereignisEinfuegen(artikel.getProduktID(), anzahl, "Artikel gekauft", this.kunde.getPersonID());

                // Preis für die Anzahl der Artikel berechnen
                double preis = artikel.getPreis() * anzahl;
                // Preis zu dem Gesamtpreis addieren
                gesamtPreis = gesamtPreis + preis;
                rechnung.add("|\tID [" + artikel.getProduktID() + "] " + " Anzahl: " + anzahl + "x " + artikel.getName()
                        + " für " + preis + "€ |");
            }
        }
        rechnung.add("+-------------------------------------------------+");
        rechnung.add("Gesamtpreis:    " + gesamtPreis + "€");
        return rechnung;
    }

    public List<HashMap<Artikel, Integer>> getArtikel() {
        return artikelListe;
    }

    @Override
    public String toString() {
        return "Rechnung:   " + "kunde: " + kunde.getName() + ", artikel  " + "Datum: " + datum;
    }
}
