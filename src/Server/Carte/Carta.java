package src.Server.Carte;

public class Carta {
    private String ID;
    private String nome;
    private int prezzo;
    private String propietario;

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

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public Carta(String ID, String nome, int prezzo) {
        this.ID = ID;
        this.nome = nome;
        this.prezzo = prezzo;
        this.propietario = "";
    }
}
