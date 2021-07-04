package eshop.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunden;
import eshop.valueobjects.Mitarbeiter;

/**
 * ClientEShopRequestProcessor
 */
public class ClientEShopRequestProcessor implements Runnable {

    private EshopInterface eShop;

    private Socket socket;
    private BufferedReader in;
    private PrintStream out;

    public ClientEShopRequestProcessor(Socket socket, EshopInterface eShop) {

        this.eShop = eShop;
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintStream(this.socket.getOutputStream());
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException e2) {
            }
            System.err.println("Ausnahme bei Bereitstellung des Streams: " + e);
            return;
        }
        System.out.println("Verbunden mit " + this.socket.getInetAddress() + ":" + this.socket.getPort());
        // this.start(); // Thread starten...
    }

    public void run() {
        String input = "";

        System.out.println("Server hat eine Verbindung"); // auf Konsole!

        // Begrüßungsnachricht an den Client senden
        out.println("Verbindung erfolgreich hergestellt!");

        do { // Beginn der Benutzerinteraktion:
             // Aktion vom Client einlesen [ggf. weitere Daten einlesen ...]
            try {
                input = in.readLine();
            } catch (Exception e) {
                System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
                System.out.println(e.getMessage());
                try {
                    socket.close();
                    } catch (Exception e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
                // continue;
            }

            // Eingabe-Menue bearbeiten:
            // die einzige Eingabe ist: "ein text..." oder "q"
            if (input == null) {
                input = "q";
            } else if (input.equals("q")) { // Client will Ende
                verbindungsAbbruch();
                out.println("#"); // Client beenden
            } else if (input.equals("bestandReduzieren")) {
                bestandReduzieren();
            } else if (input.equals("gibAlleAnzeigen")) {
                try {
                    gibAlleAnzeigen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (input.equals("artikelSortieren")) {
                artikelSortieren();
            } else if (input.equals("artikelNeu")) {
                try {
                    artikelNeu();
                } catch (ArtikelExistiertBereitsException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (input.equals("massenartikelNeu")) {
                try {
                    massenartikelNeu();
                } catch (ArtikelExistiertBereitsException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (input.equals("artikelBestandAendern")) {
                artikelBestandAendern();
            } else if (input.equals("produktIDSortieren")) {
                produktIDSortieren();
            } else if (input.equals("logInCustomer")) {
                logInCustomer();
            } else if (input.equals("registerCustomer")) {
                registerCustomer();
            } else if (input.equals("valPassword")) {
                valPassword();
            } else if (input.equals("checkPass")) {
                checkPass();
            } else if (input.equals("logInEmployee")) {
                logInEmployee();
            } else if (input.equals("registerEmployee")) {
                registerEmployee();
            } else if (input.equals("getEmployee")) {
                getEmployee();
            } else if (input.equals("getCustomer")) {
                getCustomer();
            } else if (input.equals("getArtikelViaID")) {
                getArtikelViaID();
            } else if (input.equals("artikelInWarenkorb")) {
                artikelInWarenkorb();
            } else if (input.equals("warenkorbAnzeigen")) {
                warenkorbAnzeigen();
            } else if (input.equals("artikelInWarenkorbLeeren")) {
                artikelInWarenkorbLeeren();
            } else if (input.equals("anzahlArtikelAendern")) {
                anzahlArtikelAendern();
            } else if (input.equals("rechnungErstellen")) {
                rechnungErstellen();
            }

        } while (!(input.equals("q")));
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gibAlleAnzeigen() throws IOException {
        List<Artikel> artikelListe = eShop.gibAlleArtikel();
        int size = artikelListe.size();
        // Gibt die Länge der ArrayList aus
        out.println(String.valueOf(size));
        // Gebe alle Artikel aus
        for (Artikel artikel : artikelListe) {
            out.println(String.valueOf(artikel.getProduktID()));
            out.println(artikel.getName());
            out.println(String.valueOf(artikel.getPreis()));
            out.println(String.valueOf(artikel.getBestand()));
        }
    }

    private void bestandReduzieren() {
        try {
            Artikel artikel = artikelErstellen();
            String entfernenStr = in.readLine();
            int entfernen = Integer.parseInt(entfernenStr);
            eShop.bestandReduzieren(artikel, entfernen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Artikel artikelErstellen() throws IOException {
        String name = in.readLine();
        String preisStr = in.readLine();
        double preis = Double.parseDouble(preisStr);
        String bastandStr = in.readLine();
        int bestand = Integer.parseInt(bastandStr);
        return new Artikel(name, preis, bestand);
    }

    private void artikelSortieren() {
        eShop.artikelSortieren();
    }

    private void artikelNeu() throws ArtikelExistiertBereitsException {
        try {
            Artikel a = artikelErstellen();
            eShop.artikelNeu(a.getName(), a.getPreis(), a.getBestand());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void massenartikelNeu() throws ArtikelExistiertBereitsException {
        try {
            Artikel a = artikelErstellen();
            String packungsGroesseStr = in.readLine();
            int packungsGroesse = Integer.parseInt(packungsGroesseStr);
            eShop.massenartikelNeu(a.getName(), a.getPreis(), a.getBestand(), packungsGroesse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void artikelBestandAendern() {
        try {
            int artId = Integer.parseInt(in.readLine());
            Artikel artikel = eShop.getArtikelViaID(artId);
            String newbestandStr = in.readLine();
            int newbestand = Integer.parseInt(newbestandStr);
            eShop.artikelBestandAendern(artikel, newbestand);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArtikelNichtGefundenException e) {
            e.printStackTrace();
        }
    }

    private void produktIDSortieren() {
        eShop.produktIDSortieren();
    }

    private void logInCustomer() {
        try {
            String passwort = in.readLine();
            String email = in.readLine();
            Kunden kunde = eShop.logInCustomer(passwort, email);
            // String name, String email, String passwort, String adresse, double guthaben
            if (kunde != null) {
                out.println("true");
                out.println(kunde.getName());
                out.println(kunde.getAdresse());
            } else {
                System.out.println("Login failed");
                out.println("false");
            }

            ;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void registerCustomer() {
        try {
            String name = in.readLine();
            String email = in.readLine();
            String passwort = in.readLine();
            String adresse = in.readLine();
            eShop.registerCustomer(name, email, passwort, adresse);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void valPassword() {
        try {
            String passwort = in.readLine();
            boolean check = eShop.valPassword(passwort);
            if (check) {
                out.println("true");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void checkPass() {
        try {
            String passwort = in.readLine();
            boolean check = eShop.checkPass(passwort);
            if (check) {
                out.println("true");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void logInEmployee() {
        try {
            String passwort = in.readLine();
            String email = in.readLine();
            System.out.println("P: " + passwort + " E: " + email);
            Mitarbeiter mitarbeiter = eShop.logInEmployee(passwort, email);
            // String name, String email, String passwort, String adresse, double guthaben
            if (mitarbeiter != null) {
                System.out.println("true");
                out.println("true");
                out.println(mitarbeiter.getName());
                out.println(mitarbeiter.getEmail());
                out.println(mitarbeiter.getPasswort());
            } else {
                out.println("false");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void registerEmployee() {
        try {
            String name = in.readLine();
            String email = in.readLine();
            String passwort = in.readLine();
            eShop.registerEmployee(name, email, passwort);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void getEmployee() {
        try {
            String passwort = in.readLine();
            String email = in.readLine();
            Mitarbeiter mitarbeiter = eShop.getEmployee(passwort, email);
            if (mitarbeiter != null) {
                out.println("true");
                out.println(mitarbeiter.getName());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void getCustomer() {
        try {
            String passwort = in.readLine();
            String email = in.readLine();
            Kunden kunde = eShop.getCustomer(passwort, email);
            if (kunde != null) {
                out.println("true");
                out.println(kunde.getName());
                out.println(kunde.getAdresse());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void getArtikelViaID() {
        try {
            String idStr = in.readLine();
            int id = Integer.parseInt(idStr);
            Artikel artikel = eShop.getArtikelViaID(id);
            if (artikel != null) {
                out.println("true");
                out.println(artikel.getProduktID());
                out.println(artikel.getName());
                out.println(artikel.getPreis());
                out.println(artikel.getBestand());
            }
            //out.println("false");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void artikelInWarenkorb() {
        try {
            int persIs = Integer.parseInt(in.readLine());
            int artikelID = Integer.parseInt(in.readLine());
            int anzahl = Integer.parseInt(in.readLine());

            Artikel artikel = eShop.getArtikelViaID(artikelID);

            eShop.artikelInWarenkorb(persIs, artikel, anzahl);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void warenkorbAnzeigen() {
        try {
            int persId = Integer.parseInt(in.readLine());
            List<String> warenkorb = eShop.warenkorbAnzeigen(persId);
            out.println(warenkorb.size());
            for (String string : warenkorb) {
                out.println(string);
                System.out.println("Warenkorb: " + string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // out.println("stop");
    }

    private void artikelInWarenkorbLeeren() {
        try {
            int persId = Integer.parseInt(in.readLine());
            eShop.artikelInWarenkorbLeeren(persId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void anzahlArtikelAendern() {
        try {
            int persId = Integer.parseInt(in.readLine());
            int artId = Integer.parseInt(in.readLine());
            int anzahl = Integer.parseInt(in.readLine());

            Artikel artikel = eShop.getArtikelViaID(artId);
            eShop.anzahlArtikelAendern(persId, artikel, anzahl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArtikelNichtGefundenException e) {
            e.printStackTrace();
        }
    }

    private void rechnungErstellen() {
        try {
            int persId = Integer.parseInt(in.readLine());
            List<String> rechnung = eShop.rechnungErstellen(persId);
            out.println(rechnung.size());
            for (String string : rechnung) {
                out.println(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verbindungsAbbruch() {
        eShop.verbindungsAbbruch();
    }
}