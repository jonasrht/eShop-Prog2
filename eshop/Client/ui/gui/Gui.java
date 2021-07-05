package eshop.Client.ui.gui;

import javax.swing.*;

import eshop.Client.net.EshopFassade;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Vector;


public class Gui extends JFrame {
    
    private EshopInterface eshopInterface;

    public Gui(String titel, String host, int port){
        super(titel);
        eshopInterface = new EshopFassade(host, port);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initialize();
    }

    public void initialize() {
        setLayout(new BorderLayout());

        // NORTH
        JPanel suchPanel = new JPanel();
        suchPanel.setLayout(new FlowLayout());
        suchPanel.add(new JLabel("Suchbegriff"));
        JTextField suchTextFeld = new JTextField();
        suchTextFeld.setPreferredSize(new Dimension(200, 30));
        suchPanel.add(suchTextFeld);
        suchPanel.add(new JButton("Such!"));

        // WEST
        JPanel einfuegePanel = new JPanel();
        einfuegePanel.setLayout(new BoxLayout(einfuegePanel, BoxLayout.PAGE_AXIS));
        einfuegePanel.add(new JLabel("Nummer:"));
        einfuegePanel.add(new JTextField());
        einfuegePanel.add(new JLabel("Titel:"));
        einfuegePanel.add(new JTextField());

        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        einfuegePanel.add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));


        // CENTER
        try {
            java.util.List<Artikel> artikel = eshopInterface.gibAlleArtikel();
            JList artikelListe = new JList(new Vector(artikel));
            add(suchPanel, BorderLayout.NORTH);
            add(einfuegePanel, BorderLayout.WEST);
            add(artikelListe, BorderLayout.CENTER);
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
}
