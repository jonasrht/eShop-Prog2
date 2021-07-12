package eshop.Client.ui.gui.panels;

import eshop.Client.ui.gui.Gui;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Kunden;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JLabel succ;

    public LoginPanel(EshopInterface eshopInterface, LoginListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
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
                    Gui.errorBox(e1.getMessage());
                }
            }
        });
    }

    private void erstelleUI() {
        JPanel whiteSpace = new JPanel();

        //Distance
//        Establish padding/margin like distances to be used between components
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferredSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setAlignmentY(0);

        userLabel = new JLabel("E-Mail");
//        userLabel.setBounds(10,20,80,25);
        add(userLabel);

        userText = new JTextField(20);
//        userText.setBounds(100,20,170,25);
        add(userText);

        passLabel = new JLabel("Passwort");
//        passLabel.setBounds(10,50,80,25);
        add(passLabel);

        passText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
        add(passText);

        loginButton = new JButton("Log in");
//        loginButton.setBounds(200,80,70,25);
        add(loginButton);

        succ = new JLabel();
//        succ.setBounds(10,80,300,25);
        add(succ);
    }
}
