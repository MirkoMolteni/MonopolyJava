package src.Server.Carte;

/**
 * La classe Probabilita rappresenta una carta probabilità nel gioco Monopoly.
 * Estende la classe Carta e contiene proprietà aggiuntive per il caso e il
 * valore della carta.
 */
public class Probabilita extends Carta {
    private int caso;
    private String value;

    /**
     * Crea un nuovo oggetto Probabilita con l'ID, il nome, il prezzo, il
     * numero di caso e il valore specificati.
     *
     * @param ID     L'ID della carta
     * @param nome   Il nome della carta
     * @param prezzo Il prezzo della carta
     * @param caso   Il numero di caso della carta
     * @param value  Il valore della carta
     */
    public Probabilita(String ID, String nome, int prezzo, int caso, String value) {
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
