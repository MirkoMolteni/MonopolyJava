package src.client;

public class player {
    // struttura: InfoPlayer = ID:nome:soldi:posizione:[proprietà1,proprietà2]:[uscitePrigione]
    String ID;
    String nome;
    int saldo;
    int posizione;
    String[] proprieta;
    String[] cartePrigione;

    public void setCartePrigione(String[] cartePrigione) {
        this.cartePrigione = cartePrigione;
    }

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

    // getters e setters
    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public String[] getProprieta() {
        return proprieta;
    }

    public void setProprieta(String[] proprieta) {
        this.proprieta = proprieta;
    }

    public String[] getCartePrigione() {
        return cartePrigione;
    }
}
