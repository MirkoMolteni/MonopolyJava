package src.client;

import java.util.ArrayList;

public class condivisa {
    private static condivisa instance;
    ArrayList<player> playerList = new ArrayList<player>();
    
    
    private condivisa() {}

    public static condivisa getInstance() {
        if (instance == null) {
            synchronized (condivisa.class) {
                if (instance == null) {
                    instance = new condivisa();
                }
            }
        }
        return instance;
    }

    public void parsePlayers(String data) {
        playerList.clear();
        data = data.substring(6);
        String[] players = data.split(";");
        for (String player : players) {
            // formato -> {P#2:PLY2:1500:0:[]:[,]}
            String[] playerData = player.split(":");

            String ID = playerData[0];
            String nome = playerData[1];
            int saldo = Integer.parseInt(playerData[2]);
            int posizione = Integer.parseInt(playerData[3]);
            String[] proprieta = playerData[4].substring(1, playerData[4].length() - 1).split(",");
            String[] cartePrigione = playerData[5].substring(1, playerData[5].length() - 1).split(",");

            player p = new player(ID, nome, saldo, posizione, proprieta, cartePrigione);
            playerList.add(p);
        }
    }
}
