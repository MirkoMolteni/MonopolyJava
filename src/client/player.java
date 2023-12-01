package src.client;

public class player {
    // struttura: InfoPlayer = ID:nome:soldi:posizione:[proprietà1,proprietà2]:[uscitePrigione]
    String ID;
    String nome;
    int saldo;
    int posizione;
    String[] proprieta;
    String[] cartePrigione;

    public player(String ID, String nome, int saldo, int posizione, String[] proprieta, String[] cartePrigione) {
        this.ID = ID;
        this.nome = nome;
        this.saldo = saldo;
        this.posizione = posizione;
        this.proprieta = proprieta;
        this.cartePrigione = cartePrigione;
    }

    public void updatePlayer(String ID, String nome, int saldo, int posizione, String[] proprieta, String[] cartePrigione) {
        this.ID = ID;
        this.nome = nome;
        this.saldo = saldo;
        this.posizione = posizione;
        this.proprieta = proprieta;
        this.cartePrigione = cartePrigione;
    }
}
