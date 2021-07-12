package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Kunden;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;

public class LoginPanel extends JPanel {

    public interface LoginListener {
        public void loginErfolgreich(Kunden kunde);
    }

    private EshopInterface eshopInterface;
    private LoginListener listener;

    private JLabel userLabel;
    private JTextField userText;
    private JLabel passLabel;
    private JPasswordField passText;
    private JButton loginButton;
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
                    kunde = eshopInterface.logInCustomer(nutzerEingabe, passEingabe);
                    listener.loginErfolgreich(kunde);
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
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mitarbeiterLogin.setForeground(Color.blue);
                Font font = mitarbeiterLogin.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                mitarbeiterLogin.setFont(font.deriveFont(attributes));
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
                System.out.println("Juhu");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                registrieren.setForeground(Color.blue);
                registrieren.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                registrieren.setForeground(Color.black);
            }
        });
    }

    private void erstelleUI() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        userLabel = new JLabel("E-Mail");
        add(userLabel);

        userText = new JTextField(20);
        add(userText);

        passLabel = new JLabel("Passwort");
        add(passLabel);

        passText = new JPasswordField(20);
        add(passText);

        loginButton = new JButton("Log in");
        add(loginButton);

        mitarbeiterLogin = new JLabel("bist du ein Mitarbeiter?");
        add(mitarbeiterLogin);

        registrieren = new JLabel("Registrieren");
        add(registrieren);

    }
}
