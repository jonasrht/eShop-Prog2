package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunden;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WarenkorbPanel extends JPanel {

    public interface WarenkorbListener {
        public void warenkorbZurueck(Kunden kunde);
        public void warenkorbZurKasse(Kunden kunde);
    }

    private EshopInterface eshopInterface;
    private WarenkorbListener listener;
    private Kunden kunde;

    private JButton warenkorbLeerenBtn;
    private JButton anzahlWarenAendernBtn;
    private JButton zurKasse;
    private JButton zurueckBtn;
    private JTextArea warenkorbAusgabe;
    private JComboBox artikelAuswahl;
    private JTextField neueAnzahl;
    private JPanel buttonsPanel;
    private JPanel warenkorb;
    private JPanel neueAnzahlPanel;

    public WarenkorbPanel(EshopInterface eshopInterface, Kunden kunde, WarenkorbListener listener) {
        this.eshopInterface = eshopInterface;
        this.kunde = kunde;
        this. listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        warenkorbLeerenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eshopInterface.artikelInWarenkorbLeeren(kunde.getPersonID());
                // JTextArea leeren
                warenkorbAusgabe.selectAll();
                warenkorbAusgabe.replaceSelection("");
                warenkorbAusgabe.update(warenkorbAusgabe.getGraphics());
            }
        });

        zurueckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.warenkorbZurueck(kunde);
            }
        });

        zurKasse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.warenkorbZurKasse(kunde);
            }
        });

        anzahlWarenAendernBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int artikelId = (int) artikelAuswahl.getSelectedItem();
                String neueAnzahlStr = neueAnzahl.getText();
                if (!neueAnzahlStr.isEmpty()) {
                    try {
                        int anzahl = Integer.parseInt(neueAnzahlStr);
                        Artikel artikel = eshopInterface.getArtikelViaID(artikelId);
                        eshopInterface.anzahlArtikelAendern(kunde.getPersonID(), artikel, anzahl);
                        // JTextArea refresh
                        warenkorbAusgabe.update(warenkorbAusgabe.getGraphics());
                    } catch (ArtikelNichtGefundenException e1) {
                        Gui.errorBox(e1.getMessage());
                    }
                }
            }
        });
    }

    private void erstelleUI() {
        setLayout(new BorderLayout());
        warenkorb = new JPanel();
        neueAnzahlPanel = new JPanel();
        neueAnzahlPanel.setLayout(new FlowLayout());

        // Warenkorb
        warenkorbAusgabe = new JTextArea();
        warenkorbAusgabe.setEditable(false);
        int kundenId = kunde.getPersonID();
        List<String> warenkorbListe = eshopInterface.warenkorbAnzeigen(kundenId);
        for (String warenkorb: warenkorbListe) {
            warenkorbAusgabe.append(warenkorb + "\n");
        }
        warenkorb.add(warenkorbAusgabe);

        // Buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        JLabel headerText = new JLabel("Warenkorb");
        headerText.setFont(headerText.getFont().deriveFont(16f));
        headerText.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.add(headerText);
        warenkorbLeerenBtn = new JButton("Warenkorb leeren");
        warenkorbLeerenBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonsPanel.add(warenkorbLeerenBtn);
        zurKasse = new JButton("Zur Kasse");
        zurKasse.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonsPanel.add(zurKasse);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        zurueckBtn = new JButton("<- Zurück");
        zurueckBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        buttonsPanel.add(zurueckBtn);

        // Anzahl der Artikel im Warenkorb ändern
        // Alle Artikel IDs aus dem Warenkorb in die JComboBox einfügen
        List<String> warenkorbString = eshopInterface.warenkorbAnzeigen(kunde.getPersonID());
        List<Integer> artikelIds = new ArrayList<Integer>();
        for (String artikelStr : warenkorbString) {
            // Nimm die 4. Ziffer, da dort immer die ID steht.
            String artikelIdStr = artikelStr.substring(4, 5);
            int artikelId = Integer.parseInt(artikelIdStr);
            artikelIds.add(artikelId);
        }
        artikelAuswahl = new JComboBox(artikelIds.toArray());
        neueAnzahlPanel.add(new JLabel("Neue Anzahl"));
        neueAnzahl = new JTextField(5);
        neueAnzahlPanel.add(neueAnzahl);
        neueAnzahlPanel.add(new JLabel("Artikel ID:"));
        neueAnzahlPanel.add(artikelAuswahl);
        anzahlWarenAendernBtn = new JButton("Anzahl der Artikel im Korb ändern");
        neueAnzahlPanel.add(anzahlWarenAendernBtn);

        // Borders
        warenkorb.setBorder(BorderFactory.createLineBorder(Color.gray));
        buttonsPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        neueAnzahlPanel.setBorder(BorderFactory.createLineBorder(Color.gray));



        add(warenkorb, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.WEST);
        add(neueAnzahlPanel, BorderLayout.SOUTH);
    }
}
