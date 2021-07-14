package eshop.Client.net;

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.exceptions.BestandZuGering;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Ereignis;
import eshop.valueobjects.Kunden;
import eshop.valueobjects.Mitarbeiter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Klasse des Clients.Client kennt Server und baut bei Bedarf Verbindungen auf und stellt Anfragen.Der Client hat Computerprogramme, die ablaufen sollen und der Server besitzt die Informationen und Ressourcen, welche die Programme benoetigen, um zu funktionieren.Der Client moechte einen Prozess durchfuehren und benoetigt dafuer Informationen und Ressourcen, die auf einem mit ihm verbundenen Server liegen.Also sendet er eine Anfrage (Request) an den Server und fordert diese an.Der Server verarbeitet alle Anfragen, die bei ihm eingehen, und stellt die Elemente bereit (Response), die von ihm gefordert werden.Dabei ist es dem Server ueberlassen, in welcher Reihenfolge er die Anfragen abarbeitet.Server lauuft immer im Hintergrund und wartet auf Anfragen.Beide kommunizieren mit Sockets: repraesentiert Endpunkte einer Netzwerkverbindung.Datenaustausch zwischen Client und Server erfolgt ueber Streams: bietet einen sequenzierten und einzigartigen Fluss fehlerfreier Daten ohne Datensatzgrenzen mit gut definierten Mechanismen zum Erstellen und Zerstoeren von Verbindungen und Melden von Fehlern.Ein Stream-Socket uebertraegt Daten zuverlaessig, geordnet und mit Out-of-Band-Faehigkeiten.Setzt Methodenaufrufe der UI-Klassen in Netzwerkprotokoll um.
 *
 * @author Jonas, Jana, Dabina
 * @implements EshopInterface
 * - Import aus der Java Bibliotheck
 */
public class EshopFassade implements EshopInterface {
    // Attribute
    private Socket socket = null;
    private BufferedReader in;
    private PrintStream out;

    /**
     * Konstruktor
     *
     * @param host Host
     * @param port Port des Hosts
     */
    public EshopFassade(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            System.out.println("ServerMsg:" + in.readLine()); // Begr��ung durch Server auf Konsole ausgeben
        } catch (Exception e) {
            System.err.println("Fehler ---> " + e.getMessage());
        }

