package eshop.Client.ui.gui.menus;

import eshop.interfaces.EshopInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenueMitarbeiter extends JMenuBar {

    public interface MenuMitarbeiterListener {

    }

    private EshopInterface eshopInterface;
    private MenuMitarbeiterListener listener;

    private JMenuBar menue;
    private JMenu dateiMenue;
    private JMenuItem speichern;
    private JMenuItem speichernUndSchliessen;

    public MenueMitarbeiter(EshopInterface eshopInterface, MenuMitarbeiterListener listener) {
        super();
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleMenue();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        // Speichern Action
        speichern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eshopInterface.verbindungsAbbruch();
            }
        });

        // Speichern und Schließen
        speichernUndSchliessen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eshopInterface.verbindungsAbbruch();
                System.exit(0);
            }
        });
    }

    private void erstelleMenue() {
        dateiMenue = new JMenu("Datei");
        speichern = new JMenuItem("Speichern");
        speichernUndSchliessen = new JMenuItem("Speichern & Schließen");
        dateiMenue.add(speichern);
        dateiMenue.add(speichernUndSchliessen);

        add(dateiMenue);
    }

}
