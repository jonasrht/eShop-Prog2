package eshop.Client.ui.gui.panels;

import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Kunden;
import eshop.valueobjects.Warenkorb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KundenMenuPanel extends JPanel {

    public interface KundenMenuPanelListener {
        public void wechselWarenkorbHinzu(Kunden kunde);
        public void wechselWarenkorb(Kunden kunde);
        public void wechselZurKasse(Kunden kunde);
        public void wechselLogout();
    }

    private EshopInterface eshopInterface;
    private KundenMenuPanelListener listener;

    private JButton zumWarenkorbHinzuBtn;
    private JButton warenkorbAnzeigenBtn;
    private JButton zurKasseBtn;
    private JButton logoutBtn;
    private Kunden kunde;

    public KundenMenuPanel(EshopInterface eshopInterface, Kunden kunde, KundenMenuPanelListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;
        this.kunde = kunde;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        // Artikel zum Warenkorb hinzufügen
        zumWarenkorbHinzuBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselWarenkorbHinzu(kunde);
            }
        });

        // Warenkorb anzeigen
        warenkorbAnzeigenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselWarenkorb(kunde);
            }
        });

        // Zur Kasse
        zurKasseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.wechselZurKasse(kunde);
            }
        });

        // Logut
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.exit();
                listener.wechselLogout();
            }
        });
    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        zumWarenkorbHinzuBtn = new JButton("Artikel in den Warenkorb");
        zumWarenkorbHinzuBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(zumWarenkorbHinzuBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        warenkorbAnzeigenBtn = new JButton("Warenkorb anzeigen");
        warenkorbAnzeigenBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(warenkorbAnzeigenBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        zurKasseBtn = new JButton("Zur Kasse");
        zurKasseBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(zurKasseBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
        logoutBtn = new JButton("<- Logout");
        logoutBtn.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));

        add(logoutBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
    }
}
