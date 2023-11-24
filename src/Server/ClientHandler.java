package src.Server;

import java.io.*;
import java.net.*;
import java.util.Scanner;

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
        // TODO: gestire il connection reset
        try {
            String inputLine;
            // Scanner sc = new Scanner(System.in);

            // while ((inputLine = sc.nextLine()) != null) {
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Message received: " + inputLine);
                String campi[] = inputLine.split(";");
                String risposta = "";
                switch (campi[0]) {
                    case "ADD":
                        // aggiungo un giocatore alla partita
                        risposta = p.addGiocatore(new Player(campi[1], Integer.parseInt(campi[2])));
                        break;
                    case "START":
                        // inizio partita
                        risposta = p.startGame();
                        break;
                    case "ROLL":
                        // lancio i dadi
                        risposta = p.rollDiceAndMove();
                        break;
                    case "BUY":
                        // acquista casella
                        risposta = p.buyCasella();
                        break;
                    case "CH":
                        // termina il turno
                        risposta = p.changeTurn();
                        break;
                    case "IP":
                        // ipoteca una casella
                        risposta = p.ipotecaCasella(campi[1]);
                        break;
                    case "LST":
                        // lista dei giocatori
                        risposta = p.getListaGiocatori();
                        break;
                    case "INFC":
                        // informazioni casella
                        risposta = p.getInfoCasella(campi[1]);
                        break;
                    default:
                        risposta = "Comando non riconosciuto";
                        break;
                }
                s.notifyAllClients(risposta);
                // sendMessage(risposta);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        // invio risposta
        out.println(message);
        System.out.println(
                "Message sent to " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " : " + message);
        // System.out.println("Message sent : " + message);
        System.out.println("-------------------------");
    }
}