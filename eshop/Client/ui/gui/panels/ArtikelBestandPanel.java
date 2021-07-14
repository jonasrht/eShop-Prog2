package eshop.Client.ui.gui.panels;

import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ArtikelBestandPanel extends JPanel {

    public interface ArtikelBestandListener {
        public void wechselMenu();
        public void beiBestandGeaendert(java.util.List<Artikel> artikel);
    }
    private EshopInterface eshopInterface;
    private ArtikelBestandListener listener;

    private JTextField artikelIDFeld;
    private JTextField neuerBestandFeld;
    private JButton bestandAendernBtn;
    private JButton zurueckBtn;
    
    public ArtikelBestandPanel(EshopInterface eshopInterface, ArtikelBestandListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;
        
        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        // Action für Bestandändern
        bestandAendernBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artIDStr = artikelIDFeld.getText();
                String neuerBestandStr = neuerBestandFeld.getText();

                if (!artIDStr.isEmpty() && !neuerBestandStr.isEmpty()) {
                    int artID = Integer.parseInt(artIDStr);
                    int neuerBestand = Integer.parseInt(neuerBestandStr);
                    try {
                        Artikel artikel = eshopInterface.getArtikelViaID(artID);
                        eshopInterface.artikelBestandAendern(artikel, neuerBestand);
                        java.util.List<Artikel> artikelListe = eshopInterface.gibAlleArtikel();
                        listener.beiBestandGeaendert(artikelListe);
                    } catch (ArtikelNichtGefundenException | IOException e1) {
                        JOptionPane.showMessageDialog(null,  e1.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                artikelIDFeld.setText("");
                neuerBestandFeld.setText("");
            }
        });

        // Action für zurück Button
        zurueckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselMenu();
            }
        });

    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Artikel ID:"));
        artikelIDFeld = new JTextField();
        add(artikelIDFeld);

        add(new JLabel("Neuer Bestand:"));
        neuerBestandFeld = new JTextField();
        add(neuerBestandFeld);
        // Button Bestand ändern
        bestandAendernBtn = new JButton("Bestand ändern");
        add(bestandAendernBtn);
        // Button zurücks
        zurueckBtn = new JButton("<- Zurück");

        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));

        add(zurueckBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
    }

}
