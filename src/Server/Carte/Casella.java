package src.Server.Carte;

/**
 * Rappresenta una casella del gioco Monopoly.
 * 
 * Estende la classe Carta.
 */
public class Casella extends Carta {
    private int gruppo;
    private int rendita[];
    private int prezzoCasa;
    private int ipoteca;
    private boolean ipotecata;
    private int numCase;
    private boolean albergo;

    /**
     * Crea un oggetto Casella con i parametri specificati.
     * 
     * @param ID         L'ID della casella
     * @param nome       Il nome della casella
     * @param prezzo     Il prezzo della casella
     * @param gruppo     Il gruppo della casella
     * @param rendita    L'array contenente i valori della rendita della casella
     * @param prezzoCasa Il prezzo di costruzione di una casa sulla casella
     * @param ipoteca    L'ipoteca della casella
     */
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

    /**
     * Controlla se la casella è ipotecata.
     * 
     * @return true se la casella è ipotecata, false altrimenti
     */
    @Override
    public boolean isIpotecata() {
        return ipotecata;
    }

    /**
     * Imposta lo stato ipotecato della casella.
     * 
     * @param ipotecata true per ipotecare la casella, false per rimuovere l'ipoteca
     */
    @Override
    public void setIpotecata(boolean ipotecata) {
        this.ipotecata = ipotecata;
    }

    /**
     * Prendi il valore ipotecario della casella.
     * 
     * @return il valore ipotecario della casella
     */
    @Override
    public int getValIpoteca() {
        return ipoteca;
    }

    /**
     * Ritorna il valore della casa.
     *
     * @return il valore della casa
     */
    public int getValCasa() {
        return prezzoCasa;
    }

    /**
     * Prendi il valore del pedaggio della casella.
     * 
     * @return il valore del pedaggio della casella
     */
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
