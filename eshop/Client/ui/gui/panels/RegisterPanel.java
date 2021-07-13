package eshop.Client.ui.gui.panels;
import eshop.Client.net.EshopFassade;
import eshop.interfaces.EshopInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JFrame{


    private EshopInterface eshopInterface;
    private LoginPanel.LoginListener listener;

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



    public RegisterPanel(String host, int port) {
        //Titel übergeben
//        super(title);
        eshopInterface = new EshopFassade(host, port);

        try {
            initializeRegister();
        } catch (Exception e) {
            System.out.println("what");
        }
    }

    private void erstelleEreignisse() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameEingabe = nameText.getText();
//                String vornameEingabe = vornameText.getText();
                String emailEingabe = emailText.getText();
                String passEingabe = passText.getText();
                String passBestaetigungEingabe = passBestaetigungText.getText();
                //TODO Passwörter stimmen nicht überein
                String adresseEingabe = adresseText.getText();

                if (passBestaetigungEingabe.equals(passEingabe)) {
                    try {
                        eshopInterface.registerCustomer(nameEingabe, emailEingabe, passEingabe, adresseEingabe);
                        succ.setText("Erfolgreich Registriert! Bitte loggen Sie sich erneut ein.");
                        //TODO Züruck zum Login Fenster um sich wieder einzuloggen
                    } catch (Exception ex) {
                        System.out.println("oops");
                    }
                } else {
                    succ.setText("Passwort stimmt nicht überein");
                }
            }
        });
    }

    public void initializeRegister() {

        setLayout(new BorderLayout());

        frame = new JFrame();
        setLocationRelativeTo(null);

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

        //Distance
//        Establish padding/margin like distances to be used between components
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferredSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        loginPanel.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

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

        adresseText = new JPasswordField(20);
        loginPanel.add(adresseText);

        registerButton = new JButton("Log in");
        loginPanel.add(registerButton);

        accountButton = new JButton("Account schon vorhanden");
        loginPanel.add(accountButton);
        //TODO

        succ = new JLabel();
        loginPanel.add(succ);



        add(loginPanel, BorderLayout.CENTER);
        add(whiteSpaceW, BorderLayout.WEST);
        add(whiteSpaceS, BorderLayout.SOUTH);
        add(whiteSpaceE, BorderLayout.EAST);


        this.setSize(500, 400);
        this.setVisible(true);



    }

    public void initializeRegister2() {
        frame.setLayout(new BoxLayout(frame, BoxLayout.PAGE_AXIS));
        setLayout(new GridLayout(8,8,30,10));

//        setLayout(new BorderLayout());

        frame = new JFrame();

        JPanel loginPanel = new JPanel();
        JPanel whiteSpaceW = new JPanel();
        JPanel whiteSpaceN = new JPanel();
        JPanel whiteSpaceS = new JPanel();
        JPanel whiteSpaceE = new JPanel();
        JPanel whitespace = new JPanel();
        JLabel succ;

        whiteSpaceN.setPreferredSize(new Dimension(500, 20));
        whiteSpaceW.setPreferredSize(new Dimension(10,300));
        whiteSpaceS.setPreferredSize(new Dimension(500,35));
        whiteSpaceE.setPreferredSize(new Dimension(200,20));

        //Distance
//        Establish padding/margin like distances to be used between components
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferredSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        loginPanel.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

//        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
//        loginPanel.setAlignmentY(0);

        frame.add(loginPanel);

        header = new JLabel("Neuen Account erstellen", JLabel.LEFT);
        header.setVerticalAlignment(JLabel.TOP);
        header.setFont(new Font("Arial", Font.HANGING_BASELINE, 15));
//        loginPanel.add(header);

        nameLabel = new JLabel("Name");
//        userLabel.setBounds(10,20,80,25);
//        loginPanel.add(nameLabel);

        nameText = new JTextField(20);
//        userText.setBounds(100,20,170,25);
//        loginPanel.add(nameText);
//
//        vornameLabel = new JLabel("Nachname");
////        passLabel.setBounds(10,50,80,25);
//        loginPanel.add(vornameLabel);
//
//        vornameText = new JPasswordField(20);
////        passText.setBounds(100,50,170,25);
//        loginPanel.add(vornameText);

        emailLabel = new JLabel("E-Mail");
//        userLabel.setBounds(10,20,80,25);
//        loginPanel.add(emailLabel);

        emailText = new JTextField(20);
//        userText.setBounds(100,20,170,25);
//        loginPanel.add(emailText);

        passLabel = new JLabel("Passwort");
//        passLabel.setBounds(10,50,80,25);
//        loginPanel.add(passLabel);

        passInfoLabel1 = new JLabel("-mindestens 8 Zeichen");
        passInfoLabel1.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
//        loginPanel.add(passInfoLabel1);

        passInfoLabel2 = new JLabel("-mindestens 1 Großbuchstabe");
        passInfoLabel2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
//        loginPanel.add(passInfoLabel2);

        passInfoLabel3 = new JLabel("-mindestens 1 Kleinbuchstabe");
        passInfoLabel3.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
//        loginPanel.add(passInfoLabel3);

        passInfoLabel4 = new JLabel("-mindestens 1 Zahl");
        passInfoLabel4.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
//        loginPanel.add(passInfoLabel4);

        passInfoLabelFull = new JLabel();
        passInfoLabel4.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));
        passInfoLabelFull.setText("-mindestens 8 Zeichen \n -mindestens 1 Großbuchstabe \n -mindestens 1 Kleinbuchstabe \n -mindestens 1 Kleinbuchstabe \n -mindestens 1 Zahl");


        passText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
