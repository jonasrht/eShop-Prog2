package eshop.Client.net;

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.exceptions.BestandZuGering;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunden;
import eshop.valueobjects.Mitarbeiter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Klasse der Eshop Fassade.
 *
 * @author Jonas, Jana, Dabina
 * @implements EshopInterface
 * - Import aus der Java Bibliotheck
 */
public class EshopFassade implements EshopInterface {

    private Socket socket = null;
    private BufferedReader in;
    private PrintStream out;
    /**
     * Methode zum Verbinden des Servers mit Inet und Port.
     *
     * @param host Host
     * @param port Port des Hosts
     * @throws Exception
     */
    public EshopFassade(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream());
            System.out.println("ServerMsg:" + in.readLine()); // Begrueßung durch Server auf Konsole ausgeben
        } catch (Exception e) {
            System.err.println("Fehler ---> " + e.getMessage());
        }

        System.err.println("Verbunden mit Server " + socket.getInetAddress() + ":" + socket.getPort() + "\n");
    }
    /**
     * Methode zum Anzeigen des Entfernen der Artikel im Bestand.
     *
     * @param artikel Artikel
     * @param entfernen  die die Anzahl an Artikel entfernt
     */
    public void bestandReduzieren(Artikel artikel, int entfernen) {
        out.println("bestandReduzieren");
        printArtikel(artikel);
        out.println(entfernen);
    }
    /**
     * Methode zum Anzeigen aller Artikel im Bestand.
     *
     * @throws IOException
     * @return artikelListe
     */
    public List<Artikel> gibAlleArtikel() throws IOException {
        List<Artikel> artikelListe = new ArrayList<Artikel>();
        out.println("gibAlleAnzeigen");
        String sizeStr = in.readLine();
        int size = Integer.parseInt(sizeStr);
        for (int i = 0; i < size; i++) {
            String idStr = in.readLine();
            String name = in.readLine();
            String preisStr = in.readLine();
            String bestandStr = in.readLine();
            // TODO wenn was anderes eingegeben wird, Erinnerung, dass Zahlen eingegeben werden müssen
            int id = Integer.parseInt(idStr);
            double preis = Double.parseDouble(preisStr);
            int bestand = Integer.parseInt(bestandStr);

            Artikel artikel = new Artikel(id, name, preis, bestand);
            artikelListe.add(artikel);
        }
        return artikelListe;
    }
    /**
     * Methode zum Anzeigen des Sortierens der Artikel.
     */
    public void artikelSortieren() {
        out.println("artikelSortieren");

    }
    /**
     * Methode zum Anzeigen der Infos der Artikel.
     */
    public void printArtikel(Artikel artikel) {
        out.println(artikel.getName());
        out.println(artikel.getPreis());
        out.println(artikel.getBestand());
    }
    /**
     * Methode zum Anzeigen eines neuen Artikels im Bestand.
     *
     * @param name Artikel
     * @param preis  des Artikels
     * @param bestand wieviele von den Artikeln
     * @throws ArtikelExistiertBereitsException
     */
    public void artikelNeu(String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        out.println("artikelNeu");
        out.println(name);
        out.println(preis);
        out.println(bestand);
    }
    /**
     * Methode zum Anzeigen eines neuen Massengutartikels im Bestand.
     *
     * @param name Massengutartikel
     * @param preis  des Massengutartikels
     * @param bestand wieviele von den Massengutartikeln
     * @param packungsGroesse Packungsgröße des Massengutartikels
     * @throws ArtikelExistiertBereitsException
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
     * Methode zum Anzeigen eines Aendern der Artikel im Bestand.
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
     * Methode zum Anzeigen des Sortierens der Produkt ID.
     */
    public void produktIDSortieren() {
        out.println("produktIDSortieren");
    }
    /**
     * Methode zum Anzeigen eines LogIns der Kunden.
     *
     * @param passwort des Kunden
     * @param email des Kunden
     * @throws LoginFehlgeschlagen
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
     * Methode zum Anzeigen eines Rgistrieren neuer Kunden.
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
     * Methode zum Anzeigen eines validen Passworts.
     *
     * @param password
     * @return false
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
     * Methode zum Anzeigen eines geprueften Passworts.
     *
     * @param password
     * @return false
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
     * Methode zum Anzeigen eines LogIns der Mitarbeiter.
     *
     * @param passwort des Mitarbeiters
     * @param email des Mitarbeiters
     * @throws LoginFehlgeschlagen
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
     * Methode zum Anzeigen eines Registrierens der Mitarbeiter.
     *
     * @param name des Mitarbeiters
     * @param passwort des Mitarbeiters
     * @param email des Mitarbeiters
     */
    public void registerEmployee(String name, String email, String passwort) {
        out.println("registerEmployee");
        out.println(name);
        out.println(email);
        out.println(passwort);
    }
    /**
     * Methode zum Anzeigen des zu einloggenden Mitarbeiter.
     *
     * @param passwort des Mitarbeiters
     * @param email des Mitarbeiters
     * @return null
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
     * Methode zum Anzeigen des zu einloggenden Kunden.
     *
     * @param passwort des Kunden
     * @param email des Kunden
     * @return null
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
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }
    /**
     * Methode zum Anzeigen eines Artikels via ID.
     *
     * @param id des Artikels
     * @throws ArtikelNichtGefundenException
     * @return null
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
     * Methode zum Anzeigen des Warenkorb.
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
     * Methode zum Anzeigen der Artikel im Warenbkorb.
     *
     * @param id des Artikels
     * @param artikel des Artikels
     * @param anzahl
     * @throws BestandZuGering
     */
    public void artikelInWarenkorb(int id, Artikel artikel, int anzahl) throws BestandZuGering {
        out.println("artikelInWarenkorb");
        out.println(id);
        out.println(artikel.getProduktID());
        out.println(anzahl);
    }

    public void artikelInWarenkorbLeeren(int id) {
        out.println("artikelInWarenkorbLeeren");
        out.println(id);
    }

    public void anzahlArtikelAendern(int id, Artikel artikel, int anzahl) {
        out.println("anzahlArtikelAendern");
        out.println(id);
        out.println(artikel.getProduktID());
        out.println(anzahl);
    }

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

    public void verbindungsAbbruch() {
        out.println("q");
    }
}
