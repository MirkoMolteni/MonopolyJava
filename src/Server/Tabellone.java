package src.Server;

import src.Server.Carte.Carta;

public class Tabellone {
    public Carta[] caselle = new Carta[40];

    public Tabellone() {
    }

    public Carta getCasellaByPos(int pos) {
        return caselle[pos];
    }

    public Carta getCassellaByID(String id) {
        return caselle[Integer.parseInt(id.split("#")[1])];
    }

    public Carta[] getCaselle() {
        return caselle;
    }

    public String getTypeCasella(int pos) {
        return caselle[pos].getID().split("#")[0];
    }
}
