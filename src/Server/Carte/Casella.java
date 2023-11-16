package src.Server.Carte;

public class Casella extends Carta {
    int gruppo;
    int rendita[];
    int prezzoCasa;
    int ipoteca;
    boolean ipotecata;
    int numCase;
    boolean albergo;

    public Casella(String ID, String nome, int prezzo, int gruppo, int rendita[], int prezzoCasa, int ipoteca) {
        super(ID, nome, prezzo);
        this.gruppo = gruppo;
        this.rendita = rendita;
        this.prezzoCasa = prezzoCasa;
        this.ipoteca = ipoteca;
        this.ipotecata = false;
        this.numCase = 0;
        this.albergo = false;
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

    @Override
    public int getPedaggio() {
        if (ipotecata) {
            return 0;
        } else if (albergo) {
            return rendita[5];
        } else {
            return rendita[numCase];
        }
    }
}
