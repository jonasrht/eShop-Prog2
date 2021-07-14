package eshop.valueobjects;
<<<<<<< HEAD
//        Bei Artikeln wird nun zwischen Massengut- und Einzelartikeln unterschieden. Massengutartikel haben
//        eine individuelle Packungsgröße, so dass Ein- und Auslagerungen nur in Vielfachen dieser Packungsgröße
//        vorgenommen werden können. Einzelartikel entsprechen den bereits vom letzten Aufgabenblatt
//        bekannten Artikeln.

//        Beispiel aus dem täglichen Leben: bei Dosenbier handelt es sich um einen Massengutartikel, der nur in
//        6er-Einheiten abgegeben wird. Der Lagerbestand soll allerdings wie auch bei Einzelartikeln geführt
//        werden, d.h. er umfasst die tatsächliche Anzahl verfügbarer Dosen. Der Lagerbestand könnte also z.B. 48
//        Dosen (Vielfaches der Packungsgröße!) betragen. Ein Kunde könnte z.B. 6 oder 18 Dosen (entspricht 1
//        oder 3 Six-Packs) kaufen, d.h. der Kunde legt einzelne Dosen (immer Vielfaches der Packungsgröße!) in
//        den Warenkorb und keine Sixpacks. Implementieren Sie die Klasse Massengutartikel nachträglich.
//        Nutzen Sie Vererbung!
/**
 * Klasse zur Verwaltung von Massengutartikeln vom Asia Shop.
 *
 * @author Jonas, Jana, Dabin
 */
=======

>>>>>>> GUI
public class Massengutartikel extends Artikel {
    private int packungsGroeße;

    public Massengutartikel(String name, double preis, int bestand, int packungsGroeße){
        super(name+ " (" + packungsGroeße +" stk. )", preis, bestand*packungsGroeße);
        this.packungsGroeße = packungsGroeße;
    }

    public Massengutartikel(String name, double preis, int bestand, int packungsGroeße, String fromFile){
        super(name, preis, bestand*packungsGroeße);
        this.packungsGroeße = packungsGroeße;
    }

    public int getPackungsGroeße() {
        return packungsGroeße;
    }

    public void setPackungsGroeße(int packungsGroeße) {
        this.packungsGroeße = packungsGroeße;
    }
}
