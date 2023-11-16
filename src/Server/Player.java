package src.Server;

import java.util.ArrayList;

public class Player {
    private String ID;
    private String nome;
    private int posizione;
    private int soldi;
    // lista degli id delle proprieta possedute
    ArrayList<String> proprieta;
    // lista degli id delle carte uscite di prigione possedute
    String uscitePrigione[];

    private int turniPrigione = 0;

    public Player(String nome) {
        this.ID = "P#" + ++Settings.PLAYER_COUNT;
        this.nome = nome;
        this.soldi = Settings.STARTING_MONEY;
        this.posizione = 0;
        this.proprieta = new ArrayList<>();
        this.uscitePrigione = new String[2];
        uscitePrigione[0] = "";
        uscitePrigione[1] = "";
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

        for (int i = 0; i < uscitePrigione.length; i++) {
            if (i == 0) {
                s += uscitePrigione[i];
            } else {
                s += "," + uscitePrigione[i];
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

    public void addUscitaPrigione(String idUscitaPrigione) {
        if (uscitePrigione[0] == "") {
            uscitePrigione[0] = idUscitaPrigione;
        } else {
            uscitePrigione[1] = idUscitaPrigione;
        }
    }

    public void removeUscitaPrigione() {
        if (uscitePrigione[0] != "") {
            uscitePrigione[0] = "";
        } else {
            uscitePrigione[1] = "";
        }
    }

    public boolean hasUscitaPrigione() {
        return uscitePrigione[0] != "" || uscitePrigione[1] != "";
    }

    public void addTurnoPrigione() {
        turniPrigione++;
    }

    public int getTurniPrigione() {
        return turniPrigione;
    }

    public void resetTurniPrigione() {
        turniPrigione = 0;
    }

    public boolean hasProprieta(String idProprieta) {
        return proprieta.contains(idProprieta);
    }
}
