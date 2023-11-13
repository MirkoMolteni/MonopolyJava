package src.Server;

import java.io.*;
import java.net.*;

public class Server {
    ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server avviato. In attesa di connessioni...");
    }

    public void start() throws Exception {
        Partita p = new Partita();
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
            System.out.println("Connessione con " + clientSocket.getInetAddress() + " terminata.");
            in.close();
            out.close();
            clientSocket.close();
        }
    }
}