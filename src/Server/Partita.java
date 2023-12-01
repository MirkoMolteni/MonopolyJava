package src.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import src.Server.Carte.*;

/**
 * La classe Partita rappresenta una sessione di gioco di Monopoly.
 * Gestisce il tabellone di gioco, i giocatori, i turni e la logica di gioco.
 */
public class Partita {
    private Tabellone t = new Tabellone();
    private ArrayList<Imprevisto> imprev = new ArrayList<Imprevisto>();
    private ArrayList<Probabilita> prob = new ArrayList<Probabilita>();
    private HashMap<String, Player> giocatori = new HashMap<String, Player>();
    private int turno = 1;
    private Player currentPlayer = null;

    /**
     * Rappresenta una sessione di gioco di Monopoly.
     * Questa classe inizializza il gioco parsando i file XML per le carte e le
     * probabilità.
     * 
     * @throws Exception Se si verifica un errore durante il parsing del file XML
     */
    public Partita() throws Exception {
        Parser.parseCarteXml(t.getCaselle(), imprev, prob);
    }

    /**
     * Aggiungi un giocatore alla partita.
     * 
     * @param g Il giocatore da aggiungere
     * @return Una stringa contenente il codice di risposta e i dati del giocatore
     */
    public String addGiocatore(Player g) {
        String id = g.getID();
        giocatori.put(id, g);
        currentPlayer = giocatori.get("P#1");
        return "ADD;";
    }

    /**
     * Inzia la partita e ritorna una stringa contenente lo stato del gioco.
     * 
     * @return Una stringa contenente il codice di risposta e i dati dei giocatori
     */
    public String startGame() {
        Settings.GAME_STATUS = 0;
        String s = "START;P#1;" + getInfoAllPlayer();
        return s;
    }

