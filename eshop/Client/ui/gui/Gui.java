package eshop.Client.ui.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import eshop.Client.net.EshopFassade;
import eshop.Client.ui.gui.models.ArtikelTabellenModel;
import eshop.Client.ui.gui.panels.ArtikelEinfuegenPanel;
import eshop.Client.ui.gui.panels.ArtikelTabellenPanel;
import eshop.Client.ui.gui.panels.MenuPanel;
import eshop.Client.ui.gui.panels.SuchPanel;
import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Vector;


public class Gui extends JFrame implements SuchPanel.SucheArtikelPanelListener, ArtikelEinfuegenPanel.ArtikelHinzufuegenListener, MenuPanel.MenuPanelListener {
    
    private final EshopInterface eshopInterface;
    //String name, double preis, int bestand
    private JTextField artikelNameFeld;
    private JTextField artikelPreisFeld;
    private JTextField artikelBestandFeld;
    private JTextField suchTextFeld;

    private JList artikelListe;
    private JButton switchScene;

    private JScrollPane scrollPane;
    private SuchPanel suchPanel;
    private ArtikelEinfuegenPanel artikelEinfuegenPanel;
    private MenuPanel menuPanel;

    private JTable artikelTabelle;

    private ArtikelTabellenPanel artikelPanel;

    public Gui(String titel, String host, int port){
        super(titel);
        eshopInterface = new EshopFassade(host, port);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowCloser());
        initialize();
    }

    public void initialize() {
        setLayout(new BorderLayout());

        // NORTH
        suchPanel = new SuchPanel(eshopInterface, this);


        // WEST
        artikelEinfuegenPanel = new ArtikelEinfuegenPanel(eshopInterface, this);

        // CENTER
        try {
            java.util.List<Artikel> artikel = eshopInterface.gibAlleArtikel();
            artikelPanel = new ArtikelTabellenPanel(artikel);
            scrollPane = new JScrollPane(artikelPanel);
            switchScene = new JButton();

            // Panels zusammen fassen
            add(suchPanel, BorderLayout.NORTH);
            add(artikelEinfuegenPanel, BorderLayout.WEST);
            add(scrollPane, BorderLayout.CENTER);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setSize(640, 480);
        setVisible(true);
    }

    public void initializeMitarbeiterMenu() {
        suchPanel.setVisible(true);
        artikelEinfuegenPanel.setVisible(false);
        scrollPane.setVisible(true);

        menuPanel = new MenuPanel(eshopInterface, this);
        add(menuPanel, BorderLayout.WEST);
        System.out.println("Mitarbeiter");
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
    public void switchScene() {
        initializeMitarbeiterMenu();
    }

    @Override
    public void aktikelPanelaktualisieren(java.util.List<Artikel> artikel) {
        artikelPanel.aktualisiereArtikelListe(artikel);
    }

    @Override
    public void wechselNeuerArtikel() {
        menuPanel.setVisible(false);
        add(artikelEinfuegenPanel, BorderLayout.WEST);
        artikelEinfuegenPanel.setVisible(true);
    }
}
