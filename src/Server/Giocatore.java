package src.Server;

import java.util.ArrayList;

public class Giocatore {
    private String ID;
    private String nome;
    private int posizione;
    private int soldi;
    // lista degli id delle proprieta possedute
    ArrayList<String> proprieta;
    // lista degli id delle carte uscite di prigione possedute
    ArrayList<String> uscitePrigione;

    public Giocatore(String nome) {
        this.ID = "P#" + Settings.PLAYER_COUNT++;
        this.nome = nome;
        this.soldi = Settings.STARTING_MONEY;
        this.posizione = 0;
        this.proprieta = new ArrayList<>();
        this.uscitePrigione = new ArrayList<>();
    }

    public String toString() {
        // {ID:nome:soldi:posizione:[proprietà]:[uscitePrigione]}
        String s = "{" + ID + ":" + nome + ":" + soldi + ":" + posizione + ":[";
        for (String string : proprieta) {
            if (proprieta.indexOf(string) == 0) {
                s += string;
            } else {
                s += "," + string;
            }
        }
        s += "]:[";
        for (String string : uscitePrigione) {
            if (uscitePrigione.indexOf(string) == 0) {
                s += string;
            } else {
                s += "," + string;
            }
        }
        s += "]}";
        return s;
    }

    public int getSoldi() {
        return soldi;
    }

    public void setSoldi(int soldi) {
        this.soldi = soldi;
    }

    public String getID() {
        return ID;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public void addProprieta(String idProprieta) {
        this.proprieta.add(idProprieta);
    }
}
