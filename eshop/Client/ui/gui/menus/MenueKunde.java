package eshop.Client.ui.gui.menus;

import eshop.interfaces.EshopInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenueKunde extends JMenuBar {
    private EshopInterface eshopInterface;

    private JMenuBar menue;
    private JMenu dateiMenue;
    private JMenuItem speichern;
    private JMenuItem speichernUndSchliessen;

    public MenueKunde(EshopInterface eshopInterface) {
        super();
        this.eshopInterface = eshopInterface;

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
