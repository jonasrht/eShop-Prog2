package eshop.Client.ui.gui.panels;
import eshop.Client.net.EshopFassade;
import eshop.Client.ui.gui.Gui;
import eshop.interfaces.EshopInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel{

    public interface RegisterKundeListener {
        void registerKundeZurueck();
    }

    private EshopInterface eshopInterface;
    private RegisterKundeListener listener;

    private JLabel header;

    private JLabel nameLabel;
    private JTextField nameText;
    private JLabel emailLabel;
    private JTextField emailText;
    private JLabel passLabel;
    private JLabel passInfoLabel1;
    private JLabel passInfoLabel2;
    private JLabel passInfoLabel3;
    private JLabel passInfoLabel4;
    private JLabel passInfoLabelFull;
    private JPasswordField passText;
    private JLabel passBestaetigungLabel;
    private JPasswordField passBestaetigungText;
    private JLabel adresseLabel;
    private JTextField adresseText;
    private JButton registerButton;
    private JButton accountButton;
    private JLabel succ = new JLabel();

    private JFrame frame;



    public RegisterPanel(EshopInterface eshopInterface, RegisterKundeListener listener) {
        this.eshopInterface = eshopInterface;
        this.listener = listener;

        erstelleUI();
        erstelleEreignisse();
    }

    private void erstelleEreignisse() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                String email = emailText.getText();
                String passwort = passText.getText();
                String passwortCheck = passBestaetigungText.getText();
                String adresse = adresseText.getText();

                if (!name.isEmpty() && !email.isEmpty() && !passwort.isEmpty() && !passwortCheck.isEmpty() && !adresse.isEmpty()) {
                    if (passwort.equals(passwortCheck)) {
                        if (eshopInterface.checkPass(passwort)) {
                            eshopInterface.registerCustomer(name, email, passwort, adresse);
                            listener.registerKundeZurueck();
                            Gui.infoBox("Accunt erfolgreich angelegt.", "Info");
                        } else {
                            Gui.errorBox("Bitte geben Sie min. 8. Zeichen mit min. einen Großbuchstaben und einer Zahl ein.");
                        }
                    } else {
                        Gui.errorBox("Passwörter stimmen nicht überein!");
                    }
                } else {
                    Gui.errorBox("Bitte füllen Sie alle Felder aus.");
                }

                nameText.setText("");
                emailText.setText("");
                passText.setText("");
                passBestaetigungText.setText("");
                adresseText.setText("");
            }
        });

        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.registerKundeZurueck();
            }
        });
    }

    public void erstelleUI() {

        setLayout(new BorderLayout());


        JPanel loginPanel = new JPanel();
        JPanel whiteSpaceW = new JPanel();
        JPanel whiteSpaceN = new JPanel();
        JPanel whiteSpaceS = new JPanel();
        JPanel whiteSpaceE = new JPanel();
        JLabel succ;

        whiteSpaceN.setPreferredSize(new Dimension(500, 20));
        whiteSpaceW.setPreferredSize(new Dimension(10,300));
        whiteSpaceS.setPreferredSize(new Dimension(500,35));
        whiteSpaceE.setPreferredSize(new Dimension(200,20));


        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
        loginPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        header = new JLabel("Neuen Account erstellen", JLabel.LEFT);
        header.setVerticalAlignment(JLabel.TOP);
        header.setFont(new Font("Arial", Font.ITALIC, 15));
        loginPanel.add(header);

        nameLabel = new JLabel("Name");
        loginPanel.add(nameLabel);

        nameText = new JTextField(20);
        loginPanel.add(nameText);

        emailLabel = new JLabel("E-Mail");
        loginPanel.add(emailLabel);

        emailText = new JTextField(20);
        loginPanel.add(emailText);

        passLabel = new JLabel("Passwort");
        loginPanel.add(passLabel);

        passInfoLabel1 = new JLabel("-mindestens 8 Zeichen");
        passInfoLabel1.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
        loginPanel.add(passInfoLabel1);

        passInfoLabel2 = new JLabel("-mindestens 1 Großbuchstabe");
        passInfoLabel2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
        loginPanel.add(passInfoLabel2);

        passInfoLabel3 = new JLabel("-mindestens 1 Kleinbuchstabe");
        passInfoLabel3.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
        loginPanel.add(passInfoLabel3);

        passInfoLabel4 = new JLabel("-mindestens 1 Zahl");
        passInfoLabel4.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
        loginPanel.add(passInfoLabel4);

        passText = new JPasswordField(20);
        loginPanel.add(passText);

        passBestaetigungLabel = new JLabel("Passwort bestätigen");
        loginPanel.add(passBestaetigungLabel);

        passBestaetigungText = new JPasswordField(20);
        loginPanel.add(passBestaetigungText);

        adresseLabel = new JLabel("Adresse");
        loginPanel.add(adresseLabel);

        adresseText = new JTextField(20);
        loginPanel.add(adresseText);

        registerButton = new JButton("Registrieren");
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(registerButton);

        accountButton = new JButton("<- Zurück");
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(accountButton);

        succ = new JLabel();
        loginPanel.add(succ);



        add(loginPanel, BorderLayout.CENTER);
        add(whiteSpaceW, BorderLayout.WEST);
        add(whiteSpaceS, BorderLayout.SOUTH);
        add(whiteSpaceE, BorderLayout.EAST);
        //Distance
//        Establish padding/margin like distances to be used between components
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferredSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        loginPanel.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));
    }
}