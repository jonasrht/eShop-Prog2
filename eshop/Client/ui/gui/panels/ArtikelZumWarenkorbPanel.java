package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.exceptions.BestandZuGering;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtikelZumWarenkorbPanel extends JPanel {

    public interface ArtikelZumWarenkorbListener {
        public void beiArtikelHinzugefügt();
        public void kundenLogout();
    }

    private EshopInterface eshopInterface;
    private ArtikelZumWarenkorbListener listener;

    private JTextField artikelIDFeld;
    private JTextField artikelMengeFeld;
    private JButton hinzufuegenBtn;
    private JButton zurueckBtn;

    public ArtikelZumWarenkorbPanel(EshopInterface eshopInterface, ArtikelZumWarenkorbListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        // Artikel zum Warenkorb hinzufügen
        hinzufuegenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String artikelIdStr = artikelIDFeld.getText();
                String mengeStr = artikelMengeFeld.getText();

                if (!artikelIdStr.isEmpty() && !mengeStr.isEmpty()) {
                    int artikelId = Integer.parseInt(artikelIdStr);
                    int menge = Integer.parseInt(mengeStr);
                    try {
                        Artikel artikel = eshopInterface.getArtikelViaID(artikelId);
                        eshopInterface.artikelInWarenkorb(1, artikel, menge);
                        listener.beiArtikelHinzugefügt();
                        Gui.infoBox("Artikel " + artikel.getName() + " " + menge + " mal zum Warenkorb hinzugefügt.", "Info");
                    } catch (ArtikelNichtGefundenException | BestandZuGering e1) {
                        Gui.errorBox(e1.getMessage());
                    }
                }

            }
        });

        // Logout
        zurueckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.kundenLogout();
            }
        });
    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new JLabel("Artikel ID:"));
        artikelIDFeld = new JTextField();
        add(artikelIDFeld);
        add(new JLabel("Anzahl der Artikel:"));
        artikelMengeFeld = new JTextField();
        add(artikelMengeFeld);
        hinzufuegenBtn = new JButton("Zum Warenkorb hinzufügen");
        add(hinzufuegenBtn);
        zurueckBtn = new JButton("<- Zurück");
        add(zurueckBtn);
    }

}
