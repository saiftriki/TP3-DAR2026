package clientPackage;

import sharedPackage.Operation;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client_3_2 {
    static final String IP_SERVEUR = "localhost";
    static final int PORT = 1234;

    // nombre, opérateur (+ - * /), nombre ; espaces facultatifs
    static final Pattern SYNTAXE = Pattern.compile(
            "^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$");

    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté…");
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(IP_SERVEUR), PORT), 5000);
            System.out.println("je suis un client connecté");

            // Out
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            // In
            InputStream is = socket.getInputStream();
            ObjectInputStream oin = new ObjectInputStream(is);

            Scanner sc = new Scanner(System.in);

            System.out.println("Serveur : " + oin.readObject());

            while (true) {
                System.out.print("taper une operation (ex : 55 * 25) ou quit : ");
                String operation = sc.nextLine().trim();
                if (operation.equals("quit")) {
                    break;
                }
                Matcher m = SYNTAXE.matcher(operation);
                if (!m.matches()) {
                    System.out.println("Syntaxe invalide !");
                    continue;
                }
                double op1 = Double.parseDouble(m.group(1));
                String op = m.group(2);
                double op2 = Double.parseDouble(m.group(3));

                oos.writeObject(new Operation(op1, op, op2));
                oos.flush();

                double res = oin.readDouble();
                System.out.println("Résultat : " + res);
            }

            oos.writeObject("quit");
            oos.flush();
            sc.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}
