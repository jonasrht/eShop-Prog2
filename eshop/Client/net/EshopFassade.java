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
import java.util.List;

public class EshopFassade implements EshopInterface {

    private Socket socket = null;
    private BufferedReader in;
    private PrintStream out;

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

    public void bestandReduzieren(int id, Artikel artikel, int entfernen) {
        out.println("bestandReduzieren");
        out.println(id);
        printArtikel(artikel);
        out.println(entfernen);
    }

    public List<Artikel> gibAlleArtikel() throws IOException {
        List<Artikel> artikelListe = new ArrayList<>();
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
        List<Artikel> suchErgebnis = new ArrayList<>();
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

    public void artikelSortieren() {
        out.println("artikelSortieren");

    }

    public void printArtikel(Artikel artikel) {
        out.println(artikel.getName());
        out.println(artikel.getPreis());
        out.println(artikel.getBestand());
    }

    public void artikelNeu(int id, String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        out.println("artikelNeu");
        out.println(id);
        out.println(name);
        out.println(preis);
        out.println(bestand);
    }

    public void massenartikelNeu(String name, double preis, int bestand, int packungsGroesse)
            throws ArtikelExistiertBereitsException {
        out.println("massenartikelNeu");
        out.println(name);
        out.println(preis);
        out.println(bestand);
        out.println(packungsGroesse);
    }

    public void artikelBestandAendern(Artikel artikel, int newbestand) {
        out.println("artikelBestandAendern");
        out.println(artikel.getProduktID());
        // printArtikel(artikel);
        out.println(newbestand);
    }

    public void produktIDSortieren() {
        out.println("produktIDSortieren");
    }

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

    public void registerCustomer(String name, String email, String passwort, String adresse) {
        out.println("registerCustomer");
        out.println(name);
        out.println(email);
        out.println(passwort);
        out.println(adresse);
    }

    public boolean valPassword(String password) {
        out.println("valPassword");
        out.println(password);

        try {
            String passwordCheck = in.readLine();
            return passwordCheck.equals("true");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

    public boolean checkPass(String password) {
        out.println("checkPass");
        out.println(password);

        try {
            String passwordCheck = in.readLine();
            return passwordCheck.equals("true");
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }

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

    public void registerEmployee(String name, String email, String passwort) {
        out.println("registerEmployee");
        out.println(name);
        out.println(email);
        out.println(passwort);
    }

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

    public Artikel getArtikelViaID(int id) throws ArtikelNichtGefundenException {
        out.println("getArtikelViaID");
        out.println(id);
        Artikel artikel;

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

        return null;
    }

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rechnung;
    }

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
    @Override
    public void ereignisEinfuegen(int artikelId, int anzahl, String ereignisMsg, int persId) {
        out.println("ereignisEinfuegen");
        out.println(artikelId);
        out.println(anzahl);
        out.println(ereignisMsg);
        out.println(persId);
    }


}
