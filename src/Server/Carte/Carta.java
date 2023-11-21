package src.Server.Carte;

/**
 * La superclasse Carta rappresenta una carta del gioco Monopoly.
 * Contiene informazioni riguardanti l'ID, il nome, il prezzo e il proprietario
 * della carta.
 */
public class Carta {
    private String ID;
    private String nome;
    private int prezzo;
    private String propietario;

    /**
     * Prendi l'ID della carta.
     * 
     * @return L'ID della carta.
     */
    public String getID() {
        return ID;
    }

    /**
     * Prendi il nome della carta.
     * 
     * @return Il nome della carta.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Prendi il prezzo della carta.
     * 
     * @return Il prezzo della carta.
     */
    public int getPrezzo() {
        return prezzo;
    }

    /**
     * Prendi il proprietario della carta.
     * 
     * @return Il proprietario della carta.
     */
    public String getPropietario() {
        return propietario;
    }

    /**
     * Imposta il proprietario della carta.
     * 
     * @param propietario Il proprietario della carta.
     */
    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    /**
     * Crea un nuovo oggetto Carta con l'ID, il nome e il prezzo specificati.
     * 
     * @param ID     L'ID della carta.
     * @param nome   Il nome della carta.
     * @param prezzo Il prezzo della carta.
     */
    public Carta(String ID, String nome, int prezzo) {
        this.ID = ID;
        this.nome = nome;
        this.prezzo = prezzo;
        this.propietario = "";
    }

    /**
     * Controlla se la carta è ipotecata.
     * 
     * @return true se la carta è ipotecata, false altrimenti.
     */
    public boolean isIpotecata() {
        return false;
    }

    /**
     * Imposta lo stato ipotecato della carta.
     * 
     * @param ipotecata true se la carta è ipotecata, false altrimenti.
     */
    public void setIpotecata(boolean ipotecata) {
    }

    /**
     * Prendi il valore ipotecario della carta.
     * 
     * @return Il valore ipotecario della carta.
     */
    public int getValIpoteca() {
        return 0;
    }

    /**
     * Prendi il pedaggio della carta.
     * 
     * @return Il pedaggio della carta.
     */
    public int getPedaggio() {
        return 0;
    }
}
