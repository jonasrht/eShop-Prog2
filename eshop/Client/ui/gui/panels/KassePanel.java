package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Kunden;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class KassePanel extends JPanel {

    public interface KasseListener {
        public void zurueckKasse(Kunden kunde);
    }

    private EshopInterface eshopInterface;
    private KasseListener listener;
    private Kunden kunde;

    private JTextArea rechnungsArea;
    private JButton zurueck;

    public KassePanel(EshopInterface eshopInterface, Kunden kunde, KasseListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;
        this.kunde = kunde;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        zurueck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.zurueckKasse(kunde);
            }
        });
    }

    private void erstelleUI() {
        List<String> rechnungList = eshopInterface.rechnungErstellen(kunde.getPersonID());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        rechnungsArea = new JTextArea();
        rechnungsArea.setEditable(false);

        for (String rechnung : rechnungList) {
            rechnungsArea.append(rechnung +"\n");
        }
        add(rechnungsArea);
        zurueck = new JButton("<- Zurück");
        add(zurueck);
    }
}
