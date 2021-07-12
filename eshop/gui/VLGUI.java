package eshop.gui;
import eshop.Client.net.EshopFassade;
import eshop.Eshop;
import eshop.exceptions.LoginFehlgeschlagen;
import eshop.interfaces.EshopInterface;
import eshop.valueobjects.Kunden;
import eshop.valueobjects.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO DEUTSCH

public class VLGUI extends JFrame implements ActionListener {

    private EshopInterface eshopInterface;

    private JLabel userLabel;
    private JTextField userText;
    private JLabel passLabel;
    private JPasswordField passText;
    private JButton loginButton;
    private JLabel succ;
    private JFrame frame;

    public VLGUI(String title, String host, int port) {
        //Titel übergeben
        super(title);
        eshopInterface = new EshopFassade(host, port);

        try {
            initializeLogin();
        } catch (Exception e) {
            System.out.println("what");
        }


        //Fenster schließen und Programm beenden wenn ALT+F4
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public void initializeLogin() {

        frame = new JFrame();

        JPanel loginPanel = new JPanel();
        JPanel whiteSpace = new JPanel();
        JLabel succ;

        //Distance
//        Establish padding/margin like distances to be used between components
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferredSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        loginPanel.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
        loginPanel.setAlignmentY(0);

        frame.add(loginPanel);

        userLabel = new JLabel("E-Mail");
//        userLabel.setBounds(10,20,80,25);
        loginPanel.add(userLabel);

        userText = new JTextField(20);
//        userText.setBounds(100,20,170,25);
        loginPanel.add(userText);

        passLabel = new JLabel("Passwort");
//        passLabel.setBounds(10,50,80,25);
        loginPanel.add(passLabel);

        passText = new JPasswordField(20);
//        passText.setBounds(100,50,170,25);
        loginPanel.add(passText);

        loginButton = new JButton("Log in");
//        loginButton.setBounds(200,80,70,25);
        loginButton.addActionListener(this);
        loginPanel.add(loginButton);

        succ = new JLabel();
//        succ.setBounds(10,80,300,25);
        loginPanel.add(succ);

        add(loginPanel, BorderLayout.WEST);
        add(whiteSpace, BorderLayout.CENTER);

        this.setSize(500, 300);
        this.setVisible(true);


    }

    public void initialize1() {

        setLayout(new BorderLayout());

        JPanel suchPanel = new JPanel();

        //FIRST: SEARCHBAR, NORTH
        //FlowLayout = Horizontal layout, one component after another from left to right
        suchPanel.setLayout(new FlowLayout());

        //FlowLayout, first component = Searchbar Label (far-left)
        suchPanel.add(new JLabel("Suchbegriff:"));

        //FlowLayout, second component initialized in method
        JTextField suchTextFeld = new JTextField();

        //Set searchbar's size
        suchTextFeld.setPreferredSize((new Dimension(200, 30)));

        //Flowlayout, second component implemented (center)
        suchPanel.add(suchTextFeld);

        //FlowLayout, third component = Search initiate button (right)
        suchPanel.add(new JButton("Suchen!"));

        //------------------------

        JPanel addPanel = new JPanel();

        //SECOND: LEFT PANE, WEST
        //BoxLayout = Vertical layout, one component after another from top to bottom
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));

        //Implement text input fields after their corresponding labels
        addPanel.add(new JLabel("Nummer: "));
        addPanel.add(new JTextField());
        addPanel.add(new JLabel("Artikel: "));
        addPanel.add(new JTextField());

        //Distance
        //Establish padding/margin like distances to be used between components
        Dimension fillerMinSize = new Dimension(5, 20);
        Dimension fillerPreferredSize = new Dimension(5, Short.MAX_VALUE);
        Dimension fillerMaxSize = new Dimension(5, Short.MAX_VALUE);
        addPanel.add(new Box.Filler(fillerMinSize, fillerPreferredSize, fillerMaxSize));

        addPanel.add(new JButton("Hinzufügen"));

        //-------------------------

        //CENTER

        JList artikelListe = new JList();
        java.util.List<Person> personList;

        //-------PANEL ASSEMBLY

        add(suchPanel, BorderLayout.NORTH);
        add(addPanel, BorderLayout.WEST);

        setSize(600, 400);
        setVisible(true);
    }

    public void initialize2() {


        setSize(600, 400);
        setVisible(true);

    }

    public static void main(String[] args) {
        VLGUI gui = new VLGUI("ASIA SHOP", "localhost", 6789);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String nutzerEingabe = userText.getText();
        String passEingabe = passText.getText();

        try {
            Kunden kunde;
            kunde = eshopInterface.logInCustomer(nutzerEingabe, passEingabe);
            System.out.println(kunde.getName());
        } catch (LoginFehlgeschlagen loginFehlgeschlagen) {
            loginFehlgeschlagen.printStackTrace();
        }
    }
}
