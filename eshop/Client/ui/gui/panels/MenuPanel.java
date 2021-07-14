package eshop.Client.ui.gui.panels;

import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Mitarbeiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    public interface MenuPanelListener {
        public void wechselNeuerMitarbeiter();
        public void wechselNeuerArtikel(Mitarbeiter mitarbeiter);
        public void wechselBestand();
    }

    private EshopInterface eshopInterface;
    private MenuPanelListener listener;
    private Mitarbeiter mitarbeiter;

    private JButton mitarbeiterRegistrieren;
    private JButton neuerArtikel;
    private JButton bestandAendern;
    private JButton logAnzeigen;


    public MenuPanel(EshopInterface eshopInterface, Mitarbeiter mitarbeiter, MenuPanelListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;
        this.mitarbeiter = mitarbeiter;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        mitarbeiterRegistrieren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselNeuerMitarbeiter();
            }
        });

        neuerArtikel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselNeuerArtikel(mitarbeiter);
            }
        });

        bestandAendern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselBestand();
            }
        });

        logAnzeigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                JFrame logFrame = new JFrame();
                JTextArea logText = new JTextArea();
                java.util.List<String> logs = eshopInterface.getEreignisList();
                for (String log: logs) {
                    logText.append(log + "\n");
                }
                logFrame.add(logText);
                logFrame.setVisible(true);
                logFrame.setSize(500, 500);
            }
        });

    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        mitarbeiterRegistrieren = new JButton("Mitarbeiter registrieren:");
        mitarbeiterRegistrieren.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(mitarbeiterRegistrieren);

        neuerArtikel = new JButton("Neuer Artikel");
        neuerArtikel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(neuerArtikel);

        bestandAendern = new JButton("Bestand ändern");
        bestandAendern.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(bestandAendern);

        logAnzeigen = new JButton("Log Anzeigen");
        logAnzeigen.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(logAnzeigen);
    }

}
