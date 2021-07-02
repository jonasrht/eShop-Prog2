package eshop.valueobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eshop.exceptions.BestandZuGering;
import eshop.verwaltung.*;
import eshop.valueobjects.Artikel;

public class Warenkorb {
    private List<HashMap<Artikel, Integer>> artikelImKorb;

    // Konstruktor
    public Warenkorb() {
        this.artikelImKorb = new ArrayList<>();
    }

    public void warenkorbAnzeigen() {
        for (HashMap<Artikel, Integer> array : artikelImKorb) {
            for (Artikel artikel : array.keySet()) {
                // System.out.println(artikel);
                Integer anzahl = array.get(artikel);
                double preis = artikel.getPreis() * anzahl;
                // TODO "System.out.print" nur in der CUI Klasse(?)
                System.out.print("ID [" + artikel.getProduktID() + "] " + " Anzahl: " + anzahl + " mal");
                System.out.println(" " + artikel.getName() + " für " + preis + "€");
            }
        }
    }

    public void artikelInWarenkorbLeeren() {
        this.artikelImKorb.removeAll(artikelImKorb);
    }

    /**
     *
     * @param artikel
     * @param anzahl
     * @throws BestandZuGering
     */
    public void artikelInWarenkorb(Artikel artikel, int anzahl) throws BestandZuGering {

        if (artikel != null && artikel.getBestand() >= anzahl) {
            HashMap<Artikel, Integer> hashMap = new HashMap<>(); // wird verwendet, um Datenelemente in einer großen
                                                                 // Datenmenge zu suchen bzw. aufzufinden
            for (int i = 0; i < anzahl; i++) {
                hashMap.put(artikel, anzahl);
            }
            this.artikelImKorb.add(hashMap);
        } else {
            throw new BestandZuGering(artikel, " - in artikelInWarenkorb()");
        }

        warenkorbAnzeigen();
    }

    public void anzahlArtikelAendern(Artikel artikel, int anzahl) {
        for (HashMap<Artikel, Integer> array : artikelImKorb) {
            for (Artikel artikel1 : array.keySet()) {
                Integer anz = array.get(artikel1);
                if (artikel1.equals(artikel)) {
                    array.replace(artikel1, anzahl);
                }
            }
        }
    }

    // Getter
    public List<HashMap<Artikel, Integer>> getArtikelImKorb() {
        return this.artikelImKorb;
    }

    // Wird nicht benutzt
    public Rechnung warenkorbKaufen(Kunden kunde, List<HashMap<Artikel, Integer>> artikel) {
        Rechnung rechnung = new Rechnung(kunde, artikel);
        artikelInWarenkorbLeeren();
        return rechnung;
    }

    @Override
    public String toString() {
        String output = "";
        for (HashMap<Artikel, Integer> array : artikelImKorb) {
            for (Artikel artikel : array.keySet()) {
                // System.out.println(artikel);
                Integer anzahl = array.get(artikel);
                double preis = artikel.getPreis() * anzahl;
                // TODO "System.out.print" nur in der CUI Klasse(?)
                output = output + ("ID [" + artikel.getProduktID() + "] " + " Anzahl: " + anzahl + " mal" + " "
                        + artikel.getName() + " für " + preis + "€" + " | ");
            }
        }
        return output;
    }
}
