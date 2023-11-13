package src.Server;

import java.util.ArrayList;
import src.Server.Carte.*;

public class Partita {
    private ArrayList<Carta> mappa = new ArrayList<Carta>();
    private ArrayList<Imprevisto> imprev = new ArrayList<Imprevisto>();
    private ArrayList<Probabilita> prob = new ArrayList<Probabilita>();
    private ArrayList<Giocatore> giocatori = new ArrayList<Giocatore>();

    public Partita() throws Exception {
        Parser.parseCarteXml(mappa, imprev, prob);
    }

    public void addGiocatore(Giocatore g) {
        giocatori.add(g);
    }

    public void startGame() {
        while (Settings.GAME_STATUS == 0) {

        }
    }
}
