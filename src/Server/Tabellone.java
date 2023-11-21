package src.Server;

import src.Server.Carte.Carta;

/**
 * La classe Tabellone rappresenta il tabellone di gioco in una partita a
 * Monopoly.
 * Contiene metodi per accedere e manipolare le proprietà sul tabellone.
 */
public class Tabellone {
    public Carta[] caselle = new Carta[40];

    /**
     * Crea un nuovo oggetto Tabellone.
     */
    public Tabellone() {
    }

    /**
     * Ritorna l'oggetto Carta alla posizione specificata sul tabellone.
     * 
     * @param pos La posizione dell'oggetto Carta sul tabellone.
     * @return L'oggetto Carta alla posizione specificata.
     */
    public Carta getCasellaByPos(int pos) {
        return caselle[pos];
    }

    /**
     * Ritorna l'oggetto Carta con l'ID specificato.
     * 
     * @param id L'ID dell'oggetto Carta.
     * @return L'oggetto Carta con l'ID specificato.
     */
    public Carta getCassellaByID(String id) {
        return caselle[Integer.parseInt(id.split("#")[1])];
    }

    /**
     * Ritorna un array di tutti gli oggetti Carta sul tabellone.
     * 
     * @return Un array di tutti gli oggetti Carta sul tabellone.
     */
    public Carta[] getCaselle() {
        return caselle;
    }

    /**
     * Ritorna il tipo dell'oggetto Carta alla posizione specificata sul tabellone.
     * 
     * @param pos La posizione dell'oggetto Carta sul tabellone.
     * @return Il tipo dell'oggetto Carta alla posizione specificata.
     */
    public String getTypeCasella(int pos) {
        return caselle[pos].getID().split("#")[0];
    }
}
