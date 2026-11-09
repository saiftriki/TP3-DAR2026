package serverPackage;

import sharedPackage.Operation;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientProcess_3_2 extends Thread {
    private Socket socket;
    private int numero;

    public ClientProcess_3_2(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {
        try {
            // In
            InputStream is = socket.getInputStream();
            ObjectInputStream oin = new ObjectInputStream(is);

            // Out
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);

            oos.writeObject("Bienvenue, vous êtes le client n°" + numero);
            oos.flush();

            Object inputObject = oin.readObject();
            while (inputObject instanceof Operation) {
                Operation operation = (Operation) inputObject;
                double op1 = operation.getOp1();
                String op = operation.getOp();
                double op2 = operation.getOp2();

                System.out.println("[client n°" + numero + "] operation reçue : " + operation.toString());

                double res = 0;

                try {
                    res = calculer(op1, op, op2);
                } catch (IllegalArgumentException e) {
                    System.out.println("[client n°" + numero + "] erreur de traitement : " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("[client n°" + numero + "] erreur : " + e.getMessage());
                }

                oos.writeDouble(res);
                oos.flush();
                System.out.println("[client n°" + numero + "] resultat envoyé : " + res);

                inputObject = oin.readObject();
            }
            System.out.println("Client n°" + numero + " déconnecté");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur client n°" + numero + " : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    private static double calculer(double op1, String op, double op2) {
        return switch (op) {
            case "+" -> op1 + op2;
            case "-" -> op1 - op2;
            case "*" -> op1 * op2;
            case "/" -> op1 / op2;
            default -> throw new IllegalArgumentException("Choix d'opérateur Invalide ! " + "(" + op + ")");
        };
    }
}
