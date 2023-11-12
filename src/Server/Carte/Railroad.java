package src.Server.Carte;

public class Railroad extends Carta {
    int rendita[];
    int ipoteca;

    public Railroad(String ID, String nome, int prezzo, int rendita[], int ipoteca) {
        super(ID, nome, prezzo);
        this.rendita = rendita;
        this.ipoteca = ipoteca;
    }
}
