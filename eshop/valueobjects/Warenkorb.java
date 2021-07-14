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

    public List<String> warenkorbAnzeigen() {
        List<String> warenkorb = new ArrayList<String>();
        for (HashMap<Artikel, Integer> array : artikelImKorb) {
            for (Artikel artikel : array.keySet()) {
                // System.out.println(artikel);
                Integer anzahl = array.get(artikel);
                double preis = artikel.getPreis() * anzahl;
                // TODO "System.out.print" nur in der CUI Klasse(?)
                warenkorb.add("ID [" + artikel.getProduktID() + "] " + " Anzahl: " + anzahl + " mal" + " "
                        + artikel.getName() + " für " + preis + "€");
            }
        }
        return warenkorb;
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
        System.out.println("Warenkorb 45: Artikel " + artikel.getName() + " a: " + anzahl);
        if (artikel != null && artikel.getBestand() >= anzahl) {
            HashMap<Artikel, Integer> hashMap = new HashMap<>(); // wird verwendet, um Datenelemente in einer großen
                                                                 // Datenmenge zu suchen bzw. aufzufinden
            if (artikel instanceof Massengutartikel) {
                System.out.println("Artikel ist ein Massengutartikel");
                int packung = ((Massengutartikel) artikel).getPackungsGroeße();
                if (artikel.getBestand() >= anzahl*packung) {
                    for (int i = 0; i < anzahl; i++) {
                        hashMap.put(artikel, anzahl);
                    }
                } else {
                    throw new BestandZuGering(artikel, "da der Artikel eine Packungsgröße von " + packung + " hat.");
                }
            } else {
                for (int i = 0; i < anzahl; i++) {
                    hashMap.put(artikel, anzahl);
                }
            }
            this.artikelImKorb.add(hashMap);
        } else {
            System.out.println("Warenkorb 66: fehler");
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


    public List<HashMap<Artikel, Integer>> getArtikelImKorb() {
        return this.artikelImKorb;
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
