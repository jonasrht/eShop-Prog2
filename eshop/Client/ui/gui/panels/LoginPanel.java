package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Kunden;
import eshop.valueobjects.Mitarbeiter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class LoginPanel extends JPanel {

    public interface LoginListener {
        void loginErfolgreich(Kunden kunde);
        void loginMitarbeiterErfolgreich(Mitarbeiter mitarbeiter);
        void wechselRegisterKunde();
    }

    private EshopInterface eshopInterface;
    private LoginListener listener;

    private JLabel userLabel;
    private JTextField userText;
    private JLabel passLabel;
    private JPasswordField passText;
    private JButton loginButton;
    private JButton loginMitarbeiterBtn;
    private JButton beendenBtn;
    private JLabel mitarbeiterLogin;
    private JLabel registrieren;

    public LoginPanel(EshopInterface eshopInterface, LoginListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        // Wenn Login Button geklickt wird
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nutzerEingabe = userText.getText();
                String passEingabe = passText.getText();
                try {
                    Kunden kunde;
                    kunde = eshopInterface.logInCustomer(passEingabe, nutzerEingabe);
                    listener.loginErfolgreich(kunde);
                } catch (LoginFehlgeschlagen e1) {
                    userText.setText("");
                    passText.setText("");
                    Gui.errorBox(e1.getMessage());
                }
            }
        });

        loginMitarbeiterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nutzerEingabe = userText.getText();
                String passEingabe = passText.getText();
                try {
                    Mitarbeiter mitarbeiter;
                    mitarbeiter = eshopInterface.logInEmployee(passEingabe, nutzerEingabe);
                    listener.loginMitarbeiterErfolgreich(mitarbeiter);
                } catch (LoginFehlgeschlagen e1) {
                    userText.setText("");
                    passText.setText("");
                    Gui.errorBox(e1.getMessage());
                }
            }
        });

        mitarbeiterLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Juhu");
                mitarbeiterLogin.setVisible(false);
                registrieren.setVisible(false);
                loginButton.setVisible(false);
                loginMitarbeiterBtn.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mitarbeiterLogin.setForeground(Color.blue);
                mitarbeiterLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                mitarbeiterLogin.setForeground(Color.black);
            }
        });

        registrieren.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                listener.wechselRegisterKunde();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                registrieren.setForeground(Color.blue);
                registrieren.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                registrieren.setForeground(Color.black);
            }
        });

        beendenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eshopInterface.verbindungsAbbruch();
                System.exit(0);
            }
        });

    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        userLabel = new JLabel("E-Mail");
        add(userLabel);

        userText = new JTextField(1);
        add(userText);

        passLabel = new JLabel("Passwort");
        add(passLabel);

        passText = new JPasswordField(1);
        add(passText);

        loginButton = new JButton("Log in");
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(loginButton);

        loginMitarbeiterBtn = new JButton("Login");
        add(loginMitarbeiterBtn);
        loginMitarbeiterBtn.setVisible(false);

        mitarbeiterLogin = new JLabel("bist du ein Mitarbeiter?");
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(mitarbeiterLogin);

        registrieren = new JLabel("Registrieren");
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(registrieren);

        // Abstand
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferedSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferedSize, fillerMaxSize));

        beendenBtn = new JButton("Beenden");
        add(beendenBtn);
        add(Box.createRigidArea(new Dimension(0, 15)));
    }
}
