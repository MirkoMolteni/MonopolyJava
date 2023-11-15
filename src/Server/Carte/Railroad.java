package src.Server.Carte;

public class Railroad extends Carta {
    int rendita[];
    int ipoteca;
    boolean ipotecata;

    public Railroad(String ID, String nome, int prezzo, int rendita[], int ipoteca) {
        super(ID, nome, prezzo);
        this.rendita = rendita;
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