        System.err.println("Verbunden mit Server " + socket.getInetAddress() + ":" + socket.getPort() + "\n");
    }


    /**
     * Methode zum Anfragen des Entfernen der Artikel im Bestand.
     *
     * @param artikel Artikel
     * @param entfernen  die die Anzahl an Artikel entfernt
     */
    public void bestandReduzieren(int id, Artikel artikel, int entfernen) {
        out.println("bestandReduzieren");
        out.println(id);
        printArtikel(artikel);
        out.println(entfernen);
    }

    /**
     * Methode zum Anfragen aller Artikel im Bestand.
     *
     * @throws IOException Fehlerpruefung
     * @return artikelListe
     */
    public List<Artikel> gibAlleArtikel() throws IOException {
        List<Artikel> artikelListe = new ArrayList<Artikel>();
        out.println("gibAlleAnzeigen");
        String sizeStr = in.readLine();
        System.out.println(sizeStr);
        int size = Integer.parseInt(sizeStr);
        for (int i = 0; i < size; i++) {
            String idStr = in.readLine();
            String name = in.readLine();
            String preisStr = in.readLine();
            String bestandStr = in.readLine();

            int id = Integer.parseInt(idStr);
            double preis = Double.parseDouble(preisStr);
            int bestand = Integer.parseInt(bestandStr);

            Artikel artikel = new Artikel(id, name, preis, bestand);
            artikelListe.add(artikel);
        }
        return artikelListe;
    }

    public List<Artikel> sucheNachArtikel(String suchName) {
        List<Artikel> suchErgebnis = new ArrayList<Artikel>();
        out.println("sucheNachArtikel");
        out.println(suchName);
        try {
            int size = Integer.parseInt(in.readLine());

            for (int i = 0; i < size; i++) {
                int id = Integer.parseInt(in.readLine());
                String name = in.readLine();
                double preis = Double.parseDouble(in.readLine());
                int bestand = Integer.parseInt(in.readLine());

                suchErgebnis.add(new Artikel(id, name, preis, bestand));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suchErgebnis;
    }

    /**
     * Methode zum Anfragen des Sortierens der Artikel.
     */
    public void artikelSortieren() {
        out.println("artikelSortieren");

    }

    /**
     * Methode zum Anfragen der Infos der Artikel.
     */
    public void printArtikel(Artikel artikel) {
        out.println(artikel.getName());
        out.println(artikel.getPreis());
        out.println(artikel.getBestand());
    }


    /**
     * Methode zum Anfragen eines neuen Artikels im Bestand.
     *
     * @param name Artikel
     * @param preis  des Artikels
     * @param bestand wieviele von den Artikeln
     * @throws ArtikelExistiertBereitsException Schutz vor doppelten Einträgen
     */
    public void artikelNeu(int id, String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        out.println("artikelNeu");
        out.println(id);
        out.println(name);
        out.println(preis);
        out.println(bestand);
    }

    /**
     * Methode zum Anfragen eines neuen Massengutartikels im Bestand.
     *
     * @param name Massengutartikel
     * @param preis  des Massengutartikels
     * @param bestand wieviele von den Massengutartikeln
     * @param packungsGroesse Packungsgröße des Massengutartikels
     * @throws ArtikelExistiertBereitsException Schutz vor doppelten Einträgen
     */
    public void massenartikelNeu(String name, double preis, int bestand, int packungsGroesse)
            throws ArtikelExistiertBereitsException {
        out.println("massenartikelNeu");
        out.println(name);
        out.println(preis);
        out.println(bestand);
        out.println(packungsGroesse);
    }

    /**
     * Methode zum Anfragen eines Aendern der Artikel im Bestand.
     *
     * @param artikel Artikel
     * @param newbestand die neue Anzahl der Artikel
     */
    public void artikelBestandAendern(Artikel artikel, int newbestand) {
        out.println("artikelBestandAendern");
        out.println(artikel.getProduktID());
        // printArtikel(artikel);
        out.println(newbestand);
    }

    /**
     * Methode zum Anfragen des Sortierens der Produkt ID.
     */
    public void produktIDSortieren() {
        out.println("produktIDSortieren");
    }
    /**
     * Methode zum Anfragen eines LogIns der Kunden.
     *
     * @param passwort des Kunden
     * @param email des Kunden
     * @throws LoginFehlgeschlagen Fehler beim Login
     */
    public Kunden logInCustomer(String passwort, String email) throws LoginFehlgeschlagen {
        out.println("logInCustomer");
        out.println(passwort);
        out.println(email);

        String loginCheck = "";
        try {
            loginCheck = in.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (loginCheck.equals("true")) {
            String name = "";
            String adresse = "";
            try {
                name = in.readLine();
                adresse = in.readLine();
            } catch (IOException e) {

                e.printStackTrace();
            }

            return new Kunden(name, email, passwort, adresse);
        } else {
            throw new LoginFehlgeschlagen();
        }
    }

    /**
     * Methode zum Anfragen eines Rgistrieren neuer Kunden.
     *
     * @param name des Kunden
     * @param email des Kunden
     * @param passwort des Kunden
     * @param adresse des Kunden
     */
    public void registerCustomer(String name, String email, String passwort, String adresse) {
        out.println("registerCustomer");
        out.println(name);
        out.println(email);
        out.println(passwort);
        out.println(adresse);
    }

    /**
     * Methode zum Anfragen eines validen Passworts.
     *
     * @param password des zu festzulegenden
     * @return true or false
     */
    public boolean valPassword(String password) {
        out.println("valPassword");
        out.println(password);

        try {
            String passwordCheck = in.readLine();
            if (passwordCheck.equals("true")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    /**
     * Methode zum Anfragen eines geprueften Passworts.
     *
     * @param password des zu festzulegenden
     * @return true or false
     */
    public boolean checkPass(String password) {
        out.println("checkPass");
        out.println(password);

        try {
            String passwordCheck = in.readLine();
            if (passwordCheck.equals("true")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    /**
     * Methode zum Anfragen eines LogIns der Mitarbeiter.
     *
     * @param passwort des Mitarbeiters
     * @param email des Mitarbeiters
     * @throws LoginFehlgeschlagen Fehler beim Login
     * @return mitarbeiter
     */
    public Mitarbeiter logInEmployee(String passwort, String email) throws LoginFehlgeschlagen {
        out.println("logInEmployee");
        out.println(passwort);
        out.println(email);
        Mitarbeiter mitarbeiter = null;

        try {
            String loginCheck = in.readLine();
            if (loginCheck.equals("true")) {
                String name = in.readLine();
                String emailSer = in.readLine();
                String password = in.readLine();
                return new Mitarbeiter(name, emailSer, password);
            } else {
                throw new LoginFehlgeschlagen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mitarbeiter;
    }

    /**
     * Methode zum Anfragen eines Registrierens der Mitarbeiter.
     *
     * @param name des Mitarbeiters
     * @param email des Mitarbeiters
     * @param passwort des Mitarbeiters
     */
    public void registerEmployee(String name, String email, String passwort) {
        out.println("registerEmployee");
        out.println(name);
        out.println(email);
        out.println(passwort);
    }

    /**
     * Methode zum Anfragen des zu einloggenden Mitarbeiter.
     *
     * @param passwort des Mitarbeiters
     * @param email des Mitarbeiters
     * @return Mitarbeiter or null
     */
    public Mitarbeiter getEmployee(String passwort, String email) {
        out.println("getEmployee");
        out.println(passwort);
        out.println(email);

        try {
            String check = in.readLine();
            if (check.equals("true")) {
                String name = in.readLine();
                return new Mitarbeiter(name, email, passwort);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    /**
     * Methode zum Anfragen des zu einloggenden Kunden.
     *
     * @param passwort des Kunden
     * @param email des Kunden
     * @return Kunden or null
     */
    public Kunden getCustomer(String passwort, String email) {
        out.println("getCustomer");
        out.println(passwort);
        out.println(email);

        try {
            String check = in.readLine();
            if (check.equals("true")) {
                String name = in.readLine();
                String adresse = in.readLine();
                return new Kunden(name, email, passwort, adresse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Methode zum Anfragen eines Artikels via ID.
     *
     * @param id des Artikels
     * @throws ArtikelNichtGefundenException Artikel gibt es nicht
     * @return artikel or null
     */
    public Artikel getArtikelViaID(int id) throws ArtikelNichtGefundenException {
        out.println("getArtikelViaID");
        out.println(id);
        Artikel artikel = null;

        try {
            String check = in.readLine();
            if (check.equals("true")) {
                String idStr = in.readLine();
                String name = in.readLine();
                String preisStr = in.readLine();
                String bestandStr = in.readLine();

                double preis = Double.parseDouble(preisStr);
                int bestand = Integer.parseInt(bestandStr);
                int idP = Integer.parseInt(idStr);
                artikel = new Artikel(idP, name, preis, bestand);
                return artikel;
                //return new Artikel(idP, name, preis, bestand);
            } else {
                System.out.println("EshopFassade 282: Artikel nicht gefunden");
                throw new ArtikelNichtGefundenException();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (artikel == null) {
            throw new ArtikelNichtGefundenException();
        }
        return null;
    }

    /**
     * Methode zum Anfragen des Warenkorb.
     *
     * @param id des Artikels
     * @return warenkorb
     */
    public List<String> warenkorbAnzeigen(int id) {
        List<String> warenkorb = new ArrayList<String>();
        out.println("warenkorbAnzeigen");
        out.println(id);
        try {
            int size = Integer.parseInt(in.readLine());

            for (int i = 0; i < size; i++) {
                String warenkorbStr = in.readLine();
                warenkorb.add(warenkorbStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return warenkorb;
    }


    /**
     * Methode zum Anfragen der Artikel im Warenbkorb.
     *
     * @param id des Artikels
     * @param artikel Name des Artikels
     * @param anzahl der Artikel
     * @throws BestandZuGering Artikel nicht in Besitz
     */
    public void artikelInWarenkorb(int id, Artikel artikel, int anzahl) throws BestandZuGering, ArtikelNichtGefundenException {
        out.println("artikelInWarenkorb");
        out.println(id);
        out.println(artikel.getProduktID());
        out.println(anzahl);
        try {
            String check = in.readLine();
            System.out.println("EshopFassade 317: " + check);
            if (check.equals("nicht gefunden")) {
                throw new ArtikelNichtGefundenException();
            }
            if (check.equals("bestand zu gering")) {
                throw new BestandZuGering(artikel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Methode zum Anfragen des Leerens der Artikel im Warenbkorb.
     *
     * @param id des Artikels
     */
    public void artikelInWarenkorbLeeren(int id) {
        out.println("artikelInWarenkorbLeeren");
        out.println(id);
    }
    /**
     * Methode zum Anfragen des Aendern der Artikelanzahl im Warenbkorb.
     *
     * @param id des Artikels
     * @param artikel Name des Artikels
     * @param anzahl der Artikel
     */
    public void anzahlArtikelAendern(int id, Artikel artikel, int anzahl) {
        out.println("anzahlArtikelAendern");
        out.println(id);
        out.println(artikel.getProduktID());
        out.println(anzahl);
    }
    /**
     * Methode zum Anfragen der Rechnung.
     *
     * @param persId des Kunden
     * @return rechnung
     */
    public List<String> rechnungErstellen(int persId) {
        List<String> rechnung = new ArrayList<String>();
        out.println("rechnungErstellen");
        out.println(persId);
        try {
            int size = Integer.parseInt(in.readLine());

            for (int i = 0; i < size; i++) {
                String rechnungStr = in.readLine();
                rechnung.add(rechnungStr);
            }
            // String input;
            // while (!((input = in.readLine()).equals("stop"))) {
            // if (input.equals("start")) {
            // String rechnungStr = in.readLine();
            // rechnung.add(rechnungStr);
            // }
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rechnung;
    }
    /**
     * Methode zum Abbrechen der Verbindung.
     */
    public void verbindungsAbbruch() {
        out.println("q");
    }

    @Override
    public List<String> getEreignisList() {
        List<String> ereignisListStr = new ArrayList<String>();
        out.println("getEreignisList");
        try {
            int size = Integer.parseInt(in.readLine());
            for (int i = 0; i < size; i++) {
                String ereignisStr = in.readLine();
                ereignisListStr.add(ereignisStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ereignisListStr;
    }

}
