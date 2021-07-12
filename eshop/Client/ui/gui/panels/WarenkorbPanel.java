package eshop.Client.ui.gui.panels;

import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Kunden;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        warenkorbAusgabe = new JTextArea();
        warenkorbAusgabe.setEditable(false);

        int kundenId = kunde.getPersonID();
        List<String> warenkorbListe = eshopInterface.warenkorbAnzeigen(kundenId);
        for (String warenkorb: warenkorbListe) {
            warenkorbAusgabe.append(warenkorb + "\n");
        }
        add(warenkorbAusgabe);

        warenkorbLeerenBtn = new JButton("Warenkorb leeren");
        add(warenkorbLeerenBtn);
        anzahlWarenAendernBtn = new JButton("Anzahl der Artikel im Korb ändern");
        add(anzahlWarenAendernBtn);
        zurKasse = new JButton("Zur Kasse");
        add(zurKasse);
        zurueckBtn = new JButton("<- Zurück");
        add(zurueckBtn);
    }
}
