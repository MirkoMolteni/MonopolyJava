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
    private Player currentPlayer = null;

    public Partita() throws Exception {
        Parser.parseCarteXml(t.getCaselle(), imprev, prob);
    }

    public String addGiocatore(Player g) {
        String id = g.getID();
        giocatori.put(id, g);
        currentPlayer = giocatori.get("P#1");
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

    public String rollDiceAndMove() {
        // lancio i dadi
        int dice1Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
        int dice2Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
        // TODO: implementare la logica del doppio

        // controllo se il player è in prigione
        if (currentPlayer.getPosizione() == 10 && currentPlayer.InPrigione()) {
            // controllo se ha una carta uscita prigione
            if (currentPlayer.hasUscitaPrigione()) {
                // usa la carta
                currentPlayer.removeUscitaPrigione();
                // lo sposto in base al tiro dei dadi
                movePlayer(false, dice2Roll + dice1Roll);
                return "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString()
                        + ";Sei uscito di prigione tramite una carta uscita prigione";
            } else {
                // se non ha una carta, controllo se ha fatto un doppio
                if (dice1Roll == dice2Roll) {
                    // se ha fatto un doppio lo sposto in base al tiro dei dadi
                    movePlayer(false, dice2Roll + dice1Roll);
                    return "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString()
                            + ";Sei uscito di prigione tramite un doppio";
                } else {
                    // se non ha fatto un doppio e non ha ancora fatto 3 turni in prigione, aumento
                    if (currentPlayer.getTurniPrigione() < 3) {
                        currentPlayer.addTurnoPrigione();
                        return "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString()
                                + ";Non sei riuscito a uscire di prigione";
                    } else {
                        currentPlayer.resetTurniPrigione();
                    }
                }
            }
        }

        // aggiorno la posizione del giocatore
        movePlayer(false, dice2Roll + dice1Roll);
        String s = "";
        // controllo la casella su cui è finito il player
        String typeCasella = t.getTypeCasella(currentPlayer.getPosizione());
        if (!typeCasella.equals("PL") && !typeCasella.equals("R") && !typeCasella.equals("S")) {
            switch (typeCasella) {
                case "V":
                    // gli do 200€ per il giro completo
                    currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString()
                            + ";Giro completato, prelievi 200€";
                    break;
                case "PR":
                    // scelgo una carta probabilita
                    Probabilita p = prob.get((int) (Math.random() * prob.size()));
                    // se è un'uscita di prigione, la do al player
                    if (p.getCaso() == 5) {
                        currentPlayer.addUscitaPrigione(p.getID());
                    }
                    // eseguo la carta
                    String x = eseguiProbabilita(p);
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString() + ";" + p.getNome()
                            + "\r\n" + x;
                    // la elimino dal mazzo
                    prob.remove(p);
                    break;
                case "I":
                    // scelgo una carta imprevisto
                    Imprevisto i = imprev.get((int) (Math.random() * imprev.size()));
                    // se è un'uscita di prigione, la do al player
                    if (i.getCaso() == 5) {
                        currentPlayer.addUscitaPrigione(i.getID());
                    }
                    // eseguo la carta
                    x = eseguiImprevisto(i);
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString() + ";" + i.getNome()
                            + "\r\n" + x;
                    // la elimino dal mazzo
                    imprev.remove(i);
                    break;
                case "T":
                    // tolgo i soldi al player
                    currentPlayer.setSoldi(currentPlayer.getSoldi()
                            + t.getCasellaByPos(currentPlayer.getPosizione()).getPrezzo());
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString() + ";Hai pagato "
                            + Math.abs(t.getCasellaByPos(currentPlayer.getPosizione()).getPrezzo()) + "€ di "
                            + t.getCasellaByPos(currentPlayer.getPosizione()).getNome();
                    break;
                case "GPG":
                    // sposto il player in prigione
                    movePlayer(true, 10);
                    s = "3-1;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString()
                            + ";Sei finito in prigione";
                    break;
                default:
                    s = "3-0;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString();
                    break;
            }
        } else {
            s = "3-0;" + dice1Roll + ";" + dice2Roll + ";" + currentPlayer.toString();
        }

        return s;
    }

    public String buyCasella() {
        Player g = currentPlayer;
        int pos = g.getPosizione();
        String s = "";

        // TODO:controllare se la casella è ipotecata

        // controllo se il player è in transito sulla prigione
        if (pos == 10) {
            s = "0;Non puoi acquistare la prigione";
            return s;
        }

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
                s = "4;" + currentPlayer.toString();
            } else {
                s = "0;Soldi insufficienti";
            }
        } else {
            s = "0;Casella non acquistabile";
        }

        return s;
    }

    public String ipotecaCasella(String id) {
        String s = "";

        // controllo se il giocatore possiede la casella
        if (currentPlayer.hasProprieta(id)) {
            // controllo se la casella è ipotecata
            if (!t.getCassellaByID(id).isIpotecata()) {
                // ipoteco la casella
                t.getCassellaByID(id).setIpotecata(true);
                // aggiungo i soldi al giocatore
                currentPlayer.setSoldi(currentPlayer.getSoldi() + t.getCassellaByID(id).getValIpoteca());
                // rispondo al client
                s = "6;" + currentPlayer.toString();
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
        String x;
        if (turno > Settings.PLAYER_COUNT) {
            x = (turno - Settings.PLAYER_COUNT) + "";
            turno = 1;
        } else {
            x = turno + "";
        }
        currentPlayer = giocatori.get("P#" + x);
        return "5;";
    }

    private String eseguiImprevisto(Imprevisto i) {
        String s = "";
        int caso = i.getCaso();
        String value = i.getValue();
        int posP = currentPlayer.getPosizione();
        switch (caso) {
            case 0:
                currentPlayer.setSoldi(currentPlayer.getSoldi() + i.getPrezzo());
                break;
            case 1:
                // vai alla casella successiva
                String typeCasella = value.split("#")[0];
                // se è una prigione, non ritira il bonus giro completo
                if (typeCasella.equals("PG")) {
                    movePlayer(true, 10);
                } else {

                    // caso della casella successiva
                    if (Integer.parseInt(value.split("#")[1]) == 0 && !typeCasella.equals("V")) {
                        // se è una railroad, pago il doppio del pedaggio
                        if (typeCasella.equals("R")) {
                            // trovo la posizione della prossima railroad 5-15-25-35
                            int posR = 0;
                            if (posP < 5) {
                                posR = 5;
                            } else if (posP < 15) {
                                posR = 15;
                            } else if (posP < 25) {
                                posR = 25;
                            } else if (posP < 35) {
                                posR = 35;
                            } else {
                                posR = 5;
                            }

                            // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                            if (posR == 5 && posP >= 35) {
                                currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                            }

                            // muovo il player
                            movePlayer(true, posR);

                            // controllo se la casella ha già un proprietario
                            if (t.getCasellaByPos(posP).getPropietario() != "") {
                                // se ha un proprietario, paga il pedaggio
                                int importo = currentPlayer.getSoldi()
                                        - t.getCasellaByPos(posP).getPedaggio() * 2;
                                currentPlayer.setSoldi(importo);
                                s = "Hai pagato " + importo + "€ di pedaggio";
                            }
                        }
                        // se è una società, pago 10 volte il tiro dei dadi
                        if (typeCasella.equals("S")) {
                            // trovo la posizione della prossima società 12-28
                            int posS = 0;
                            if (posP < 12) {
                                posS = 12;
                            } else if (posP < 28) {
                                posS = 28;
                            } else {
                                posS = 12;
                            }

                            // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                            if (posS == 12 && posP >= 28) {
                                currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                            }

                            // muovo il player
                            movePlayer(true, posS);

                            // controllo se la casella ha già un proprietario
                            if (t.getCasellaByPos(posP).getPropietario() != "") {
                                // se ha un proprietario, paga 10 volte il lancio dei dadi
                                int dice1Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
                                int dice2Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
                                int importo = currentPlayer.getSoldi() - ((dice1Roll + dice2Roll) * 10);
                                currentPlayer.setSoldi(importo);
                                s = "Hai rollato" + (dice1Roll + dice2Roll) + " e hai pagato " + importo
                                        + "€ di pedaggio";
                            }
                        }
                    } else {
                        // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                        if (posP > Integer.parseInt(value.split("#")[1])) {
                            currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                        }
                        // muovo il player
                        movePlayer(false, Integer.parseInt(value.split("#")[1]));
                    }
                }
                break;
            case 2:
                // paga per ogni casa e albergo
                int impCase = Integer.parseInt(value.split(":")[0]);
                int impAlberghi = Integer.parseInt(value.split(":")[1]);

                currentPlayer.setSoldi(currentPlayer.getSoldi() - (impCase * currentPlayer.getCountCase())
                        - (impAlberghi * currentPlayer.getCountAlberghi()));

                break;
            case 3:
                // paga +/- ogni giocatore
                if (Integer.parseInt(value) > 0) {
                    // ogni giocatore paga al player
                    for (Player g : giocatori.values()) {
                        if (g.getID() != currentPlayer.getID()) {
                            g.setSoldi(g.getSoldi() - Integer.parseInt(value));
                            currentPlayer.setSoldi(currentPlayer.getSoldi() + Integer.parseInt(value));
                        }
                    }
                } else {
                    // il player paga ogni giocatore
                    for (Player g : giocatori.values()) {
                        if (g.getID() != currentPlayer.getID()) {
                            currentPlayer.setSoldi(currentPlayer.getSoldi() - Integer.parseInt(value));
                            g.setSoldi(g.getSoldi() + Integer.parseInt(value));
                        }
                    }
                }
                break;
            case 4:
                // avanza di tot caselle
                // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                if (posP + Integer.parseInt(value) > 39) {
                    currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                }
                // muovo il player
                movePlayer(false, Integer.parseInt(value));
                break;
            case 5:
                // esci dalla prigione
                currentPlayer.addUscitaPrigione(i.getID());
                break;
            default:
                break;
        }

        // tolgo la carta dall'array
        imprev.remove(i);

        return s;
    }

    private String eseguiProbabilita(Probabilita p) {
        String s = "";
        int caso = p.getCaso();
        String value = p.getValue();
        int posP = currentPlayer.getPosizione();
        switch (caso) {
            case 0:
                currentPlayer.setSoldi(currentPlayer.getSoldi() + p.getPrezzo());
                break;
            case 1:
                // vai alla casella successiva
                String typeCasella = value.split("#")[0];
                // se è una prigione, non ritira il bonus giro completo
                if (typeCasella.equals("PG")) {
                    movePlayer(true, 10);
                } else {

                    // caso della casella successiva
                    if (Integer.parseInt(value.split("#")[1]) == 0 && !typeCasella.equals("V")) {
                        // se è una railroad, pago il doppio del pedaggio
                        if (typeCasella.equals("R")) {
                            // trovo la posizione della prossima railroad 5-15-25-35
                            int posR = 0;
                            if (posP < 5) {
                                posR = 5;
                            } else if (posP < 15) {
                                posR = 15;
                            } else if (posP < 25) {
                                posR = 25;
                            } else if (posP < 35) {
                                posR = 35;
                            } else {
                                posR = 5;
                            }

                            // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                            if (posR == 5 && posP >= 35) {
                                currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                            }

                            // muovo il player
                            movePlayer(true, posR);

                            // controllo se la casella ha già un proprietario
                            if (t.getCasellaByPos(posP).getPropietario() != "") {
                                // se ha un proprietario, paga il pedaggio
                                int importo = currentPlayer.getSoldi()
                                        - t.getCasellaByPos(posP).getPedaggio() * 2;
                                currentPlayer.setSoldi(importo);
                                s = "Hai pagato " + importo + "€ di pedaggio";
                            }
                        }
                        // se è una società, pago 10 volte il tiro dei dadi
                        if (typeCasella.equals("S")) {
                            // trovo la posizione della prossima società 12-28
                            int posS = 0;
                            if (posP < 12) {
                                posS = 12;
                            } else if (posP < 28) {
                                posS = 28;
                            } else {
                                posS = 12;
                            }

                            // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                            if (posS == 12 && posP >= 28) {
                                currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                            }

                            // muovo il player
                            movePlayer(true, posS);

                            // controllo se la casella ha già un proprietario
                            if (t.getCasellaByPos(posP).getPropietario() != "") {
                                // se ha un proprietario, paga 10 volte il lancio dei dadi
                                int dice1Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
                                int dice2Roll = (int) (Math.random() * Settings.MAX_ROLL) + 1;
                                int importo = currentPlayer.getSoldi() - ((dice1Roll + dice2Roll) * 10);
                                currentPlayer.setSoldi(importo);
                                s = "Hai rollato" + (dice1Roll + dice2Roll) + " e hai pagato " + importo
                                        + "€ di pedaggio";
                            }
                        }
                    } else {
                        // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                        if (posP > Integer.parseInt(value.split("#")[1])) {
                            currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                        }
                        // muovo il player
                        movePlayer(true, Integer.parseInt(value.split("#")[1]));

                        // controllo se la casella ha già un proprietario
                        if (t.getCasellaByPos(posP).getPropietario() != "") {
                            // se ha un proprietario, paga il pedaggio
                            currentPlayer.setSoldi(currentPlayer.getSoldi()
                                    - t.getCasellaByPos(posP).getPedaggio());
                        }
                    }
                }
                break;
            case 2:
                // paga per ogni casa e albergo
                int impCase = Integer.parseInt(value.split(":")[0]);
                int impAlberghi = Integer.parseInt(value.split(":")[1]);

                currentPlayer.setSoldi(currentPlayer.getSoldi() - (impCase * currentPlayer.getCountCase())
                        - (impAlberghi * currentPlayer.getCountAlberghi()));

                break;
            case 3:
                // paga +/- ogni giocatore
                if (Integer.parseInt(value) > 0) {
                    // ogni giocatore paga al player
                    for (Player g : giocatori.values()) {
                        if (g.getID() != currentPlayer.getID()) {
                            g.setSoldi(g.getSoldi() - Integer.parseInt(value));
                            currentPlayer.setSoldi(currentPlayer.getSoldi() + Integer.parseInt(value));
                        }
                    }
                } else {
                    // il player paga ogni giocatore
                    for (Player g : giocatori.values()) {
                        if (g.getID() != currentPlayer.getID()) {
                            currentPlayer.setSoldi(currentPlayer.getSoldi() - Integer.parseInt(value));
                            g.setSoldi(g.getSoldi() + Integer.parseInt(value));
                        }
                    }
                }
                break;
            case 4:
                // avanza di tot caselle
                // se per andare alla casella indicata devo passare dal via, ritiro il bonus
                if (posP + Integer.parseInt(value) > 39) {
                    currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
                }
                // muovo il player
                movePlayer(false, Integer.parseInt(value));
                break;
            case 5:
                // esci dalla prigione
                currentPlayer.addUscitaPrigione(p.getID());
                break;
            default:
                break;
        }

        // tolgo la carta dall'array
        prob.remove(p);

        return s;
    }

    public String getListaGiocatori() {
        String s = "7;[";

        for (Entry<String, Player> entry : giocatori.entrySet()) {
            Player value = entry.getValue();
            s += value.getNome() + ",";
        }
        s += "]";
        return s.replace(",]", "]");
    }

    private void movePlayer(boolean goTo, int pos) {
        // metodo che muove il player
        if (goTo)
            currentPlayer.setPosizione(pos);
        else
            currentPlayer.setPosizione(currentPlayer.getPosizione() + pos);

        checkPosizione();
    }

    private void checkPosizione() {
        // metodo che controlla la posizione del player per capire se deve fare qualcosa

        // controllo se la casella ha già un proprietario
        if (t.getCasellaByPos(currentPlayer.getPosizione()).getPropietario() != "") {
            // se ha un proprietario, paga il pedaggio
            currentPlayer.setSoldi(currentPlayer.getSoldi()
                    - t.getCasellaByPos(currentPlayer.getPosizione()).getPedaggio());
        }
    }

}