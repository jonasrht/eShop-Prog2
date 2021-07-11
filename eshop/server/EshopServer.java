package eshop.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import eshop.interfaces.EshopInterface;
import eshop.verwaltung.ShopVerwaltung;
/**
 * Klasse des Eshop Servers.Kommunikation findet zwischen Server und Client sattt.
 * Socket ist eine bidirektionale Netzwerk-Kommunikationsschnittstelle, deren Verwaltung das Betriebssystem uebernimmt.
 * Die Kommunikation findet zwischen einem Server und einem Client ueber einen definierten Port statt.
 *
 * @author Jonas, Jana, Dabina
 * - Import aus der Java Bibliotheck
 */
public class EshopServer {
    public final static int DEFAULT_PORT = 6789;

    private int port;
    private ServerSocket serverSocket;
    private EshopInterface eShop;

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
