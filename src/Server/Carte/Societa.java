package src.Server.Carte;

public class Societa extends Carta {
    int moltiplicatori[];
    int ipoteca;

    public Societa(String ID, String nome, int prezzo, int moltiplicatori[], int ipoteca) {
        super(ID, nome, prezzo);
        this.moltiplicatori = moltiplicatori;
        this.ipoteca = ipoteca;
    }
}
