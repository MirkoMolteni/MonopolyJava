package src.Server.Carte;

/**
 * Rappresenta una carta Societa nel gioco Monopoly.
 * Estende la classe Carta.
 */
public class Societa extends Carta {
    int moltiplicatori[];
    int ipoteca;
    boolean ipotecata;

    /**
     * Crea un oggetto Societa con l'ID, il nome, il prezzo, i moltiplicatori e il
     * valore ipotecario specificati.
     * 
     * @param ID             L'ID della carta
     * @param nome           Il nome della carta
     * @param prezzo         Il prezzo della carta
     * @param moltiplicatori L'array contenente i moltiplicatori della carta
     * @param ipoteca        L'ipoteca della carta
     */
    public Societa(String ID, String nome, int prezzo, int moltiplicatori[], int ipoteca) {
        super(ID, nome, prezzo);
        this.moltiplicatori = moltiplicatori;
        this.ipoteca = ipoteca;
        this.ipotecata = false;
    }

    /**
     * Controlla se la carta Societa è ipotecata.
     * 
     * @return true se la carta è ipotecata, false altrimenti
     */
    @Override
    public boolean isIpotecata() {
        return ipotecata;
    }

    /**
     * Imposta lo stato ipotecato della carta Societa.
     * 
     * @param ipotecata true per ipotecare la carta, false per rimuovere l'ipoteca
     */
    @Override
    public void setIpotecata(boolean ipotecata) {
        this.ipotecata = ipotecata;
    }

    /**
     * Prendi il valore ipotecario della carta Societa.
     * 
     * @return il valore ipotecario della carta
     */
    @Override
    public int getValIpoteca() {
        return ipoteca;
    }
}