    /**
     * Lancia i dadi e muove il giocatore in base al risultato del lancio dei dadi.
     * 
     * @return Il risultato del lancio dei dadi e lo spostamento del giocatore come
     */
    public String movePlayer(int dice1Roll, int dice2Roll) {
        // TODO: implementare la logica del doppio

        // controllo se il player è in prigione
        if (currentPlayer.getPosizione() == 10 && currentPlayer.InPrigione()) {
            // controllo se ha una carta uscita prigione
            if (currentPlayer.hasUscitaPrigione()) {
                // usa la carta
                currentPlayer.removeUscitaPrigione();
                // lo sposto in base al tiro dei dadi
                movePlayer(false, dice2Roll + dice1Roll);
                return "ROLL-1;" + getInfoAllPlayer()
                        + ";Sei uscito di prigione tramite una carta uscita prigione";
            } else {
                // se non ha una carta, controllo se ha fatto un doppio
                if (dice1Roll == dice2Roll) {
                    // se ha fatto un doppio lo sposto in base al tiro dei dadi
                    movePlayer(false, dice2Roll + dice1Roll);
                    return "ROLL-1;" + getInfoAllPlayer()
                            + ";Sei uscito di prigione tramite un doppio";
                } else {
                    // se non ha fatto un doppio e non ha ancora fatto 3 turni in prigione, aumento
                    if (currentPlayer.getTurniPrigione() < 3) {
                        currentPlayer.addTurnoPrigione();
                        return "ROLL-1;" + getInfoAllPlayer()
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
                    s = "ROLL-1;" + getInfoAllPlayer()
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
                    s = "ROLL-1;" + getInfoAllPlayer() + ";" + p.getNome()
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
                    s = "ROLL-1;" + getInfoAllPlayer() + ";" + i.getNome()
                            + "\r\n" + x;
                    // la elimino dal mazzo
                    imprev.remove(i);
                    break;
                case "T":
                    // tolgo i soldi al player
                    currentPlayer.setSoldi(currentPlayer.getSoldi()
                            + t.getCasellaByPos(currentPlayer.getPosizione()).getPrezzo());
                    s = "ROLL-1;" + getInfoAllPlayer() + ";Hai pagato "
                            + Math.abs(t.getCasellaByPos(currentPlayer.getPosizione()).getPrezzo()) + "€ di "
                            + t.getCasellaByPos(currentPlayer.getPosizione()).getNome();
                    break;
                case "GPG":
                    // sposto il player in prigione
                    movePlayer(true, 10);
                    s = "ROLL-1;" + getInfoAllPlayer()
                            + ";Sei finito in prigione";
                    break;
                default:
                    s = "ROLL-0;" + getInfoAllPlayer();
                    break;
            }
        } else {
            s = "ROLL-0;" + getInfoAllPlayer();
        }

        checkPosizione();
        return s;
    }

    /**
     * Questo metodo viene utilizzato per gestire il processo di acquisto di una
     * proprietà sulla scacchiera di gioco.
     * Controlla se il giocatore corrente può acquistare la proprietà e esegue le
     * azioni necessarie se possibile.
     * 
     * @return Una stringa che rappresenta il risultato del processo di acquisto. Il
     *         formato della stringa è il seguente:
     *         - "0;Non puoi acquistare la prigione" se il giocatore è nella cella
     *         della prigione e non può acquistarla.
     *         - "0;Soldi insufficienti" se il giocatore non ha abbastanza soldi per
     *         acquistare la proprietà.
     *         - "0;Casella non acquistabile" se la proprietà non è disponibile per
     *         l'acquisto.
     *         - "4;[currentPlayer.toString()]" se la proprietà viene acquistata con
     *         successo dal giocatore.
     */
    public String buyCasella() {
        Player g = currentPlayer;
        int pos = g.getPosizione();
        String s = "";

        // controllo se il player è in transito sulla prigione
        if (pos == 10) {
            s = "0;Non puoi acquistare la prigione";
            return s;
        }

        // controllare se la casella è acquistabile
        if (t.getCasellaByPos(pos).getPropietario() == "") {
            int prezzo = 0;
            boolean ipotecata = false;
            if (t.getCasellaByPos(pos).isIpotecata()) {
                prezzo = t.getCasellaByPos(pos).getValIpoteca();
                ipotecata = true;
            } else {
                prezzo = t.getCasellaByPos(pos).getPrezzo();
            }
            // controllare se il giocatore ha abbastanza soldi
            if (t.getCasellaByPos(pos).getPrezzo() <= g.getSoldi()) {
                // sottraggo i soldi al giocatore
                g.setSoldi(g.getSoldi() - prezzo);
                // aggiungo la casella alla lista delle proprieta del giocatore
                g.addProprieta(t.getCasellaByPos(pos).getID());
                // imposto il proprietario della casella
                t.getCasellaByPos(pos).setPropietario(g.getID());
                if (ipotecata) {
                    t.getCasellaByPos(pos).setIpotecata(false);
                }
                // rispondo al client
                s = "BUY;" + getInfoAllPlayer();
            } else {
                s = "0;Soldi insufficienti";
            }
        } else {
            s = "0;Casella non acquistabile";
        }

        return s;
    }

    /**
     * Questo metodo consente al giocatore corrente di ipotecare una proprietà sulla
     * scacchiera di gioco.
     * 
     * @param id L'ID della proprietà da ipotecare.
     * @return Una stringa che rappresenta la risposta all'azione di ipoteca.
     *         - Se la proprietà viene ipotecata con successo, la stringa inizierà
     *         con
     *         "6;"
     *         seguito dalle informazioni aggiornate del giocatore corrente.
     *         - Se la proprietà è già ipotecata, la stringa inizierà con "0;Casella
     *         già ipotecata".
     *         - Se il giocatore non possiede la proprietà, la stringa inizierà con
     *         "0;Non possiedi questa casella".
     */
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
                s = "IP;" + getInfoAllPlayer();
            } else {
                s = "0;Casella già ipotecata";
            }
        } else {
            s = "0;Non possiedi questa casella";
        }

