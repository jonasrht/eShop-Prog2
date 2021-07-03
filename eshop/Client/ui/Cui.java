package eshop.Client.ui;

import eshop.Client.net.EshopFassade;

/**
* Klasse für eine sehr einfache Benutzungsschnittstelle für den Asiashp.
* Die Benutzungsschnittstelle basiert auf Ein- und Ausgaben auf der Kommandozeile,
* daher der Name CUI (Command line User Interface).
*
* @author Jonas, Jana, Dabina
* - Import aus der Java Bibliotheck
*/

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.exceptions.BestandZuGering;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.verwaltung.*;
import eshop.valueobjects.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner; //Import aus der Java Bib, damit das Skript Eingaben lesen kann

import static eshop.valueobjects.Log.logAusgeben;

public class Cui {
    Scanner eingabe = new Scanner(System.in);
    // private KundenVerwaltung kundenVerwaltung;
    // private ArtikelVerwaltung artikelVerwaltung;
    // private MitarbeiterVerwaltung mitarbeiterVerwaltung;

    public static final int DEFAULT_PORT = 6789;

    private EshopInterface eshopInterface;

    private BufferedReader in;
    private PrintStream out;

    // Konstruktor
    public Cui(String host, int port) {
        eshopInterface = new EshopFassade(host, port);
        // this.kundenVerwaltung = new KundenVerwaltung();
        // this.artikelVerwaltung = new ArtikelVerwaltung();
        // this.mitarbeiterVerwaltung = new MitarbeiterVerwaltung();
    }

    // Methode
    public void welcomeMenu() {
        boolean istAktiv = true;
        do {
            System.out.println("Guten Tag! Willkommen zu unserem Asia Shop.Bitte wählen Sie");
            System.out.println("1 Login");
            System.out.println("2 Registrieren");
            System.out.println("3 Mitarbeiter Login");
            System.out.println("4 Shop verlassen");
            System.out.print("Eingabe >");
            String input = eingabe.nextLine();

            switch (input) {
                case "1":
                    kundeLogin();
                    break;
                case "2":
                    register();
                    kundeLogin();
                    break;
                case "3":
                    mitarbeiterLogin();
                    break;
                case "4":
                    // try {
                    // artikelVerwaltung.schreibeDaten();
                    // kundenVerwaltung.schreibeDaten();
                    // mitarbeiterVerwaltung.schreibeDaten();
                    // } catch (IOException e) {
                    // e.printStackTrace();
                    // }
                    System.out.println("Bis zum nächsten mal");
                    eshopInterface.verbindungsAbbruch();
                    istAktiv = false;
                    break;
                default:
                    System.out.println("Bitte wählen Sie eine Zahl von 1 bis 4 aus");
                    break;
            }
        } while (istAktiv);
    }

    public void kundenMenu(Kunden kunde) {
        boolean menu = true;
        do {
            System.out.println("Guten Morgen, " + kunde.getName() + "! Was wollen se machen!?!??!?");
            System.out.println("\n1 Artikel anzeigen");
            System.out.println("2 Warenkorb anzeigen");
            System.out.println("3 Zur Kasse");
            System.out.println("4 Guthaben");
            System.out.println("5 Log out");
            System.out.print("Eingabe >");
            String input = eingabe.nextLine();

            switch (input) {
                case "1":
                    artikelMenuKunde(kunde);
                    break;
                case "2":
                    warenkorbMenuKunde(kunde);
                    break;
                case "3":
                    // TODO "System.out.print" nur in der CUI Klasse
                    Rechnung rechnung1 = new Rechnung(kunde, kunde.getWarenkorb().getArtikelImKorb());
                    rechnung1.rechnungErstellen();
                    kunde.getWarenkorb().artikelInWarenkorbLeeren();
                    break;
                case "4":
                    guthabenMenuKunde(kunde);
                    break;
                case "5":
                    menu = false;
                    break;
                default:
                    System.out.println("Bitte wählen Sie eine Zahl von 1 bis 5 aus");
                    break;
            }
        } while (menu);
    }

    public void mitarbeiterMenu(Mitarbeiter mitarbeiter) {
        boolean menu = true;
        do {
            System.out.println("Guten Morgen, " + mitarbeiter.getName() + "! Was wollen se machen!?!??!?");
            System.out.println("\n1 Artikel anzeigen");
            System.out.println("2 Kaufverlauf anzeigen");
            System.out.println("3 Neue Mitarbeiter registrieren ");
            System.out.println("4 Daten sichern");
            System.out.println("5 Log out");
            System.out.print("Eingabe >");
            String input = eingabe.nextLine();

            switch (input) {
                case "1":
                    artikelMenuMitarbeiter(mitarbeiter);
                    break;
                case "2":

                    break;
                case "3":
                    registerMitarbeiter();
                    break;
                case "4":
                    // try {
                    // artikelVerwaltung.schreibeDaten();
                    // kundenVerwaltung.schreibeDaten();
                    // mitarbeiterVerwaltung.schreibeDaten();
                    // } catch (IOException e) {
                    // e.printStackTrace();
                    // }
                    break;
                case "5":
                    menu = false;
                    break;
            }
        } while (menu);
    }

