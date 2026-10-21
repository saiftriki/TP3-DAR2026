package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client_3_1 {
    // "localhost" pour les tests locaux ; IP de la machine serveur en réseau (ex : 192.168.1.10)
    static final String IP_SERVEUR = "localhost";
    static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(IP_SERVEUR), PORT), 5000);
            System.out.println("je suis un client connecté");

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            Scanner clavier = new Scanner(System.in);

// Message de bienvenue contenant le numéro d'ordre System.out.println("Serveur : " + br.readLine());

            while (true) {
                System.out.print("Message (quit pour arrêter) : ");
                String message = clavier.nextLine();
                pw.println(message);
                if (message.equals("quit")) {
                    break;
                }
                System.out.println("Serveur : " + br.readLine());
            }
            clavier.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}