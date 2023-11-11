package src.Server;

import java.io.*;
import java.net.*;

/**
 * main
 */
public class main {

    public class Main {
        public static void main(String[] args) {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                System.out.println("Server avviato. In attesa di connessioni...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connessione stabilita con " + clientSocket.getInetAddress());

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Messaggio ricevuto: " + inputLine);
                        out.println("Hai detto: " + inputLine);
                    }

                    in.close();
                    out.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }
}