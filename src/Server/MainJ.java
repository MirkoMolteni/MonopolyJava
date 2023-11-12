package src.Server;

public class MainJ {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            server.start();
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}
