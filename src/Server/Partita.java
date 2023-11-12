package src.Server;

import java.util.ArrayList;

public class Partita {
    ArrayList<Giocatore> giocatori;
    ArrayList<Casella> caselle;

    public Partita() {
        giocatori = new ArrayList<Giocatore>();
        caselle = new ArrayList<Casella>();
    }
}