        return s;
    }

    /**
     * Cambia il turno e restituisce una rappresentazione stringa del nuovo turno.
     * Controllo anche che il player non abbia finito i soldi
     * 
     * @return Una rappresentazione stringa del nuovo turno.
     */
    public String changeTurn() {
        if (currentPlayer.getSoldi() < 0) {
            giocatori.remove(currentPlayer.getID());
            if (giocatori.size() == 1) {
                // se è l'ultimo player rimasto, termino la partita
                Settings.GAME_STATUS = 1;
                return "END;" + currentPlayer.getID();
            }
        }
        turno++;
        String x;
        if (turno > Settings.PLAYER_COUNT) {
            x = (turno - Settings.PLAYER_COUNT) + "";
            turno = 1;
        } else {
            x = turno + "";
        }
        currentPlayer = giocatori.get("P#" + x);
        return "CH;" + currentPlayer.getID();
    }

    // TOOD: controllare carte tp
    /**
     * Esegue l'Imprevisto dato e restituisce un messaggio String.
     * 
     * @param i L'Imprevisto da eseguire.
     * 
     * @return Un messaggio String che descrive il risultato dell'esecuzione
     */
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
                } else if (typeCasella.equals("V")) {
                    // muovo il player al via
                    movePlayer(true, 0);
                    // gli do il bonus
                    currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
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

    /**
     * Esegue la Probabilita data e restituisce un messaggio String.
     * 
     * @param p La Probabilita da eseguire.
     * @return Un messaggio String che descrive il risultato dell'esecuzione della
     */
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
                } else if (typeCasella.equals("V")) {
                    // muovo il player al via
                    movePlayer(true, 0);
                    // gli do il bonus
                    currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
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

    /**
     * Ritorna una stringa che rappresenta la lista dei giocatori e il relativo ID
     * pedina.
     * La stringa contiene il numero di giocatori seguito dai loro nomi e dall'ID
     * della pedina (usato dal client per gestire la pedina),
     * separati da virgole.
     * Il formato della stringa è "7;nome1-id1,nome2-id2,nome3-id3,...".
     * 
     * @return una stringa che rappresenta la lista dei giocatori e il rispettivo id
     *         della pedina
     */
    public String getListaGiocatori() {
        String s = "LST;";

        for (Entry<String, Player> entry : giocatori.entrySet()) {
            Player value = entry.getValue();
            s += value.getNome() + "-" + value.getIDPedina() + ",";
        }
        s += "]";
        return s.replace(",]", "");
    }

    /**
     * Ritorna le informazioni di una casella specifica identificata dal suo ID.
     * Le informazioni includono il nome, il prezzo, il pedaggio , il valore
     * ipotecario ,
     * e se la casella è ipotecata (ipotecata).
     *
     * @param id l'ID della casella
     * @return una stringa contenente le informazioni della casella
     */
    public String getInfoCasella(String id) {
        // nome:prezzo:pedaggio:valIpoteca:proprietario:valCasa
        String s = "INFC;";
        Carta c = t.getCassellaByID(id);
        s += c.getNome() + ":" + c.getPrezzo() + ":" + c.getPedaggio() + ":" + c.getValIpoteca() + ":"
                + c.getPropietario();
        if (c.getClass() == Casella.class) {
            s += ":" + ((Casella) c).getValCasa();
        }
        return s;
    }

    /**
     * Muovi il giocatore alla posizione specificata.
     * 
     * @param goTo true se il giocatore deve andare direttamente alla posizione
     *             specificata, false se il giocatore deve avanzare di un numero
     *             specificato di posizioni.
     * @param pos  la posizione in cui spostare il giocatore o il numero di
     *             posizioni da avanzare.
     */
    private void movePlayer(boolean goTo, int pos) {
        // metodo che muove il player
        if (goTo)
            currentPlayer.setPosizione(pos);
        else
            currentPlayer.setPosizione(currentPlayer.getPosizione() + pos);

        checkPosizione();
    }

    /**
     * Metodo che controlla la posizione del giocatore per determinare se deve fare
     * qualcosa.
     */
    private void checkPosizione() {
        // controllo se il player ha fatto un giro completo
        if (currentPlayer.getPosizione() > 39) {
            currentPlayer.setPosizione(currentPlayer.getPosizione() - 40);
            currentPlayer.setSoldi(currentPlayer.getSoldi() + 200);
        }
        // controllo se la casella ha già un proprietario
        if (t.getCasellaByPos(currentPlayer.getPosizione()).getPropietario() != "") {
            // se ha un proprietario, paga il pedaggio
            currentPlayer.setSoldi(currentPlayer.getSoldi()
                    - t.getCasellaByPos(currentPlayer.getPosizione()).getPedaggio());

            // controllo se il player ha abbastanza soldi
            if (currentPlayer.getSoldi() < 0) {
                // se non ha abbastanza soldi, lo elimino dalla partita
                giocatori.remove(currentPlayer.getID());
                // controllo se è l'ultimo player rimasto
                if (giocatori.size() == 1) {
                    // se è l'ultimo player rimasto, termino la partita
                    Settings.GAME_STATUS = 1;
                }
            } else {
                // pago il proprietario della casella
                Player g = giocatori.get(t.getCasellaByPos(currentPlayer.getPosizione()).getPropietario());
                g.setSoldi(g.getSoldi() + t.getCasellaByPos(currentPlayer.getPosizione()).getPedaggio());
            }
        }
    }

    private String getInfoAllPlayer() {
        String s = "";
        for (Entry<String, Player> entry : giocatori.entrySet()) {
            Player value = entry.getValue();
            s += value.toString() + ";";
        }
        s += "#";
        return s.replace(";#", "");
    }

}