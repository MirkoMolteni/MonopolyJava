package src.Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server in ascolto sulla porta " + port);
    }

    public void start() throws Exception {
        Partita p = new Partita();
        while (true) {
            // Socket clientSocket = serverSocket.accept();
            // System.out.println("Connessione stabilita con " +
            // clientSocket.getInetAddress());

            // BufferedReader in = new BufferedReader(new
            // InputStreamReader(clientSocket.getInputStream()));
            // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner in = new Scanner(System.in);
            String inputLine;
            while ((inputLine = in.nextLine()) != null) {
                System.out.println("Message received: " + inputLine);
                String campi[] = inputLine.split(";");
                String risposta = "";
                switch (campi[0]) {
                    case "1":
                        // aggiungo un giocatore alla partita
                        risposta = p.addGiocatore(new Player(campi[1]));
                        break;
                    case "2":
                        // inizio partita
                        risposta = p.startGame();
                        break;
                    case "3":
                        // lancio i dadi
                        risposta = p.rollDiceAndMove();
                        break;
                    case "4":
                        // acquista casella
                        risposta = p.buyCasella();
                        break;
                    case "5":
                        // termina il turno
                        risposta = p.changeTurn();
                        break;
                    case "6":
                        // ipoteca una casella
                        risposta = p.ipotecaCasella(campi[1]);
                        break;
                    case "7":
                        // lista dei giocatori
                        risposta = p.getListaGiocatori();
                        break;
                }
                System.out.println("Message sent: " + risposta);
                // invio risposta
                // out.println(risposta);
            }
            // clientSocket.close();
            // System.out.println("Connessione con " + clientSocket.getInetAddress() +
            // "terminata.");
            in.close();
            // out.close();
        }
    }
}