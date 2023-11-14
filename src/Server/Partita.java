package src.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import src.Server.Carte.*;

public class Partita {
    private Tabellone t = new Tabellone();
    private ArrayList<Imprevisto> imprev = new ArrayList<Imprevisto>();
    private ArrayList<Probabilita> prob = new ArrayList<Probabilita>();
    private HashMap<String, Giocatore> giocatori = new HashMap<String, Giocatore>();
    private int turno = 1;

    public Partita() throws Exception {
        Parser.parseCarteXml(t.getCaselle(), imprev, prob);
    }

    public String addGiocatore(Giocatore g) {
        String id = g.getID();
        giocatori.put(id, g);
        return "1;";
    }

    public String startGame() {
        Settings.GAME_STATUS = 0;
        String s = "2";

        for (Entry<String, Giocatore> entry : giocatori.entrySet()) {
            // String key = entry.getKey();
            Giocatore value = entry.getValue();
            s += ";" + value.toString();
        }
        return s;
    }

    public String rollDice() {
        int dice1Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
        int dice2Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
        if (dice1Roll != dice2Roll) {
            // TODO: implementare il controllo del tipo di casella in cui si trova il
            // giocatore
            // aggiorno la posizione del giocatore
            getCurrentGiocatore().setPosizione(getCurrentGiocatore().getPosizione() + dice1Roll + dice2Roll);
        } else {
            // TODO: implementare la logica del doppio
        }

        return "3;" + dice1Roll + ";" + dice2Roll + ";" + getCurrentGiocatore().toString();
    }

    public String buyCasella() {
        Giocatore g = getCurrentGiocatore();
        int pos = g.getPosizione();
        String s = "";

        // controllare se la casella è acquistabile
        if (t.getCasella(pos).getPropietario() == "") {
            // controllare se il giocatore ha abbastanza soldi
            if (t.getCasella(pos).getPrezzo() <= g.getSoldi()) {
                // sottraggo i soldi al giocatore
                g.setSoldi(g.getSoldi() - t.getCasella(pos).getPrezzo());
                // aggiungo la casella alla lista delle proprieta del giocatore
                g.addProprieta(t.getCasella(pos).getID());
                // imposto il proprietario della casella
                t.getCasella(pos).setPropietario(g.getID());
                // rispondo al client
                s = "4;" + getCurrentGiocatore().toString();
            } else {
                s = "0;Soldi insufficienti";
            }
        } else {
            s = "0;Casella non acquistabile";
        }

        return s;
    }

    public String changeTurn() {
        turno++;
        return "5;";
    }

    private Giocatore getCurrentGiocatore() {
        return giocatori.get("P#" + (turno % Settings.PLAYER_COUNT));
    }
}