//        loginPanel.add(passText);

        passBestaetigungLabel = new JLabel("Passwort bestätigen");
//        passLabel.setBounds(10,50,80,25);
//        loginPanel.add(passBestaetigungLabel);

        passBestaetigungText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
//        loginPanel.add(passBestaetigungText);

        adresseLabel = new JLabel("Adresse");
//        passLabel.setBounds(10,50,80,25);
//        loginPanel.add(adresseLabel);

        adresseText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
//        loginPanel.add(adresseText);

        registerButton = new JButton("Registrieren");
//        loginButton.setBounds(200,80,70,25);
//        loginPanel.add(registerButton);

        accountButton = new JButton("Account schon vorhanden");
//        loginButton.setBounds(200,80,70,25);
//        loginPanel.add(accountButton);
        //TODO Account button > Anderes UI (Register > Login)

        succ = new JLabel();
//        succ.setBounds(10,80,300,25);
//        loginPanel.add(succ);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(50,30));
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(100,30));

        loginPanel.add(leftPanel);
        loginPanel.add(nameLabel);
        loginPanel.add(nameText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(emailLabel);
        loginPanel.add(emailText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(passLabel);
        loginPanel.add(passText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(passInfoLabelFull);
        loginPanel.add(rightPanel);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(passBestaetigungLabel);
        loginPanel.add(passBestaetigungText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(adresseLabel);
        loginPanel.add(adresseText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(accountButton);
        loginPanel.add(registerButton);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(rightPanel);
        loginPanel.add(succ);

//
//        add(loginPanel, BorderLayout.CENTER);
//        add(whiteSpaceW, BorderLayout.WEST);
////        add(whiteSpaceN, BorderLayout.NORTH);
//        add(whiteSpaceS, BorderLayout.SOUTH);
//        add(whiteSpaceE, BorderLayout.EAST);


        this.setSize(500, 400);
        this.setVisible(true);



    }

    public void initializeRegister3() {

        setLayout(new GridLayout(8,8,30,10));

//        setLayout(new BorderLayout());

        frame = new JFrame();

        JPanel loginPanel = new JPanel();
        JPanel whiteSpaceW = new JPanel();
        JPanel whiteSpaceN = new JPanel();
        JPanel whiteSpaceS = new JPanel();
        JPanel whiteSpaceE = new JPanel();
        JPanel whitespace = new JPanel();
        JLabel succ;

        whiteSpaceN.setPreferredSize(new Dimension(500, 20));
        whiteSpaceW.setPreferredSize(new Dimension(10,300));
        whiteSpaceS.setPreferredSize(new Dimension(500,35));
        whiteSpaceE.setPreferredSize(new Dimension(200,20));

        //Distance
//        Establish padding/margin like distances to be used between components
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferredSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        loginPanel.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
        loginPanel.setAlignmentY(0);

        frame.add(loginPanel);

        header = new JLabel("Neuen Account erstellen", JLabel.LEFT);
        header.setVerticalAlignment(JLabel.TOP);
        header.setFont(new Font("Arial", Font.ITALIC, 15));
        loginPanel.add(header);

        nameLabel = new JLabel("Name");
//        userLabel.setBounds(10,20,80,25);
        loginPanel.add(nameLabel);

        nameText = new JTextField(20);
//        userText.setBounds(100,20,170,25);
        loginPanel.add(nameText);
//
//        vornameLabel = new JLabel("Nachname");
////        passLabel.setBounds(10,50,80,25);
//        loginPanel.add(vornameLabel);
//
//        vornameText = new JPasswordField(20);
////        passText.setBounds(100,50,170,25);
//        loginPanel.add(vornameText);

        emailLabel = new JLabel("E-Mail");
//        userLabel.setBounds(10,20,80,25);
        loginPanel.add(emailLabel);

        emailText = new JTextField(20);
//        userText.setBounds(100,20,170,25);
        loginPanel.add(emailText);

        passLabel = new JLabel("Passwort (mind. 8 Zeichen,");
//        passLabel.setBounds(10,50,80,25);
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

        passInfoLabelFull = new JLabel();
        passInfoLabel4.setFont(new Font("Arial", Font.TRUETYPE_FONT, 10));



        passText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
        loginPanel.add(passText);

        passBestaetigungLabel = new JLabel("Passwort bestätigen");
//        passLabel.setBounds(10,50,80,25);
        loginPanel.add(passBestaetigungLabel);

        passBestaetigungText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
        loginPanel.add(passBestaetigungText);

        adresseLabel = new JLabel("Adresse");
//        passLabel.setBounds(10,50,80,25);
        loginPanel.add(adresseLabel);

        adresseText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
        loginPanel.add(adresseText);

        registerButton = new JButton("Registrieren");
//        loginButton.setBounds(200,80,70,25);
        loginPanel.add(registerButton);

        accountButton = new JButton("Account schon vorhanden");
//        loginButton.setBounds(200,80,70,25);
        loginPanel.add(accountButton);
        //TODO Account button > Anderes UI (Register > Login)

        succ = new JLabel();
//        succ.setBounds(10,80,300,25);
        loginPanel.add(succ);

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(50,30));
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(100,30));

        loginPanel.add(leftPanel);
        loginPanel.add(nameLabel);
        loginPanel.add(nameText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(emailLabel);
        loginPanel.add(emailText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(passLabel);
        loginPanel.add(passText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(passInfoLabel1);
        loginPanel.add(rightPanel);
        loginPanel.add(rightPanel);

        loginPanel.add(header);
        loginPanel.add(rightPanel);
        loginPanel.add(rightPanel);
        loginPanel.add(rightPanel);

        loginPanel.add(new JPanel());
        loginPanel.add(passLabel);
        loginPanel.add(passText);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(accountButton);
        loginPanel.add(rightPanel);
        loginPanel.add(rightPanel);

        loginPanel.add(leftPanel);
        loginPanel.add(accountButton);
        loginPanel.add(registerButton);

//
//        add(loginPanel, BorderLayout.CENTER);
//        add(whiteSpaceW, BorderLayout.WEST);
////        add(whiteSpaceN, BorderLayout.NORTH);
//        add(whiteSpaceS, BorderLayout.SOUTH);
//        add(whiteSpaceE, BorderLayout.EAST);


        this.setSize(500, 400);
        this.setVisible(true);



    }


}
