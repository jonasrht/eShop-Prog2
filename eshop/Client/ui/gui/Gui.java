package eshop.Client.ui.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import eshop.Client.net.EshopFassade;
import eshop.Client.ui.gui.models.ArtikelTabellenModel;
import eshop.Client.ui.gui.panels.ArtikelTabellenPanel;
import eshop.Client.ui.gui.panels.SuchPanel;
import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Vector;


public class Gui extends JFrame implements ActionListener, SuchPanel.SucheArtikelPanelListener {
    
    private final EshopInterface eshopInterface;
    //String name, double preis, int bestand
    private JTextField artikelNameFeld;
    private JTextField artikelPreisFeld;
    private JTextField artikelBestandFeld;
    private JTextField suchTextFeld;

    private JList artikelListe;
    private JButton neuerArtBtn;

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
        SuchPanel suchPanel = new SuchPanel(eshopInterface, this);
//        JPanel suchPanel = new JPanel();
//        suchPanel.setLayout(new FlowLayout());
//        suchPanel.add(new JLabel("Suchbegriff"));
//        suchTextFeld = new JTextField();
//        suchTextFeld.setPreferredSize(new Dimension(200, 30));
//        suchPanel.add(suchTextFeld);
//        JButton suchButton = new JButton("Such!");
//        ActionPerformedSuchBtn suchListener = new ActionPerformedSuchBtn();
//        suchButton.addActionListener(suchListener);
//        suchPanel.add(suchButton);

        // WEST
        JPanel einfuegePanel = new JPanel();
        einfuegePanel.setLayout(new BoxLayout(einfuegePanel, BoxLayout.PAGE_AXIS));
        einfuegePanel.add(new JLabel("Name:"));
        artikelNameFeld = new JTextField();
        einfuegePanel.add(artikelNameFeld);
        einfuegePanel.add(new JLabel("Preis:"));
        artikelPreisFeld = new JTextField();
        einfuegePanel.add(artikelPreisFeld);
        einfuegePanel.add(new JLabel("Bestand:"));
        artikelBestandFeld = new JTextField();
        einfuegePanel.add(artikelBestandFeld);


        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        einfuegePanel.add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));

        // Button
        neuerArtBtn = new JButton("Einfügen");
        neuerArtBtn.addActionListener(this);
        einfuegePanel.add(neuerArtBtn);

        // CENTER
        try {
            java.util.List<Artikel> artikel = eshopInterface.gibAlleArtikel();
            artikelPanel = new ArtikelTabellenPanel(artikel);
            JScrollPane scrollPane = new JScrollPane(artikelPanel);

            add(suchPanel, BorderLayout.NORTH);
            add(einfuegePanel, BorderLayout.WEST);
            add(scrollPane, BorderLayout.CENTER);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        // Panels zusammen fassen


        setSize(640, 480);
        setVisible(true);
    }

    public static void main(String[] args) {
        String host = "localhost";

        Gui gui = new Gui("Asia Shop", host, 6789);
    }

    @Override
    public void beiSuchErgebnisArtikel(List<Artikel> artikelListe) {
        artikelPanel.aktualisiereArtikelListe(artikelListe);
    }

    class ActionPerformedSuchBtn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String suche = suchTextFeld.getText();
            java.util.List<Artikel> suchErgebnis = null;
            if (suche.isEmpty()) {
                try {
                    suchErgebnis = eshopInterface.gibAlleArtikel();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                suchErgebnis = eshopInterface.sucheNachArtikel(suche);
            }
            aktualisiereArtikelListe(suchErgebnis);
        }
    }

    // Wenn auf Button geklickt wird
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(neuerArtBtn)) {
            System.out.println("Klick");

            String artName = artikelNameFeld.getText();
            String artPreis = artikelPreisFeld.getText();
            String artBestand = artikelBestandFeld.getText();
    
            double preis = Double.parseDouble(artPreis);
            int bestand = Integer.parseInt(artBestand);
    
            try {
                eshopInterface.artikelNeu(artName, preis, bestand);
                eshopInterface.verbindungsAbbruch();
                //artikelListe.ensureIndexIsVisible(index);
                java.util.List<Artikel> artikel = eshopInterface.gibAlleArtikel();
                artikelListe.setListData(new Vector(artikel));
            } catch (ArtikelExistiertBereitsException | IOException e1) {
                // TODO Auto-generated catch block
                e1.getMessage();
            }
        }
    }

    public void aktualisiereArtikelListe(java.util.List<Artikel> artikel) {

//        // TableModel von JTable holen und ...
//        DefaultListModel<Artikel> listModel = (DefaultListModel<Artikel>) artikelListe.getModel();
////		// ... Inhalt aktualisieren
//        listModel.clear();
//        listModel.addAll(artikel);

        ArtikelTabellenModel tabellenModel = (ArtikelTabellenModel)  artikelTabelle.getModel();
        tabellenModel.setArtikel(artikel);
    }
}
