package eshop.Client.ui.gui.panels;

import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Mitarbeiter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    public interface MenuPanelListener {
        void wechselNeuerMitarbeiter();
        void wechselNeuerArtikel(Mitarbeiter mitarbeiter);
        void wechselBestand(Mitarbeiter mitarbeiter);
        void wechselMitarbeiterAbmelden();
    }

    private EshopInterface eshopInterface;
    private MenuPanelListener listener;
    private Mitarbeiter mitarbeiter;

    private JButton mitarbeiterRegistrieren;
    private JButton neuerArtikel;
    private JButton bestandAendern;
    private JButton logAnzeigenBtn;
    private JButton abmeldenBtn;


    public MenuPanel(EshopInterface eshopInterface, Mitarbeiter mitarbeiter, MenuPanelListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;
        this.mitarbeiter = mitarbeiter;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        mitarbeiterRegistrieren.addActionListener(e -> listener.wechselNeuerMitarbeiter());

        neuerArtikel.addActionListener(e -> listener.wechselNeuerArtikel(mitarbeiter));

        bestandAendern.addActionListener(e -> listener.wechselBestand(mitarbeiter));

        logAnzeigenBtn.addActionListener(e -> {
            logAnzeigen();
        });

        abmeldenBtn.addActionListener(e -> {
            listener.wechselMitarbeiterAbmelden();
        });

    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Neuer Mitarbeiter Button
        mitarbeiterRegistrieren = new JButton("Mitarbeiter registrieren:");
        mitarbeiterRegistrieren.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(mitarbeiterRegistrieren);

        // Neuer Artikel Button
        neuerArtikel = new JButton("Neuer Artikel");
        neuerArtikel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(neuerArtikel);

        // Bestand ändern Button
        bestandAendern = new JButton("Bestand ändern");
        bestandAendern.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(bestandAendern);

        // Log anzeigen Button
        logAnzeigenBtn = new JButton("Log Anzeigen");
        logAnzeigenBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(logAnzeigenBtn);

        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));

        // Abmelden Button
        abmeldenBtn = new JButton("<- Abmelden");
        abmeldenBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(abmeldenBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
    }

    private void logAnzeigen() {
        // Frame
        JFrame logFrame = new JFrame();
        logFrame.setLayout(new BorderLayout());

        // Log Titel
        JPanel logHeader = new JPanel();
        logHeader.setLayout(new BoxLayout(logHeader, BoxLayout.Y_AXIS));
        JLabel logHeaderText = new JLabel("LOG");
        logHeaderText.setFont(new Font("Arial", Font.PLAIN, 20));
        logHeaderText.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        logHeader.setBackground(Color.white);
        logHeader.add(logHeaderText);

        // Log Ausgabe
        JTextArea logText = new JTextArea();
        logText.setEditable(false);
        java.util.List<String> logs = eshopInterface.getEreignisList();
        for (String log: logs) {
            logText.append(log + "\n");
        }

        logFrame.add(logHeader, BorderLayout.NORTH);
        logFrame.add(logText, BorderLayout.CENTER);
        logFrame.setVisible(true);
        logFrame.setSize(500, 500);
    }
}
