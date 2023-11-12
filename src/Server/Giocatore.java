package src.Server;

import java.util.ArrayList;

public class Giocatore {
    String nome;
    int soldi;
    // lista delle caselle che possiede
    ArrayList<Casella> proprietà;
    // lsita contente le carte bonus possedute
    ArrayList<Carta> uscitaPrigione;

    public Giocatore(String nome) {
        this.nome = nome;
        soldi = 1500;
        proprietà = new ArrayList<Casella>();
        uscitaPrigione = new ArrayList<Carta>();
    }
}
