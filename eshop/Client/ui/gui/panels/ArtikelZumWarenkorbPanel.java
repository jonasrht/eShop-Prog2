package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.exceptions.ArtikelNichtGefundenException;
import eshop.exceptions.BestandZuGering;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Artikel;
import eshop.valueobjects.Kunden;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArtikelZumWarenkorbPanel extends JPanel {

    public interface ArtikelZumWarenkorbListener {
        public void beiArtikelHinzugefügt();
        public void artikelWarenZurueck();
    }

    private EshopInterface eshopInterface;
    private ArtikelZumWarenkorbListener listener;
    private Kunden kunde;

    private JTextField artikelIDFeld;
    private JTextField artikelMengeFeld;
    private JButton hinzufuegenBtn;
    private JButton zurueckBtn;

    public ArtikelZumWarenkorbPanel(EshopInterface eshopInterface, Kunden kunde, ArtikelZumWarenkorbListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;
        this.kunde = kunde;

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
                    int kundenID = kunde.getPersonID();
                    int artikelId = Integer.parseInt(artikelIdStr);
                    int menge = Integer.parseInt(mengeStr);
                    try {
                        Artikel artikel = eshopInterface.getArtikelViaID(artikelId);
                        eshopInterface.artikelInWarenkorb(kundenID, artikel, menge);
                        listener.beiArtikelHinzugefügt();
                        //Gui.infoBox("Artikel " + artikel.getName() + " " + menge + " mal zum Warenkorb hinzugefügt.", "Info");
                    } catch (ArtikelNichtGefundenException | BestandZuGering e1) {
                        Gui.errorBox(e1.getMessage());
                    }
                }
                artikelIDFeld.setText("");
                artikelMengeFeld.setText("");
            }
        });

        // Logout
        zurueckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.artikelWarenZurueck();
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
