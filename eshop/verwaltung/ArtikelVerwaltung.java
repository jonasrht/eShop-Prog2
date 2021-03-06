package eshop.verwaltung;

//Imoport aus der Java Bibliothek
import java.io.IOException; //...Fehler oder ein nicht geplantes Ereignis, das während der Ausführung eines Programms vorkommt und dessen normalen Ablauf stört
import java.util.ArrayList; //...daynamische Datenstruktur (Items können hinzugefügt und entfernt werden)
import java.util.Collections; //...um alle möglichen Listen zu sortieren
import java.util.Comparator; //...um Artikel (nach Attribute) zu finden, wenn man nach ihnen sucht
import java.util.List; // ...schreibt Verhalten vor, die alle konkreten Listen im-plementieren müssen

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.persistence.FilePersistenceManager;
import eshop.persistence.PersistenceManager;
import eshop.valueobjects.*;

/**
 * Klasse zur Verwaltung von asiatischen Lebensmitteln.
 *
 * @author Jonas, Jana, Dabina - Verwaltung der Artikel in List/Vector mit
 *         Generics - außerdem Einsatz von Interfaces (List) - Import aus der
 *         Java Bibliotheck
 */

public class ArtikelVerwaltung {
    // Counter für die Methode Artikelsortieren() und produktIDSortieren() setzen
    private int counter = 2;
    // Persistenz_Schnittstelle des Artieklbestands in einem Array anlegen
    private List<Artikel> artikelListe = new ArrayList<Artikel>();
    // erstellt Persistenz-Manager
    private PersistenceManager pm = new FilePersistenceManager();
    // interface gibt //impementierung vor, was es enthalten soll

    /**
     * Konstruktor
     */
    public ArtikelVerwaltung() {
        // Fehlerbehandlung
        try {
            // führt Methode aus
            liesDaten();
        }
        // wenn Fehler, dann Fehlerausgabe
        catch (IOException e) {
            e.printStackTrace();
        }
        // artikelListe.add(new Artikel("Sojasoße", 1.99, 10));
        // artikelListe.add(new Artikel("Yum Yum", 0.39, 20));
        // artikelListe.add(new Artikel("Sushireis", 4.99, 10));
        // artikelListe.add(new Artikel("Tofu", 1.49, 20));
        // artikelListe.add(new Artikel("Koriander", 0.59, 10));
        // artikelListe.add(new Artikel("Litschisaft", 1.39, 20));
        // artikelListe.add(new Artikel("Orangensaft", 1.39, 0));
    }

    /**
     * Methode zum Einlesen von Artikeln aus einer Datei.
     *
     * @throws IOException Feherpruefung
     */
    public void liesDaten() throws IOException {
        // PersistenzManager für Lesevorgänge öffnen
        pm.openForReading("Artikel.txt");
        Artikel einArtikel;
        System.out.println("ArtikelVerwaltung erstellt");
        do {
            // Artikel-Objekt einlesen
            einArtikel = pm.ladeArtikel();
            if (einArtikel != null) {
                // Artiekl in Liste einfügen
                artikelListe.add(einArtikel);
            }
            // wenn nicht, dann close
        } while (einArtikel != null);

        pm.openForReading("Massengutartikel.txt");
        Massengutartikel einMasArtikel;

        do {
            // Artikel-Objekt einlesen
            einMasArtikel = pm.ladeMassengutartikel();
            if (einMasArtikel != null) {
                // Artiekl in Liste einfügen
                artikelListe.add(einMasArtikel);
            }
            // wenn nicht, dann close
        } while (einMasArtikel != null);
    }

    /**
     * Methode zum Schreiben der Artikel in eine Datei.
     *
     * @throws IOException Feherpruefung
     */
    public void schreibeDaten() throws IOException {
        // PersistenzManager für Schreibvorgänge öffnen
        pm.openForWriting("Artikel.txt");
        // sucht und speichert
        for (Artikel artikel : artikelListe) {
            if (!(artikel instanceof Massengutartikel)) {
                pm.speichereArtikel(artikel);
            }
        }
        // Persistenz-Schnittstelle wieder schließen
        pm.close();

        pm.openForWriting("Massengutartikel.txt");
        // sucht und speichert
        for (Artikel artikel : artikelListe) {
            if (artikel instanceof Massengutartikel) {
                pm.speichereMassengutartikel(artikel);
            }
        }
        // Persistenz-Schnittstelle wieder schließen
        pm.close();

    }

    /**
     * Methode zum Suchen der Artikel via ID in der Artikelliste.
     *
     * @param id  die die Artikel individualisiert
     * @return artikel or null
     */
    public Artikel getArtikelViaID(int id) throws ArtikelNichtGefundenException{
        for (Artikel artikel : artikelListe) {
            if (id == artikel.getProduktID()) {
                return artikel;
            }
        }
        throw new ArtikelNichtGefundenException();
        //return null;
    }

    /**
     * Methode zum Entfernen der Artikel im Bestand.
     *
     * @param  artikel
     * @param  entfernen, die die Anzahl an Artikel entfernt
     */
    public void bestandReduzieren(Artikel artikel, int entfernen) {
        // int wird definiert
        int currentbestand;
        // Artikel wird im Bestand-Array gesucht
        for (Artikel artikel1 : artikelListe) {
            // wenn der Name Identisch ist
            if (artikel1.equals(artikel)) {
                // jetzige Bestand wird dem currentbestand übergeben
                currentbestand = artikel.getBestand();
                // Bestand wird nach Anzahl der Eingabe entfernt und als current Bestand
                // aktualisiert
                artikel.setBestand(currentbestand - entfernen);
            }
        }
    }


