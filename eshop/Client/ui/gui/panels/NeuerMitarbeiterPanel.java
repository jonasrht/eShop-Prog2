package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.interfaces.EshopInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NeuerMitarbeiterPanel extends JPanel {

    public interface NeuerMitarbeiterListener {
        public void zurueckNeuerMitarbeiter();
    }

    private EshopInterface eshopInterface;
    private NeuerMitarbeiterListener listener;

    private JTextField nameFeld;
    private JTextField emailFeld;
    private JPasswordField passwortFeld;
    private JPasswordField passwordCheckFeld;
    private JButton registrierenBtn;
    private JButton zurueckBtn;

    public NeuerMitarbeiterPanel(EshopInterface eshopInterface, NeuerMitarbeiterListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        registrierenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameFeld.getText();
                String email = emailFeld.getText();
                String passwort = passwortFeld.getText();
                String passwortCheck = passwordCheckFeld.getText();

                if (!name.isEmpty() && !email.isEmpty() && !passwort.isEmpty() && !passwortCheck.isEmpty()) {
                    if (passwort.equals(passwortCheck)) {
                           if (eshopInterface.checkPass(passwort)) {
                               eshopInterface.registerEmployee(name, email, passwort);
                               Gui.infoBox("Mitarbeiter erfolgreich angelegt.", "Info");
                           } else {

                               Gui.errorBox("Bitte geben Sie min. 8. Zeichen mit min. einen Großbuchstaben und einer Zahl ein.");
                           }
                    } else {
                        Gui.errorBox("Passwörter stimmen nicht überein!");
                    }
                } else {
                    Gui.errorBox("Bitte füllen Sie alle Felder aus.");
                }

                nameFeld.setText("");
                emailFeld.setText("");
                passwortFeld.setText("");
                passwordCheckFeld.setText("");
            }
        });

        zurueckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.zurueckNeuerMitarbeiter();
            }
        });
    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(new JLabel("Mitarbeiter Name"));
        nameFeld = new JTextField();
        add(nameFeld);
        add(new JLabel("E-Mail"));
        emailFeld = new JTextField();
        add(emailFeld);
        add(new JLabel("Passwort"));
        passwortFeld = new JPasswordField();
        add(passwortFeld);
        add(new JLabel("Passwort"));
        passwordCheckFeld = new JPasswordField();
        add(passwordCheckFeld);
        registrierenBtn = new JButton("Registrieren");
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(registrierenBtn);
        zurueckBtn = new JButton("<- Zurück");

        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));

        add(zurueckBtn);
        add(Box.createRigidArea(new Dimension(0, 5)));
    }
}
