package src.Server.Carte;

public class Probabilita extends Carta {
    int caso;
    String value;

    public Probabilita(String ID, String nome, int prezzo, int caso, String value) {
        super(ID, nome, prezzo);
        this.caso = caso;
        this.value = value;
    }
}
