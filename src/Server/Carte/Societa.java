package src.Server.Carte;

public class Societa extends Carta {
    int moltiplicatori[];
    int ipoteca;
    boolean ipotecata;

    public Societa(String ID, String nome, int prezzo, int moltiplicatori[], int ipoteca) {
        super(ID, nome, prezzo);
        this.moltiplicatori = moltiplicatori;
        this.ipoteca = ipoteca;
        this.ipotecata = false;
    }

    @Override
    public boolean isIpotecata() {
        return ipotecata;
    }

    @Override
    public void setIpotecata(boolean ipotecata) {
        this.ipotecata = ipotecata;
    }

    @Override
    public int getValIpoteca() {
        return ipoteca;
    }
}
