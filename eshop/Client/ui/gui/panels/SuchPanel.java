package eshop.Client.ui.gui.panels;

import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class SuchPanel extends JPanel {

    public interface SucheArtikelPanelListener {
        void beiSuchErgebnisArtikel(List<Artikel> artikelListe);
    }

    private EshopInterface eshopInterface;
    private SucheArtikelPanelListener listener;

    private JTextField suchTextFeld;
    private JButton suchButton;


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
                listener.beiSuchErgebnisArtikel(suchErgebnis);
            }
        }
    }

}

