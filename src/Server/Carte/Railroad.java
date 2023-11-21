package src.Server.Carte;

/**
 * Rappresenta una carta ferrovia nel gioco Monopoly.
 * Eredita dalla classe Carta.
 */
public class Railroad extends Carta {
    int rendita[];
    int ipoteca;
    boolean ipotecata;

    /**
     * Crea un nuovo oggetto Railroad con i parametri specificati.
     * 
     * @param ID      L'ID della carta ferrovia
     * @param nome    Il nome della carta ferrovia
     * @param prezzo  Il prezzo della carta ferrovia
     * @param rendita Un array contenente i valori della rendita della carta
     *                ferrovia
     * @param ipoteca L'ipoteca della carta ferrovia
     */
    public Railroad(String ID, String nome, int prezzo, int rendita[], int ipoteca) {
        super(ID, nome, prezzo);
        this.rendita = rendita;
        this.ipoteca = ipoteca;
        this.ipotecata = false;
    }

    /**
     * Controlla se la carta ferrovia è ipotecata.
     * 
     * @return true se la carta ferrovia è ipotecata, false altrimenti
     */
    @Override
    public boolean isIpotecata() {
        return ipotecata;
    }

    /**
     * Imposta lo stato ipotecato della carta ferrovia.
     * 
     * @param ipotecata true per ipotecare la carta ferrovia, false per rimuovere
     */
    @Override
    public void setIpotecata(boolean ipotecata) {
        this.ipotecata = ipotecata;
    }

    /**
     * Prendi il valore ipotecario della carta ferrovia.
     * 
     * @return il valore ipotecario della carta ferrovia
     */
    @Override
    public int getValIpoteca() {
        return ipoteca;
    }
}
