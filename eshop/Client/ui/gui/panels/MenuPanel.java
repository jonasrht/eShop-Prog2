package eshop.Client.ui.gui.panels;

import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    public interface MenuPanelListener {
        public void wechselNeuerMitarbeiter();
        public void wechselNeuerArtikel();
        public void wechselBestand();
    }

    private EshopInterface eshopInterface;
    private MenuPanelListener listener;

    private JButton mitarbeiterRegistrieren;
    private JButton neuerArtikel;
    private JButton bestandAendern;

    public MenuPanel(EshopInterface eshopInterface, MenuPanelListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

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
                listener.wechselNeuerArtikel();
            }
        });

        bestandAendern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselBestand();
            }
        });

    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        mitarbeiterRegistrieren = new JButton("Mitarbeiter registrieren:");
        add(mitarbeiterRegistrieren);

        neuerArtikel = new JButton("Neuer Artikel");
        add(neuerArtikel);

        bestandAendern = new JButton("Bestand ändern");
        add(bestandAendern);
    }

}
