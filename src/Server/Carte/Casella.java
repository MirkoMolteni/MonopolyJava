package src.Server.Carte;

public class Casella extends Carta {
    int gruppo;
    int rendita[];
    int prezzoCasa;
    int ipoteca;
    boolean ipotecata;

    public Casella(String ID, String nome, int prezzo, int gruppo, int rendita[], int prezzoCasa, int ipoteca) {
        super(ID, nome, prezzo);
        this.gruppo = gruppo;
        this.rendita = rendita;
        this.prezzoCasa = prezzoCasa;
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
