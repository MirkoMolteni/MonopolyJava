package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class netUtil {
    private static netUtil instance;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String latestError;

    private netUtil() {}

    // implementazione singleton
    public static netUtil getInstance() {
        if (instance == null) {
            instance = new netUtil();
        }
        return instance;
    }

    // creo connessione al server e inizializzo i buffer
    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Errore nella connessione al server: " + e.getMessage());
            latestError = e.getMessage();
        }
    }

    // invio e ricevo messaggi
    public void send(String message) {
        if(out == null) {
            System.out.println("Errore: connessione non stabilita!");
            return;
        }else
            out.println(message);
        
    }

    public String receive() throws IOException {
        if(in == null) {
            return "";
        }else
            return in.readLine();
    }

    // ottengo lo status della connessione
    public boolean getStatus() {
        if(this.socket != null)
            return true;
        else
            return false;
    }

    // ottengo l'ultimo messaggio di errore
    public String getLastError() {
        return latestError;
    }

    // chiudo la connessione
    public void close() throws IOException {
        socket.close();
    }
}
