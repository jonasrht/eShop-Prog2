package eshop.valueobjects;

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
