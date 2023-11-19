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
        }
    }

    // invio e ricevo messaggi
    public void send(String message) {
        out.println(message);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    // chiudo la connessione
    public void close() throws IOException {
        socket.close();
    }
}
