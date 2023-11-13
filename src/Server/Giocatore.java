package src.Server;

import java.util.ArrayList;
import src.Server.Carte.*;

public class Giocatore {
    String nome;
    int soldi;
    int posizione;

    // lista degli id delle proprieta possedute
    ArrayList<Carta> proprieta;
    // lista degli id delle carte uscite di prigione possedute
    ArrayList<Carta> uscitePrigione;

    public Giocatore(String nome) {
        this.nome = nome;
        this.soldi = Settings.STARTING_MONEY;
        this.posizione = 0;
        this.proprieta = new ArrayList<>();
        this.uscitePrigione = new ArrayList<>();
    }

    public int lanciaDadi() {
        return (int) (Math.random() * Settings.MAX_ROLL);
    }
}
