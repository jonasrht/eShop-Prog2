package eshop.Client.net;

import eshop.exceptions.ArtikelExistiertBereitsException;
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

    public void bestandReduzieren(Artikel artikel, int entfernen) {
        out.println("bestandReduzieren");
        printArtikel(artikel);
        out.println(entfernen);
    }

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

            int id = Integer.parseInt(idStr);
            double preis = Double.parseDouble(preisStr);
            int bestand = Integer.parseInt(bestandStr);

            Artikel artikel = new Artikel(id, name, preis, bestand);
            artikelListe.add(artikel);
        }
        return artikelListe;
    }

    public void artikelSortieren() {
        out.println("artikelSortieren");

    }

    public void printArtikel(Artikel artikel) {
        out.println(artikel.getName());
        out.println(artikel.getPreis());
        out.println(artikel.getBestand());
    }

    public void artikelNeu(String name, double preis, int bestand) throws ArtikelExistiertBereitsException {
        out.println("artikelNeu");
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
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    public Artikel getArtikelViaID(int id) {
        out.println("getArtikelViaID");
        out.println(id);

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
                return new Artikel(idP, name, preis, bestand);
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
