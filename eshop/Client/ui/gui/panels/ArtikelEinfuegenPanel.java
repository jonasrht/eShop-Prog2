package eshop.Client.ui.gui.panels;

import eshop.exceptions.ArtikelExistiertBereitsException;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Mitarbeiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

public class ArtikelEinfuegenPanel extends JPanel {

    public interface ArtikelHinzufuegenListener {
        public void aktikelPanelaktualisieren(java.util.List<Artikel> artikel);
        public void zurueckArtikelHinzu();
    }

    private EshopInterface eshopInterface;
    private ArtikelHinzufuegenListener listener;
    private Mitarbeiter mitarbeiter;

    private JTextField artikelNameFeld;
    private JTextField artikelPreisFeld;
    private JTextField artikelBestandFeld;
    private JTextField packungsGroeße;
    private JButton neuerArtBtn;
    private JButton zurueckBtn;
    private JCheckBox massenArtCheckBox;

    public ArtikelEinfuegenPanel(EshopInterface eshopInterface, Mitarbeiter mitarbeiter, ArtikelHinzufuegenListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;
        this.mitarbeiter = mitarbeiter;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        neuerArtBtn.addActionListener(new NeuerArtBtnListener());
        massenArtCheckBox.addActionListener(new MassenArtCheckListener());
        zurueckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.zurueckArtikelHinzu();
            }
        });
    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(new JLabel("Name:"));
        artikelNameFeld = new JTextField();
        add(artikelNameFeld);
        add(new JLabel("Preis:"));
        artikelPreisFeld = new JTextField();
        add(artikelPreisFeld);
        add(new JLabel("Bestand:"));
        artikelBestandFeld = new JTextField();
        add(artikelBestandFeld);
        add(new JLabel("Packungsgröße:"));
        packungsGroeße = new JTextField();
        add(packungsGroeße);
        packungsGroeße.setEnabled(false);
        massenArtCheckBox = new JCheckBox("Massengutartikel", false);
        add(massenArtCheckBox);

        // Button
        neuerArtBtn = new JButton("Einfügen");
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(neuerArtBtn);

        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));

        zurueckBtn = new JButton("<- Zurück");
        add(zurueckBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
    }

    class NeuerArtBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String artName = artikelNameFeld.getText();
            String artPreis = artikelPreisFeld.getText();
            String artBestand = artikelBestandFeld.getText();

            double preis = Double.parseDouble(artPreis);
            int bestand = Integer.parseInt(artBestand);

            if (massenArtCheckBox.isSelected()) {
                String packungsGroeßeStr = packungsGroeße.getText();
                int packungsGroeßeInt = Integer.parseInt(packungsGroeßeStr);

                if (!artName.isEmpty() && !artPreis.isEmpty() && !artBestand.isEmpty() && !packungsGroeßeStr.isEmpty()) {
                    try {
                        eshopInterface.massenartikelNeu(artName, preis, bestand, packungsGroeßeInt);
                        java.util.List<Artikel> artikel = eshopInterface.gibAlleArtikel();
                        listener.aktikelPanelaktualisieren(artikel);
                        artikelNameFeld.setText("");
                        artikelPreisFeld.setText("");
                        artikelBestandFeld.setText("");
                        packungsGroeße.setText("");
                    } catch (ArtikelExistiertBereitsException | IOException e2) {
                        JOptionPane.showMessageDialog(null,  e2.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (!artName.isEmpty() && !artPreis.isEmpty() && !artBestand.isEmpty() && !massenArtCheckBox.isSelected()) {
                try {
                    eshopInterface.artikelNeu(mitarbeiter.getPersonID(), artName, preis, bestand);
                    java.util.List<Artikel> artikel = eshopInterface.gibAlleArtikel();
                    artikelNameFeld.setText("");
                    artikelPreisFeld.setText("");
                    artikelBestandFeld.setText("");
                    listener.aktikelPanelaktualisieren(artikel);
                } catch (ArtikelExistiertBereitsException | IOException e1) {
                    JOptionPane.showMessageDialog(null,  e1.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,  "Bitte alle Felder ausfüllen!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class MassenArtCheckListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox massenArtCheckBox = (JCheckBox) e.getSource();
            if (massenArtCheckBox.isSelected()) {
                System.out.println("ausgewählt");
                packungsGroeße.setEnabled(true);
            } else {
                System.out.println("abgewählt");
                packungsGroeße.setEnabled(false);
            }
        }
    }
}
