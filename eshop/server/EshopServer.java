package eshop.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import eshop.interfaces.EshopInterface;
import eshop.verwaltung.ShopVerwaltung;
/**
 * Klasse des Eshop Servers.Server stellt das ServerSocket Objekt bereit, die sich mit anderen Clients verbinden koennen.Der Client hat Computerprogramme, die ablaufen sollen und der Server besitzt die Informationen und Ressourcen, welche die Programme benoetigen, um zu funktionieren.Der Client moechte einen Prozess durchfuehren und benoetigt dafuer Informationen und Ressourcen, die auf einem mit ihm verbundenen Server liegen.Also sendet er eine Anfrage (Request) an den Server und fordert diese an.Der Server verarbeitet alle Anfragen, die bei ihm eingehen, und stellt die Elemente bereit (Response), die von ihm gefordert werden.Dabei ist es dem Server ueberlassen, in welcher Reihenfolge er die Anfragen abarbeitet.
 *
 * @author Jonas, Jana, Dabina
 * - Import aus der Java Bibliotheck
 */
public class EshopServer {
    // Attribute
    public final static int DEFAULT_PORT = 6789;

    private int port;
    private ServerSocket serverSocket;
    private EshopInterface eShop;
    /**
     * Konstruktor
     *
     * @param optPort  default port vom Client
     */
    public EshopServer(int optPort) {

        this.port = (optPort == 0) ? DEFAULT_PORT : optPort;

        try {
            serverSocket = new ServerSocket(port);

            InetAddress ia = InetAddress.getLocalHost();
            System.out.println("Host: " + ia.getHostName());
            System.out.println("Server *" + ia.getHostAddress() + "* lauscht auf Port " + port);
        } catch (IOException e) {
            System.err.println("Eine Ausnahme trat beim Anlegen des Server-Sockets auf: " + e);
            System.exit(1);
        }

        try {
            eShop = new ShopVerwaltung();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Methode zum Akzeptieren der Anfrage auf Verbindung des Clients.
     */
    public void acceptClientConnectRequests() {

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientEShopRequestProcessor c = new ClientEShopRequestProcessor(clientSocket, eShop);
                Thread thread = new Thread(c);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Fehler waehrend des Wartens auf Verbindungen: " + e);
            System.exit(1);
        }
    }
    /**
     * Main Methode des E-shop Servers.
     */
    public static void main(String[] args) {
        int port = 0;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                port = 0;
            }
        }
        EshopServer server = new EshopServer(port);
        server.acceptClientConnectRequests();
    }

}
