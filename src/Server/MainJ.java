package src.Server;

import java.util.ArrayList;

public class MainJ {
    public static void main(String[] args) throws Exception {
        // try {
        // Server server = new Server(8080);
        // server.start();
        // } catch (Exception e) {
        // System.out.println("Errore: " + e.getMessage());
        // }
        Parser.parseCarteXml(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
