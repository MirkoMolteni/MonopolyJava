package src.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import src.Server.Carte.*;

public class Partita {
    private Tabellone t = new Tabellone();
    private ArrayList<Imprevisto> imprev = new ArrayList<Imprevisto>();
    private ArrayList<Probabilita> prob = new ArrayList<Probabilita>();
    private HashMap<String, Player> giocatori = new HashMap<String, Player>();
    private int turno = 1;

    public Partita() throws Exception {
        Parser.parseCarteXml(t.getCaselle(), imprev, prob);
    }

    public String addGiocatore(Player g) {
        String id = g.getID();
        giocatori.put(id, g);
        return "1;";
    }

    public String startGame() {
        Settings.GAME_STATUS = 0;
        String s = "2";

        for (Entry<String, Player> entry : giocatori.entrySet()) {
            // String key = entry.getKey();
            Player value = entry.getValue();
            s += ";" + value.toString();
        }
        return s;
    }

    public String rollDice() {
        // lancio i dadi
        int dice1Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
        int dice2Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
        // TODO: implementare la logica del doppio

        // controllo se il player è in prigione
        if (currentPlayer().getPosizione() == 10) {
            // controllo se ha una carta uscita prigione
            if (currentPlayer().hasUscitaPrigione()) {
                // usa la carta
                currentPlayer().removeUscitaPrigione();
                // lo sposto in base al tiro dei dadi
                currentPlayer().setPosizione(currentPlayer().getPosizione() + dice1Roll + dice2Roll);
                return "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString()
                        + ";Sei uscito di prigione tramite una carta uscita prigione";
            } else {
                // se non ha una carta, controllo se ha fatto un doppio
                if (dice1Roll == dice2Roll) {
                    // se ha fatto un doppio lo sposto in base al tiro dei dadi
                    currentPlayer().setPosizione(currentPlayer().getPosizione() + dice1Roll + dice2Roll);
                    return "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString()
                            + ";Sei uscito di prigione tramite un doppio";
                } else {
                    // se non ha fatto un doppio e non ha ancora fatto 3 turni in prigione, aumento
                    if (currentPlayer().getTurniPrigione() < 3) {
                        currentPlayer().addTurnoPrigione();
                        return "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString()
                                + ";Non sei riuscito a uscire di prigione";
                    } else {
                        currentPlayer().resetTurniPrigione();
                    }
                }
            }
        }

        // aggiorno la posizione del giocatore
        currentPlayer().setPosizione(currentPlayer().getPosizione() + dice1Roll + dice2Roll);
        String s = "";
        // controllo la casella su cui è finito il player
        String typeCasella = t.getTypeCasella(currentPlayer().getPosizione());
        if (!typeCasella.equals("PL") && !typeCasella.equals("R") && !typeCasella.equals("S")) {
            switch (typeCasella) {
                case "V":
                    // gli do 200€ per il giro completo
                    currentPlayer().setSoldi(currentPlayer().getSoldi() + 200);
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString()
                            + ";Giro completato, prelievi 200€";
                    break;
                case "PR":
                    // scelgo una carta probabilita
                    Probabilita p = prob.get((int) (Math.random() * prob.size()));
                    // se è un'uscita di prigione, la do al player
                    if (p.getCaso() == 5) {
                        currentPlayer().addUscitaPrigione(p.getID());
                    }
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString() + ";" + p.getNome();
                    // eseguo la carta
                    eseguiProbabilita(p);
                    // la elimino dal mazzo
                    prob.remove(p);
                    break;
                case "I":
                    // scelgo una carta imprevisto
                    Imprevisto i = imprev.get((int) (Math.random() * imprev.size()));
                    // se è un'uscita di prigione, la do al player
                    if (i.getCaso() == 5) {
                        currentPlayer().addUscitaPrigione(i.getID());
                    }
                    // eseguo la carta
                    eseguiImprevisto(i);
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString() + ";" + i.getNome();
                    // la elimino dal mazzo
                    imprev.remove(i);
                    break;
                case "T":
                    // tolgo i soldi al player
                    currentPlayer().setSoldi(currentPlayer().getSoldi()
                            + t.getCasellaByPos(currentPlayer().getPosizione()).getPrezzo());
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString() + ";Hai pagato "
                            + Math.abs(t.getCasellaByPos(currentPlayer().getPosizione()).getPrezzo()) + "€ di "
                            + t.getCasellaByPos(currentPlayer().getPosizione()).getNome();
                    break;
                case "GPG":
                    // sposto il player in prigione
                    currentPlayer().setPosizione(10);
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString()
                            + ";Sei finito in prigione";
                    break;
                default:
                    s = "3-0;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString();
                    break;
            }
        } else {
            s = "3-0;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer().toString();
        }

        return s;
    }

    public String buyCasella() {
        Player g = currentPlayer();
        int pos = g.getPosizione();
        String s = "";

        // TODO:controllare se la casella è ipotecata
        // TODO: controllare se la casella è la prigione

        // controllare se la casella è acquistabile
        if (t.getCasellaByPos(pos).getPropietario() == "") {
            // controllare se il giocatore ha abbastanza soldi
            if (t.getCasellaByPos(pos).getPrezzo() <= g.getSoldi()) {
                // sottraggo i soldi al giocatore
                g.setSoldi(g.getSoldi() - t.getCasellaByPos(pos).getPrezzo());
                // aggiungo la casella alla lista delle proprieta del giocatore
                g.addProprieta(t.getCasellaByPos(pos).getID());
                // imposto il proprietario della casella
                t.getCasellaByPos(pos).setPropietario(g.getID());
                // rispondo al client
                s = "4;" + currentPlayer().toString();
            } else {
                s = "0;Soldi insufficienti";
            }
        } else {
            s = "0;Casella non acquistabile";
        }

        return s;
    }

    public String ipotecaCasella(String id) {
        Player g = currentPlayer();
        String s = "";

        // controllo se il giocatore possiede la casella
        if (g.hasProprieta(id)) {
            // controllo se la casella è ipotecata
            if (!t.getCassellaByID(id).isIpotecata()) {
                // ipoteco la casella
                t.getCassellaByID(id).setIpotecata(true);
                // aggiungo i soldi al giocatore
                g.setSoldi(g.getSoldi() + t.getCassellaByID(id).getValIpoteca());
                // rispondo al client
                s = "6;" + currentPlayer().toString();
            } else {
                s = "0;Casella già ipotecata";
            }
        } else {
            s = "0;Non possiedi questa casella";
        }

        return s;
    }

    public String changeTurn() {
        turno++;
        return "5;";
    }

    private Player currentPlayer() {
        String x;
        if (turno > Settings.PLAYER_COUNT) {
            x = (turno - Settings.PLAYER_COUNT) + "";
            turno = 1;
        } else {
            x = turno + "";
        }
        return giocatori.get("P#" + x);
    }

    private void eseguiProbabilita(Probabilita p) {
        // TODO: implementare le probabilita
    }

    private void eseguiImprevisto(Imprevisto i) {
        // TODO: implementare gli imprevisti
    }
}
