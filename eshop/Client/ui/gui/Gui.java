package eshop.Client.ui.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import eshop.Client.net.EshopFassade;
import eshop.Client.ui.gui.models.ArtikelTabellenModel;
import eshop.Client.ui.gui.panels.*;
import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunden;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Gui extends JFrame implements SuchPanel.SucheArtikelPanelListener, ArtikelEinfuegenPanel.ArtikelHinzufuegenListener, MenuPanel.MenuPanelListener, ArtikelBestandPanel.ArtikelBestandListener, NeuerMitarbeiterPanel.NeuerMitarbeiterListener, KundenMenuPanel.KundenMenuPanelListener, ArtikelZumWarenkorbPanel.ArtikelZumWarenkorbListener, LoginPanel.LoginListener {
    
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
    private ArtikelBestandPanel artikelBestandPanel;
    private ArtikelZumWarenkorbPanel artikelZumWarenkorbPanel;
    private NeuerMitarbeiterPanel neuerMitarbeiterPanel;
    private KundenMenuPanel kundenMenuPanel;
    private MenuPanel menuPanel;

    private JTable artikelTabelle;

    private ArtikelTabellenPanel artikelPanel;

    public Gui(String titel, String host, int port){
        super(titel);
        eshopInterface = new EshopFassade(host, port);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

            artikelPanel.setAutoCreateRowSorter(true);

            TableRowSorter<TableModel> sortierer = new TableRowSorter<>(artikelPanel.getModel());
            artikelPanel.setRowSorter(sortierer);
            List<RowSorter.SortKey> sortKeys = new ArrayList<>();

            int spaltenIndexZuSortieren = 1;
            sortKeys.add(new RowSorter.SortKey(spaltenIndexZuSortieren, SortOrder.ASCENDING));

            sortierer.setSortKeys(sortKeys);
            sortierer.sort();

            // Panels zusammen fassen
            add(suchPanel, BorderLayout.NORTH);
            add(artikelEinfuegenPanel, BorderLayout.WEST);
            add(scrollPane, BorderLayout.CENTER);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        setLocationRelativeTo(null);

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
    public void initializeKundenMenu() {
        suchPanel.setVisible(true);
        artikelEinfuegenPanel.setVisible(false);
        scrollPane.setVisible(true);

        kundenMenuPanel = new KundenMenuPanel(eshopInterface, this);
        add(kundenMenuPanel, BorderLayout.WEST);
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
    public void swichKunde() {
        initializeKundenMenu();
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
    public void wechselWarenkorbHinzu() {
        kundenMenuPanel.setVisible(false);
        artikelZumWarenkorbPanel = new ArtikelZumWarenkorbPanel(eshopInterface, this);
        artikelZumWarenkorbPanel.setVisible(true);
        add(artikelZumWarenkorbPanel, BorderLayout.WEST);
    }

    @Override
    public void wechselWarenkorb() {

    }

    @Override
    public void wechselZurKasse() {

    }

    @Override
    public void wechselLogout() {

    }

    @Override
    public void beiArtikelHinzugefügt() {
        // TODO
    }

    @Override
    public void kundenLogout() {
        // Zurück
    }

    @Override
    public void loginErfolgreich(Kunden kunde) {

    }
}
