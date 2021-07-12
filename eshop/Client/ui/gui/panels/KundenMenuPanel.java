package eshop.Client.ui.gui.panels;

import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Warenkorb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KundenMenuPanel extends JPanel {

    public interface KundenMenuPanelListener {
        public void wechselWarenkorbHinzu();
        public void wechselWarenkorb();
        public void wechselZurKasse();
        public void wechselLogout();
    }

    private EshopInterface eshopInterface;
    private KundenMenuPanelListener listener;

    private JButton zumWarenkorbHinzuBtn;
    private JButton warenkorbAnzeigenBtn;
    private JButton zurKasseBtn;
    private JButton logoutBtn;

    public KundenMenuPanel(EshopInterface eshopInterface, KundenMenuPanelListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        // Artikel zum Warenkorb hinzufügen
        zumWarenkorbHinzuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselWarenkorbHinzu();
            }
        });

        // Warenkorb anzeigen
        warenkorbAnzeigenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselWarenkorb();
            }
        });

        // Zur Kasse
        zurKasseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselZurKasse();
            }
        });

        // Logut
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselLogout();
            }
        });
    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        zumWarenkorbHinzuBtn = new JButton("Artikel in den Warenkorb");
        add(zumWarenkorbHinzuBtn);
        warenkorbAnzeigenBtn = new JButton("Warenkorb anzeigen");
        add(warenkorbAnzeigenBtn);
        zurKasseBtn = new JButton("Zur Kasse");
        add(zurKasseBtn);
        logoutBtn = new JButton("<- Logout");
        add(logoutBtn);
    }
}