    /**
     * Methode, welche mittels einer foreach Schleife durch
     * die Artikel Liste iteriert und falls ein Artikel mit
     * dem übergebenen Namen übereinstimmt, in eine "suchErgbenis"
     * Liste schreibt und diese zurückgibt.
     *
     * @param suchName Artikelname, nach welchem gesucht werden soll
     * @return Das Suchergebnis
     */
    public List<Artikel> sucheNachArtikel(String suchName) {
        List<Artikel> suchErgebnis = new ArrayList<Artikel>();
        for (Artikel artikel: artikelListe) {
            if (suchName.equals(artikel.getName())) {
                suchErgebnis.add(artikel);
            }
        }
        return suchErgebnis;
    }

    /**
     * Methode zum Anzeigen der Artikel im Bestand.
     */
    public void artikelAnzeigen() {
        System.out.println("+------------------------------------------------+"); // damit es sexy aussieht
        for (Artikel artikel : artikelListe) {
            System.out.printf("%-5s %-12s %-12s %s\n", artikel.getProduktID() + ".", artikel.getName(),
                    artikel.getPreis() + "€", "Auf Lager: " + artikel.getBestand()); // Bestand wird in dem Format
                                                                                     // ausgegeben
        }
        System.out.println("+------------------------------------------------+");

    }
    /**
     * Methode zum Ausgeben aller Artikel.
     */
    public List<Artikel> gibAlleArtikel() {
        return artikelListe;
    }

    /**
     * Methode zum alphabetischen Sortieren der Artikel im Bestand.
     *
     */
    public void artikelSortieren() {
        // wenn der counter restlos durch zwei teilbar ist...
        if (counter % 2 == 0) {
            // wird die Liste alphabetisch sortiert
            Collections.sort(artikelListe);
            // counter auf eins, somit wird die Liste beim zweiten Abruf alphabetisch
            // umgedreht
            counter--;
            // wenn der counter auf eins ist, wird die Liste im Abruf alphabetisch umgedreht
        } else {
            Collections.sort(artikelListe, Collections.reverseOrder());
            counter++;
        }
    }

    /**
     * Methode zum Hinzufügen neuer Artikel im Bestand.
     *
     * @param name    des Artikels, wonach alphabetisch sortiert wird
     * @param preis   des Artikels
     * @param bestand wie viele
     * @throws ArtikelExistiertBereitsException Schutz vor doppelten Artikel
     */
    public void artikelNeu(int id, String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        Artikel artikel = new Artikel(name, preis, bestand);
        // wird durch die Artikel durchiteriert
        for (Artikel artiekln : artikelListe) {
            if (artiekln.getName().equals(name)) {
                // zur Sicherheit
                throw new ArtikelExistiertBereitsException(artikel, " - in 'artikelNeu()'");
            }
        }
        // Artikel hinzufügen
        artikelListe.add(artikel);
    }

    /**
     * Methode zum Hinzufuegen neuer Massenartikel im Bestand.
     *
     * @param name    des Artikels, wonach alphabetisch sortiert wird
     * @param preis   des Artikels
     * @param bestand wie viele
     * @param packungsGroesse Verpackungsgroesse
     * @throws ArtikelExistiertBereitsException Schutz vor doppelten Artikel
     */
    public void massenartikelNeu(String name, double preis, int bestand, int packungsGroesse)
            throws ArtikelExistiertBereitsException {
        Artikel artikel = new Massengutartikel(name, preis, bestand, packungsGroesse);
        // wird durch die Artikel durchiteriert
        for (Artikel artiekln : artikelListe) {
            if (artiekln.getName().equals(name)) {
                // zur Sicherheit
                throw new ArtikelExistiertBereitsException(artikel, " - in 'massenartikelNeu()'");
            }
        }
        // Artikel hinzufügen
        artikelListe.add(artikel);
    }

    /**
     * Methode zum Aendern des Bestandes der Artikel.
     *
     * @param artikel       des Artikels, wonach alphabetisch sortiert wird
     * @param newbestand    neue Bestand
     */
    public void artikelBestandAendern(Artikel artikel, int newbestand) {
        artikel.setBestand(newbestand);
        // Artikel wird im Array gesucht
       // for (Artikel artikel1 : artikelListe) {
       //     if (artikel1.equals(artikel)) {
                // neuer Bestand wird angelegt
        //        artikel.setBestand(newbestand);
        //    }
        //}
    }

    /**
     * Methode zum Sortieren der Artikel nach ihrer ProduktID.
     */
    public void produktIDSortieren() {
        // wenn der counter restlos durch zwei teilbar ist...
        if (counter % 2 == 0) {
            // wird nach der Id sortiert
            artikelListe.sort(Comparator.comparing(Artikel::getProduktID));
            // counter auf eins, somit wird die Liste beim zweiten Abruf numerisch umgedreht
            counter--;
        } else {
            // wenn der counter auf eins ist, wird die Liste im Abruf numerisch umgedreht
            artikelListe.sort(Comparator.comparing(Artikel::getProduktID).reversed());
            counter++;
        }
    }

}
