package eshop.Client.ui.gui;

import javax.swing.*;
import eshop.Client.net.EshopFassade;
import eshop.Client.ui.gui.panels.*;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunden;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Gui extends JFrame implements SuchPanel.SucheArtikelPanelListener, ArtikelEinfuegenPanel.ArtikelHinzufuegenListener, MenuPanel.MenuPanelListener, ArtikelBestandPanel.ArtikelBestandListener, NeuerMitarbeiterPanel.NeuerMitarbeiterListener, KundenMenuPanel.KundenMenuPanelListener, ArtikelZumWarenkorbPanel.ArtikelZumWarenkorbListener, LoginPanel.LoginListener, WarenkorbPanel.WarenkorbListener, KassePanel.KasseListener {
    
    private final EshopInterface eshopInterface;

    private JScrollPane scrollPane;
    private SuchPanel suchPanel;
    private LoginPanel loginPanel;
    private ArtikelEinfuegenPanel artikelEinfuegenPanel;
    private ArtikelBestandPanel artikelBestandPanel;
    private ArtikelZumWarenkorbPanel artikelZumWarenkorbPanel;
    private WarenkorbPanel warenkorbPanel;
    private KassePanel kassePanel;
    private NeuerMitarbeiterPanel neuerMitarbeiterPanel;
    private KundenMenuPanel kundenMenuPanel;
    private MenuPanel menuPanel;

    private ArtikelTabellenPanel artikelPanel;

    public Gui(String titel, String host, int port){
        super(titel);
        eshopInterface = new EshopFassade(host, port);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowCloser());
        initialize();
    }

    // Login
    public void initialize() {

        setLayout(new BorderLayout());
        loginPanel = new LoginPanel(eshopInterface, this);

        add(loginPanel, BorderLayout.NORTH);

        setSize(640, 480);
        setVisible(true);
    }

    // Mitarbeitermenü
    public void initializeMitarbeiterMenu() {
        suchPanel.setVisible(true);
        artikelEinfuegenPanel.setVisible(false);
        scrollPane.setVisible(true);

        menuPanel = new MenuPanel(eshopInterface, this);
        add(menuPanel, BorderLayout.WEST);
        System.out.println("Mitarbeiter");
    }

    // Kundenmenü
    public void initializeKundenMenu(Kunden kunde) {
        try {
            suchPanel = new SuchPanel(eshopInterface, this);

            List<Artikel> artikel = eshopInterface.gibAlleArtikel();
            artikelPanel = new ArtikelTabellenPanel(artikel);
            scrollPane = new JScrollPane(artikelPanel);

            suchPanel.setVisible(true);
            scrollPane.setVisible(true);

            kundenMenuPanel = new KundenMenuPanel(eshopInterface, kunde, this);
            add(suchPanel, BorderLayout.NORTH);
            add(kundenMenuPanel, BorderLayout.WEST);
            add(scrollPane, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String host = "localhost";

        Gui gui = new Gui("Asia Shop", host, 6789);
    }

    @Override
    public void beiSuchErgebnisArtikel(List<Artikel> artikelListe) {
        artikelPanel.aktualisiereArtikelListe(artikelListe);
    }

    @Override
    public void aktikelPanelaktualisieren(java.util.List<Artikel> artikel) {
        artikelPanel.aktualisiereArtikelListe(artikel);
    }

    @Override
    public void zurueckArtikelHinzu() {
        artikelEinfuegenPanel.setVisible(false);
        menuPanel.setVisible(true);
        add(menuPanel, BorderLayout.WEST);
    }

    @Override
    public void wechselNeuerMitarbeiter() {
        menuPanel.setVisible(false);
        neuerMitarbeiterPanel = new NeuerMitarbeiterPanel(eshopInterface, this);
        add(neuerMitarbeiterPanel, BorderLayout.WEST);
    }

    @Override
    public void wechselNeuerArtikel() {
        menuPanel.setVisible(false);
        add(artikelEinfuegenPanel, BorderLayout.WEST);
        artikelEinfuegenPanel.setVisible(true);
    }

    @Override
    public void wechselBestand() {
        menuPanel.setVisible(false);
        artikelBestandPanel = new ArtikelBestandPanel(eshopInterface, this);
        add(artikelBestandPanel, BorderLayout.WEST);
    }

    @Override
    public void wechselMenu() {
        artikelBestandPanel.setVisible(false);
        menuPanel.setVisible(true);
        add(menuPanel, BorderLayout.WEST);
    }

    @Override
    public void beiBestandGeaendert(List<Artikel> artikel) {
        artikelPanel.aktualisiereArtikelListe(artikel);
    }

    @Override
    public void zurueckNeuerMitarbeiter() {
        neuerMitarbeiterPanel.setVisible(false);
        menuPanel.setVisible(true);
        add(menuPanel, BorderLayout.WEST);
    }

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void errorBox(String errorMessage)
    {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void wechselWarenkorbHinzu(Kunden kunde) {
        kundenMenuPanel.setVisible(false);
        artikelZumWarenkorbPanel = new ArtikelZumWarenkorbPanel(eshopInterface, kunde, this);
        artikelZumWarenkorbPanel.setVisible(true);
        add(artikelZumWarenkorbPanel, BorderLayout.WEST);
    }

    @Override
    public void wechselWarenkorb(Kunden kunde) {
        kundenMenuPanel.setVisible(false);
        warenkorbPanel = new WarenkorbPanel(eshopInterface, kunde, this);
        scrollPane.setVisible(false);

        add(warenkorbPanel, BorderLayout.WEST);
    }

    @Override
    public void wechselZurKasse(Kunden kunde) {
        List<String> warenkorbCheck = eshopInterface.warenkorbAnzeigen(kunde.getPersonID());
        if (!warenkorbCheck.isEmpty()) {
            kundenMenuPanel.setVisible(false);
            kassePanel = new KassePanel(eshopInterface, kunde, this);
            scrollPane.setVisible(false);
            add(kassePanel, BorderLayout.WEST);
        } else {
            Gui.errorBox("Sie haben keine Artikel im Warenkorb");
        }

    }

    @Override
    public void wechselLogout() {
        eshopInterface.verbindungsAbbruch();
        allesAusblenden();
        initialize();
    }

    @Override
    public void beiArtikelHinzugefügt() {
        // TODO
    }

    @Override
    public void artikelWarenZurueck() {
        // Zurück
        artikelZumWarenkorbPanel.setVisible(false);
        kundenMenuPanel.setVisible(true);
        add(kundenMenuPanel, BorderLayout.WEST);
    }

    @Override
    public void loginErfolgreich(Kunden kunde) {
        loginPanel.setVisible(false);
        initializeKundenMenu(kunde);
    }

    @Override
    public void warenkorbZurueck(Kunden kunde) {
        allesAusblenden();
        initializeKundenMenu(kunde);
    }

    @Override
    public void warenkorbZurKasse(Kunden kunde) {
        List<String> warenkorbCheck = eshopInterface.warenkorbAnzeigen(kunde.getPersonID());
        if (!warenkorbCheck.isEmpty()) {
            warenkorbPanel.setVisible(false);
            kassePanel = new KassePanel(eshopInterface, kunde, this);
            add(kassePanel, BorderLayout.WEST);
        } else {
            Gui.errorBox("Sie haben keine Artikel im Warenkorb");
        }
    }

    @Override
    public void zurueckKasse(Kunden kunde) {
        allesAusblenden();
        initializeKundenMenu(kunde);
    }

    public void allesAusblenden() {
        if (scrollPane != null) {
            scrollPane.setVisible(false);
        }
        if (suchPanel != null) {
            suchPanel.setVisible(false);
        }
        if (loginPanel != null) {
            loginPanel.setVisible(false);
        }
        if (artikelEinfuegenPanel != null) {
            artikelPanel.setVisible(false);
        }
        if (artikelBestandPanel != null) {
            artikelBestandPanel.setVisible(false);
        }
        if (artikelZumWarenkorbPanel != null) {
            artikelZumWarenkorbPanel.setVisible(false);
        }
        if (warenkorbPanel != null) {
            warenkorbPanel.setVisible(false);
        }
        if (kassePanel != null) {
            kassePanel.setVisible(false);
        }
        if (neuerMitarbeiterPanel != null) {
            neuerMitarbeiterPanel.setVisible(false);
        }
        if (kundenMenuPanel != null) {
            kundenMenuPanel.setVisible(false);
        }
        if (menuPanel != null) {
            menuPanel.setVisible(false);
        }
    }
}
