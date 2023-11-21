package src.Server.Carte;

/**
 * La classe Imprevisto rappresenta una carta "Imprevisto" nel gioco Monopoly.
 * Estende la classe Carta e contiene proprietà e metodi aggiuntivi specifici
 * per la carta "Imprevisto".
 */
public class Imprevisto extends Carta {
    int caso;
    String value;

    /**
     * Crea un nuovo oggetto Imprevisto con l'ID, il nome, il prezzo, il
     * numero di caso e il valore specificati.
     *
     * @param ID     L'ID della carta
     * @param nome   Il nome della carta
     * @param prezzo Il prezzo della carta
     * @param caso   Il numero di caso della carta
     * @param value  Il valore della carta
     */
    public Imprevisto(String ID, String nome, int prezzo, int caso, String value) {
        super(ID, nome, prezzo);
        this.caso = caso;
        this.value = value;
    }

    /**
     * Prendi il numero di caso della carta Imprevisto.
     * 
     * @return il numero di caso
     */
    public int getCaso() {
        return caso;
    }

    /**
     * Prendi il valore della carta Imprevisto.
     *
     * @return il valore
     */
    public String getValue() {
        return value;
    }
}
