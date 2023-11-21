package src.Server;

/**
 * La classe Settings contiene costanti e variabili relative alle impostazioni
 * del gioco.
 */
public class Settings {
    /**
     * Il numero massimo di giocatori consentiti nel gioco.
     */
    public static final int MAX_PLAYERS = 4;

    /**
     * Il numero di soldi iniziali per ogni giocatore.
     */
    public static final int STARTING_MONEY = 1500;

    /**
     * Il numero massimo che può uscire lanciando un dado.
     */
    public static final int MAX_ROLL = 6;

    /**
     * Il numero massimo di stazioni nel gioco.
     */
    public static final int MAX_STATIONS = 4;

    /**
     * Il numero massimo di servizi nel gioco.
     */
    public static final int MAX_SERVICES = 2;

    /**
     * Il numero massimo di case consentite per ogni proprietà.
     */
    public static final int MAX_HOUSES_PER_PROPERTY = 4;

    /**
     * Il numero massimo di alberghi consentiti per ogni proprietà.
     */
    public static final int MAX_HOTELS_PER_PROPERTY = 1;

    /**
     * lo stato del gioco. -1 = non iniziato, 0 = in corso, 1 = finito.
     */
    public static int GAME_STATUS = -1;

    /**
     * Il numero corrente di giocatori nel gioco.
     */
    public static int PLAYER_COUNT = 0;
}
