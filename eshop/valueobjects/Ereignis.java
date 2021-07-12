package eshop.valueobjects;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Ereignis {
    private String datum;
    private int artikelId;
    private int anzahl;
    private int ereignisArt;
    private int persId;

    SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MMM-yyyy");

    public Ereignis(int artikelId, int anzahl, int ereignisArt, int persId) {
        LocalDateTime ldt = LocalDateTime.now();
        this.datum = DateTimeFormatter.ofPattern("MM-dd-yyyy", Locale.ENGLISH).format(ldt);;
        this.artikelId = artikelId;
        this.anzahl = anzahl;
        this.ereignisArt = ereignisArt;
        this.persId = persId;
    }

    public Ereignis(String datum, int artikelId, int anzahl, int ereignisArt, int persId) {
        this.datum = datum;
        this.artikelId = artikelId;
        this.anzahl = anzahl;
        this.ereignisArt = ereignisArt;
        this.persId = persId;
    }

    public String getDatum() {
        return datum;
    }

    public int getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(int artikelId) {
        this.artikelId = artikelId;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public int getEreignisArt() {
        return ereignisArt;
    }

    public void setEreignisArt(int ereignisArt) {
        this.ereignisArt = ereignisArt;
    }

    public int getPersId() {
        return persId;
    }

    public void setPersId(int persId) {
        this.persId = persId;
    }

    /**
     * Methode die die bisher angefallenen Ereignisse in einem String �bergibt/ausgibt
     */
    @Override
    public String toString() {
        return ("Datum: " + this.datum + " UserID: " + this.persId + " ArtikelId: " + this.artikelId + " | Anzahl: " + this.anzahl + " | " + " Aktion: " + getAktion(this.ereignisArt));
    }

    private String getAktion(int ereignisArt) {
        return switch (ereignisArt) {
            case 1 -> "neuer Artikel angelegt";
            case 2 -> "zum Bestand hinzugefügt";
            case 3 -> "Artikel verkauft";
            default -> null;
        };
    }

}
