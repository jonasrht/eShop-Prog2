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
        public void switchScene();
        public void swichKunde();
    }

    private EshopInterface eshopInterface;
    private SucheArtikelPanelListener listener;

    private JTextField suchTextFeld;
    private JButton suchButton;

    // Nur zum Test
    private JButton neueScene;
    private JButton wechselKundeBtn;

    public SuchPanel(EshopInterface eshopInterface, SucheArtikelPanelListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleUI() {
        this.setLayout(new FlowLayout());
        add(new JLabel("Suchbegriff"));
        suchTextFeld = new JTextField();
        suchTextFeld.setPreferredSize(new Dimension(200, 30));
        suchTextFeld.setToolTipText("Suchbegriff eingeben.");
        add(suchTextFeld);
        suchButton = new JButton("Such!");
        add(suchButton);
        neueScene = new JButton("Mitarbeiter");
        add(neueScene);
        wechselKundeBtn = new JButton("Kunde");
        add(wechselKundeBtn);
    }

    private void erstelleEreignisse() {
        suchButton.addActionListener(new SuchListener());

        neueScene.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.switchScene();
            }
        });

        wechselKundeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.swichKunde();
            }
        });
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
                listener.beiSuchErgebnisArtikel(suchErgebnis);
            }
        }
    }

}

