package src.Server;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Partita p;
    private Server s;

    public ClientHandler(Socket socket, Partita p, Server s) throws IOException {
        this.clientSocket = socket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.p = p;
        this.s = s;
    }

    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Message received: " + inputLine);
                String campi[] = inputLine.split(";");
                String risposta = "";
                switch (campi[0]) {
                    case "1":
                        // aggiungo un giocatore alla partita
                        risposta = p.addGiocatore(new Player(campi[1], Integer.parseInt(campi[2])));
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
                s.notifyAllClients(risposta);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Connessione con " + clientSocket.getInetAddress() +
                    "terminata.");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void sendMessage(String message) {
        System.out.println(
                "Message sent to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " : " + message);
        System.out.println("-------------------------");
        // invio risposta
        out.println(message);
    }
}