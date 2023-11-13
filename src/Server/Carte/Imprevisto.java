package src.Server.Carte;

public class Imprevisto extends Carta {
    int caso;
    String value;

    public Imprevisto(String ID, String nome, int prezzo, int caso, String value) {
        super(ID, nome, prezzo);
        this.caso = caso;
        this.value = value;
    }
}
