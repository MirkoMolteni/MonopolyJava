package src.Server;

import java.util.ArrayList;

/**
 * La classe Player rappresenta un giocatore nel gioco Monopoly.
 */
public class Player {
    private String ID;
    private String nome;
    private int posizione;
    private int soldi;
    // lista degli id delle proprieta possedute
    private ArrayList<String> proprieta;
    // lista degli id delle carte uscite di prigione possedute
    private String uscitePrigione[];

    private int turniPrigione;
    private boolean inPrigione;

    private int countCase;
    private int countAlberghi;

    private int idPedina;

    /**
     * Rappresenta un giocatore nel gioco Monopoly.
     * Ogni giocatore ha un ID univoco, un nome, una quantità di soldi, una
     * posizione sulla scacchiera,
     * una lista di proprietà possedute, informazioni sullo stato di prigione e
     * altri attributi relativi al gioco.
     */
    public Player(String nome, int idPedina) {
        this.ID = "P#" + ++Settings.PLAYER_COUNT;
        this.nome = nome;
        this.soldi = Settings.STARTING_MONEY;
        this.posizione = 0;
        this.proprieta = new ArrayList<>();
        this.uscitePrigione = new String[2];
        this.inPrigione = false;
        this.uscitePrigione[0] = "";
        this.uscitePrigione[1] = "";
        this.turniPrigione = 0;
        this.countCase = 0;
        this.countAlberghi = 0;
        this.idPedina = idPedina;
    }

    /**
     * Ritorna una rappresentazione in stringa dell'oggetto Player.
     * Il formato della stringa è il seguente:
     * {ID:nome:soldi:posizione:[proprietà]:[uscitePrigione]}
     * 
     *
     * @return una rappresentazione in stringa dell'oggetto Player
     */
    public String toString() {
        // ID:nome:soldi:posizione:[proprietà1,proprietà2]:[uscitePrigione]
        String s = "" + ID + ":" + nome + ":" + soldi + ":" + posizione + ":[";
        for (String string : proprieta) {
            if (proprieta.indexOf(string) == 0) {
                s += string;
            } else {
                s += "," + string;
            }
        }
        s += "]:[";

        for (int i = 0; i < uscitePrigione.length; i++) {
            if (i == 0) {
                s += uscitePrigione[i];
            } else {
                s += "," + uscitePrigione[i];
            }
        }
        s += "]";
        return s;
    }

    /**
     * Ritorna la quantità di soldi che il giocatore possiede.
     *
     * @return la quantità di soldi che il giocatore possiede
     */
    public int getSoldi() {
        return soldi;
    }

    /**
     * Ritorna l'ID della pedina, usato dal client per identificare la pedina.
     *
     * @return l'ID della pedina, usato dal client per identificare la pedina.
     */
    public int getIDPedina() {
        return idPedina;
    }

    /**
     * Ritorna il nome del giocatore.
     *
     * @return il nome del giocatore
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta la quantità di soldi che il giocatore possiede.
     * 
     * @param soldi la quantità di soldi da impostare
     */
    public void setSoldi(int soldi) {
        this.soldi = soldi;
    }

    /**
     * Ritorna l'ID del giocatore.
     *
     * @return l'ID del giocatore
     */
    public String getID() {
        return ID;
    }

    /**
     * Ritorna la posizione corrente del giocatore.
     *
     * @return la posizione corrente del giocatore
     */
    public int getPosizione() {
        return posizione;
    }

    /**
     * Imposta la posizione del giocatore sulla scacchiera di gioco.
     * 
     * @param posizione la nuova posizione del giocatore
     */
    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    /**
     * Aggiungi una proprietà alla lista delle proprietà possedute dal giocatore.
     * 
     * @param idProprieta l'ID della proprietà da aggiungere
     */
    public void addProprieta(String idProprieta) {
        this.proprieta.add(idProprieta);
    }

    /**
     * Aggiungi l'idUscitaPrigione specificato all'array di uscitePrigione.
     * Se il primo elemento di uscitePrigione è vuoto, l'idUscitaPrigione viene
     * aggiunto ad esso.
     * In caso contrario, l'idUscitaPrigione viene aggiunto al secondo elemento di
     * uscitePrigione.
     * 
     * @param idUscitaPrigione l'id dell'uscitaPrigione da aggiungere
     */
    public void addUscitaPrigione(String idUscitaPrigione) {
        if (uscitePrigione[0] == "") {
            uscitePrigione[0] = idUscitaPrigione;
        } else {
            uscitePrigione[1] = idUscitaPrigione;
        }
    }

    /**
     * Rimuovi la carta "uscita prigione" dalla collezione del giocatore.
     * Se il giocatore ha più carte "uscita prigione", la prima viene rimossa.
     * Se il giocatore ha solo una carta "uscita prigione", viene rimossa.
     * Se il giocatore non ha carte "uscita prigione", non succede nulla.
     */
    public void removeUscitaPrigione() {
        if (uscitePrigione[0] != "") {
            uscitePrigione[0] = "";
        } else {
            uscitePrigione[1] = "";
        }
    }

    /**
     * Controlla se il giocatore ha una carta "Uscita Prigione".
     * 
     * @return true se il giocatore ha una carta "Uscita Prigione", false altrimenti
     */
    public boolean hasUscitaPrigione() {
        return uscitePrigione[0] != "" || uscitePrigione[1] != "";
    }

    /**
     * Incrementa di uno il numero di turni passati in prigione.
     */
    public void addTurnoPrigione() {
        turniPrigione++;
    }

    /**
     * Ritorna il numero di turni che il giocatore ha passato in prigione.
     *
     * @return il numero di turni passati in prigione
     */
    public int getTurniPrigione() {
        return turniPrigione;
    }

    /**
     * Resetta il numero di turni passati in prigione dal giocatore.
     */
    public void resetTurniPrigione() {
        turniPrigione = 0;
    }

    /**
     * Controlla se il giocatore è in prigione.
     * 
     * @return true se il giocatore è in prigione, false altrimenti.
     */
    public boolean InPrigione() {
        return inPrigione;
    }

    /**
     * Imposta lo stato del giocatore di essere in prigione.
     * 
     * @param isInPrigione true se il giocatore è in prigione, false altrimenti
     */
    public void setInPrigione(boolean isInPrigione) {
        this.inPrigione = isInPrigione;
    }

    /**
     * Controlla se il giocatore possiede una proprietà specifica.
     * 
     * @param idProprieta l'ID della proprietà da controllare
     * @return true se il giocatore possiede la proprietà, false altrimenti
     */
    public boolean hasProprieta(String idProprieta) {
        return proprieta.contains(idProprieta);
    }

    /**
     * Ritorna il numero di case possedute dal giocatore.
     *
     * @return il numero di case possedute dal giocatore
     */
    public int getCountCase() {
        return countCase;
    }

    /**
     * Ritorna il numero di alberghi posseduti dal giocatore.
     *
     * @return il numero di alberghi posseduti dal giocatore
     */
    public int getCountAlberghi() {
        return countAlberghi;
    }

    /**
     * Aggiungi il numero specificato di case al countCase del giocatore.
     *
     * @param count il numero di case da aggiungere
     */
    public void addCasa(int count) {
        countCase += count;
    }

    /**
     * Aggiungi il numero specificato di alberghi al countAlberghi del giocatore.
     * 
     * @param count il numero di alberghi da aggiungere
     */
    public void addAlbergo(int count) {
        countAlberghi += count;
    }
}
