package src.Server;

/**
 * La classe MainJ è il punto di ingresso dell'applicazione server di Monopoly.
 * Avvia il server sulla porta 8080 e gestisce eventuali eccezioni che si
 * verificano durante l'avvio.
 */
public class MainJ {
    public static void main(String[] args) throws Exception {
        try {
            Server server = new Server(8080);
            server.start();
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}
