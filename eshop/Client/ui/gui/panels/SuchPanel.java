package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class SuchPanel extends JPanel {

    public interface SucheArtikelPanelListener {
        public void beiSuchErgebnisArtikel(List<Artikel> artikelListe);
    }

    private EshopInterface eshopInterface;
    private SucheArtikelPanelListener listener;

    private JTextField suchTextFeld;
    private JButton suchButton;

    private JLabel fehlermeldungFeld = null;

    public SuchPanel(EshopInterface eshopInterface, SucheArtikelPanelListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleUI() {
//        GridBagLayout gridBagLayout = new GridBagLayout();
//        this.setLayout(gridBagLayout);
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.gridy = 0; // Zeile 0
//
//        JLabel sucheLabel = new JLabel("Artikelname:");
//        constraints.gridx = 0; // Spalte 0
//        constraints.weightx = 0.2; // 20% der gesamten Breite
//        constraints.anchor = GridBagConstraints.EAST;
//        gridBagLayout.setConstraints(sucheLabel, constraints);
//        this.add(sucheLabel);
//
//        sucheTextFeld = new JTextField();
//        sucheTextFeld.setToolTipText("Suchbegriff eingeben.");
//        constraints.gridx = 1; // Spalte 1
//        constraints.weightx = 0.6; // 60% der gesamten Breite
//        gridBagLayout.setConstraints(sucheTextFeld, constraints);
//        this.add(sucheTextFeld);
//
//        suchButton = new JButton("Suchen!");
//        constraints.gridx = 2; // Spalte 2
//        constraints.weightx = 0.2; // 20% der Gesammtbreite
//        gridBagLayout.setConstraints(suchButton, constraints);
//        this.add(suchButton);
//
//        fehlermeldungFeld = new JLabel("");
//        constraints.gridy = 1; // Zeile 1
//        constraints.gridx = 1; // Spalte 1
//        constraints.weightx = 0.2; // 20% der Gesammtbreite
//        gridBagLayout.setConstraints(fehlermeldungFeld, constraints);
//        this.add(fehlermeldungFeld);
        this.setLayout(new FlowLayout());
        add(new JLabel("Suchbegriff"));
        suchTextFeld = new JTextField();
        suchTextFeld.setPreferredSize(new Dimension(200, 30));
        suchTextFeld.setToolTipText("Suchbegriff eingeben.");
        add(suchTextFeld);
        suchButton = new JButton("Such!");
        add(suchButton);
    }

    private void erstelleEreignisse() {
        suchButton.addActionListener(new SuchListener());
    }

    class SuchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource().equals(suchButton)) {
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
                //artikelListe.setListData(new Vector<>(suchErgebnis));
            }
        }
    }

}

