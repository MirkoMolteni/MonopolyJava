package src.Server;

import src.Server.Carte.Carta;

public class Tabellone {
    Carta[] caselle = new Carta[40];

    public Tabellone() {
    }

    public Carta getCasella(int pos) {
        return caselle[pos];
    }

    public Carta[] getCaselle() {
        return caselle;
    }
}
