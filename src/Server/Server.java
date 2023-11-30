package src.Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
// import java.util.Scanner;

/**
 * La classe Server rappresenta un server che ascolta le connessioni in entrata
 * e gestisce le richieste dei client.
 */
public class Server {
    ServerSocket serverSocket;
    ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    /**
     * Crea un nuovo oggetto Server che ascolta sulla porta specificata.
     * 
     * @param port il numero di porta su cui ascoltare
     * @throws IOException se si verifica un errore I/O durante la creazione del
     *                     socket del server
     */
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server in ascolto sulla porta " + port);
    }

    /**
     * Fai partire il server e gestisci le connessioni dei client.
     * 
     * @throws Exception se si verifica un errore durante l'avvio del server o la
     *                   comunicazione con il client.
     */
    public void start() throws Exception {
        Partita p = new Partita();
        while (true && Settings.GAME_STATUS != 1) {
            if (clients.size() < Settings.MAX_PLAYERS) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connessione stabilita con " +
                        clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                ClientHandler client = new ClientHandler(clientSocket, p, this);
                // ClientHandler client = new ClientHandler(null, p, this);
                clients.add(client);
                client.start();
            }
        }
    }

    public void notifyAllClients(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}