    public void artikelMenuMitarbeiter(Mitarbeiter mitarbeiter) {
        boolean menu = true;
        boolean change = true;
        do {
            try {
                System.out.println(eshopInterface.gibAlleArtikel());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("\n1 Artikel nach Bezeichnung sortieren");
            System.out.println("2 Artikel nach Nummer sortieren");
            System.out.println("3 Artikel Bestand ändern");
            System.out.println("4 Neue Artikel anlegen");
            System.out.println("5 Neue Massengutartikel anlegen");
            System.out.println("6 Log ausgeben");
            System.out.println("\n7 Zurück");
            System.out.print("Eingabe >");
            String input = eingabe.nextLine();

            switch (input) {
                case "1":
                    eshopInterface.artikelSortieren();
                    break;
                case "2":
                    eshopInterface.produktIDSortieren();
                    break;
                case "3":
                    System.out.println("Für welches Artikel möchstest du den Bestand ändern?");
                    System.out.println("Bitte Artikel ID eingeben.");
                    System.out.print("Eingabe >");
                    String inputArtikelStr = eingabe.nextLine();
                    int inputArtikel = Integer.parseInt(inputArtikelStr);
                    Artikel artikel2 = eshopInterface.getArtikelViaID(inputArtikel);
                    System.out.println("Wie hoch soll der Bestand des Artikels sein?");
                    System.out.print("Eingabe >");
                    int inputArtikelNewBst = eingabe.nextInt();
                    eshopInterface.artikelBestandAendern(artikel2, inputArtikelNewBst);
                     break;
                case "4":
                    System.out.println("Welches Artikel sollte neu angelegt werden?");
                    System.out.print("Eingabe >");
                    String inputArtikelName = eingabe.nextLine();
                    String inputName = inputArtikelName;
                    System.out.println("Wie hoch soll der Bestand des Artikels sein?");
                    System.out.print("Eingabe >");
                    String inputArtikelBst = eingabe.nextLine();
                    int inputBst = Integer.parseInt(inputArtikelBst);
                    System.out.println("Wieviel beträgt jeweiliges Artikel?");
                    System.out.println("Bitte Preis angeben (z.B. 9.99)");
                    System.out.print("Eingabe >");
                    String inputArtikelPrs = eingabe.nextLine();
                    double inputPrs = Double.parseDouble(inputArtikelPrs);
                    try {
                        eshopInterface.artikelNeu(inputName, inputPrs, inputBst);
                    } catch (ArtikelExistiertBereitsException e) {
                        System.out.println("Fehler bei der erstellung des Artikels");
                        e.printStackTrace();
                    }

                    break;
                case "5":
                    System.out.println("Welcher Massengutartikel soll neu angelegt werden?");
                    System.out.print("Name >");
                    String massArtikelName = eingabe.nextLine();
                    System.out.println("Wie hoch soll der Bestand des Artikels sein?");
                    System.out.print("Eingabe >");
                    String inputMasArtikelBst = eingabe.nextLine();
                    int massenArtBst = Integer.parseInt(inputMasArtikelBst);
                    System.out.println("Wieviel beträgt jeweiliges Artikel?");
                    System.out.println("Bitte Preis angeben (z.B. 9.99)");
                    System.out.print("Eingabe >");
                    String inputMassenArtikelPrs = eingabe.nextLine();
                    double massenArtPreis = Double.parseDouble(inputMassenArtikelPrs);
                    System.out.println("Welche Packungsgröße soll der Artikel haben?");
                    System.out.print("Eingabe >");
                    String inputPackungsgr = eingabe.nextLine();
                    int massenArtPackungsgr = Integer.parseInt(inputPackungsgr);
                    try {
                        eshopInterface.massenartikelNeu(massArtikelName, massenArtPreis, massenArtBst,
                                massenArtPackungsgr);
                    } catch (ArtikelExistiertBereitsException e) {
                        System.out.println("Fehler bei der erstellung des Artikels");
                        e.printStackTrace();
                    }

                    break;
                case "6":
                    System.out.println(logAusgeben());
                    break;
                case "7":
                    menu = false;
                    break;
                default:
                    System.out.println("Bitte wählen Sie eine Zahl von 1 bis 5 aus");
                    break;
            }
        } while (menu);
    }

    public void artikelMenuKunde(Kunden kunde) {
        boolean menu = true;
        do {
            try {
                System.out.println(eshopInterface.gibAlleArtikel());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("\n1 Artikel in den Warenkorb hinzufügen");
            System.out.println("2 Artikel nach Bezeichnung sortieren");
            System.out.println("3 Artikel nach Nummer sortieren");
            System.out.println("4 Warenkorb anzeigen");
            System.out.println("5 Zur Kasse");
            System.out.println("\n6 Zurück");
            System.out.print("Eingabe >");
            String input = eingabe.nextLine();

            switch (input) {
                case "1":
                    System.out.println("Welchen Artikel willst du kaufen?");
                    System.out.print("Eingabe >");
                    String inputArtikelStr = eingabe.nextLine();
                    int inputArtikel = Integer.parseInt(inputArtikelStr);
                    Artikel artikel = eshopInterface.getArtikelViaID(inputArtikel);
                    System.out.println("Suche Artikel mit ID: " + inputArtikel);
                    System.out.println("Artikel: " + artikel.getName() + " gefunden! ID " + artikel.getProduktID());
                    System.out.println("Wie viele Artikel willst du kaufen?");
                    System.out.print("Eingabe >");
                    String inputAnzahlStr = eingabe.nextLine();
                    int inputAnzahl = Integer.parseInt(inputAnzahlStr);
                    try {
                        // kunde.getWarenkorb().artikelInWarenkorb(artikel, inputAnzahl);
                        eshopInterface.artikelInWarenkorb(kunde.getPersonID(), artikel, inputAnzahl);
                    } catch (BestandZuGering e) {
                        e.printStackTrace();
                    }

                    break;
                case "2":
                    eshopInterface.artikelSortieren();
                    break;
                case "3":
                    eshopInterface.produktIDSortieren();
                    break;
                case "4":
                    warenkorbMenuKunde(kunde);
                    break;
                case "5":
                    // TODO "System.out.print" nur in der CUI Klasse
                    // Rechnung rechnung1 = new Rechnung(kunde,
                    // kunde.getWarenkorb().getArtikelImKorb());
                    // rechnung1.rechnungErstellen();
                    eshopInterface.rechnungErstellen(kunde.getPersonID());
                    // kunde.getWarenkorb().artikelInWarenkorbLeeren();
                    break;
                case "6":
                    menu = false;
                    break;
                default:
                    System.out.println("Bitte geben Sie eine Zahl von 1 bis 5 ein:");
                    break;
            }
        } while (menu);
    }

    public void guthabenMenuKunde(Kunden kunde) {
        // boolean menu = true;
        // do {
        // System.out.println("Guthaben beträgt momentan: " +
        // eshopInterface.guthabenAnzeigen(kunde));
        // System.out.println("1 Guthaben aufladen");
        // System.out.println("2 Zurück");
        // System.out.print("Eingabe >");
        // String input = eingabe.nextLine();

        // switch (input) {
        // case "1":
        // System.out.println("Wieveiel möchten Sie aufladen? (in EUR €)");
        // System.out.print("Eingabe >");
        // String inputGuthabenDb = eingabe.nextLine();
        // double inputGuthaben = Integer.parseInt(inputGuthabenDb);
        // eshopInterface.guthabenAufladen(kunde, inputGuthaben);
        // break;
        // case "2":
        // menu = false;
        // break;
        // default:
        // System.out.println("Bitte geben Sie eine Zahl von 1 bis 2 ein:");
        // break;
        // }
        // } while (menu);
    }

    public void warenkorbMenuKunde(Kunden kunde) {
        boolean menu = true;
        do {
            System.out.println("Ihr Warenkorb enthält folgende Artikel: ");
            // TODO: "System.out.print" nur in der CUI Klasse
            // kunde.getWarenkorb().warenkorbAnzeigen();
            List<String> warenkorb = eshopInterface.warenkorbAnzeigen(kunde.getPersonID());
            for (String string : warenkorb) {
                System.out.println(string);
            }
            System.out.println("1 Zur Kasse");
            System.out.println("2 Warenkorb leeren");
            System.out.println("3 Anzahl der Artikel im Warenkorb ändern");
            System.out.println("4 Zurück");
            System.out.print("Eingabe >");
            String input = eingabe.nextLine();

            switch (input) {
                case "1":
                    // TODO "System.out.print" nur in der CUI Klasse
                    // Rechnung rechnung1 = new Rechnung(kunde,
                    // kunde.getWarenkorb().getArtikelImKorb());
                    // rechnung1.rechnungErstellen();
                    List<String> rechnung = eshopInterface.rechnungErstellen(kunde.getPersonID());
                    for (String string : rechnung) {
                        System.out.println(string);
                    }
                    // kunde.getWarenkorb().artikelInWarenkorbLeeren();
                    break;
                case "2":
                    eshopInterface.artikelInWarenkorbLeeren(kunde.getPersonID());
                    System.out.println("[!] Warenkorb wurde erfolgreich geleert.");
                    break;
                case "3":
                    System.out.println("Von welchem Artikel möchtest du die Anzahl ändern?");
                    System.out.print("Eingabe >");
                    String inputArtikelStr = eingabe.nextLine();
                    int inputArtikel = Integer.parseInt(inputArtikelStr);
                    Artikel artikel = eshopInterface.getArtikelViaID(inputArtikel);
                    System.out.println("Neue Anzahl");
                    System.out.print("Eingabe >");
                    String neueAnzahlStr = eingabe.nextLine();
                    int neueAnzahl = Integer.parseInt(neueAnzahlStr);
                    kunde.getWarenkorb().anzahlArtikelAendern(artikel, neueAnzahl);
                    eshopInterface.anzahlArtikelAendern(kunde.getPersonID(), artikel, neueAnzahl);
                    break;
                case "4":
                    menu = false;
                    break;
                default:
                    System.out.println("Bitte geben Sie eine Zahl von 1 bis 4 ein:");
                    break;
            }
        } while (menu);

    }

    public void kundeLogin() {
        System.out.print("Email >");
        String emailInput = eingabe.nextLine();
        System.out.print("Passwort >");
        String passwortInput = eingabe.nextLine();
        try {
            Kunden kunde = eshopInterface.logInCustomer(passwortInput, emailInput);
            kundenMenu(kunde);
        } catch (LoginFehlgeschlagen e) {
            e.printStackTrace();
        }
        // if (kundenVerwaltung.logInCustomer(passwortInput, emailInput)) {
        // Kunden kunde = eshopInterface.getCustomer(passwortInput, emailInput);
        // kundenMenu(kunde);
        // }
    }

    public void register() {
        System.out.print("Name >");
        String nameInputRegister = eingabe.nextLine();
        System.out.print("E-mail >");
        String emailInputRegister = eingabe.nextLine();
        String passwortVal = passwortValidationFirst();
        String passwortInputRegister = passwortValidationLast(passwortVal);
        System.out.print("Adresse >");
        String adresseInputRegister = eingabe.nextLine();
        eshopInterface.registerCustomer(nameInputRegister, emailInputRegister, passwortInputRegister,
                adresseInputRegister);
        System.out.println("[!] Registrierung erfolgreich! Bitte loggen sie sich nochmal ein.");
    }

    public String passwortValidationFirst() {
        String passwortInput;
        boolean confirm = true;
        do { // Passwort 1)
            System.out.println("Passwort:");
            System.out.println("- Mindestens 8 Zeichen");
            System.out.println("- Mindestens 1 Großbuchstaben");
            System.out.print("- Mindestens 1 Zahl >");
            passwortInput = eingabe.nextLine();
            if (eshopInterface.valPassword(passwortInput)) {
                String passwortVal = passwortValidationLast(passwortInput);
                return passwortVal;
            } else {
                System.out.println("Nochmal versuchen bitte");
            }
        } while (confirm);
        return null;
    }

    public String passwortValidationLast(String passwortVal) {
        boolean confirm = true;
        do { // Passwort 2)
            System.out.println("Passwort bestätigen");
            System.out.print("[1] drücken um Passwort erneut einzurichten >");
            String passwortConfirmRegister = eingabe.nextLine();
            if (passwortVal.equals(passwortConfirmRegister)) {
                confirm = false;
                passwortVal = passwortConfirmRegister;
                return passwortVal;
            } else if (passwortConfirmRegister.equals("1")) {
                confirm = false;
                passwortValidationFirst();
            } else {
                System.out.println("Nochmal versuchen biddeee");
            }
        } while (confirm);
        return null;
    }

    public void registerMitarbeiter() {
        System.out.print("Name >");
        String nameInputRegister1 = eingabe.nextLine();
        System.out.print("E-mail >");
        String emailInputRegister1 = eingabe.nextLine();
        String passwortVal = passwortValidationFirst();
        String passwortInputRegister1 = passwortValidationLast(passwortVal);
        System.out.println("[!] Registrierung erfolgreich! Bitte loggen sie sich nochmal ein.");
        eshopInterface.registerEmployee(nameInputRegister1, emailInputRegister1, passwortInputRegister1);
    }

    public void mitarbeiterLogin() {
        System.out.print("Email >");
        String emailInput = eingabe.nextLine();
        System.out.print("Passwort >");
        String passwortInput = eingabe.nextLine();
        try {
            Mitarbeiter mitarbeiter = eshopInterface.logInEmployee(passwortInput, emailInput);
            mitarbeiterMenu(mitarbeiter);
        } catch (LoginFehlgeschlagen e) {
            e.printStackTrace();
        }
    }

    // main Methode
    public static void main(String[] args) {
        String host = "localhost";
        Cui cui = new Cui(host, DEFAULT_PORT);
        cui.welcomeMenu();
    }

}
