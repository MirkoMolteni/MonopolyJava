package src.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class lobby extends JFrame {
    netUtil net = netUtil.getInstance();

    public lobby() {
        JFrame frame = new JFrame("Lobby - Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setMaximumSize(new Dimension(1280, 720));
        frame.setResizable(false);

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BorderLayout());
        // Parse player information received from the server
        //net.send("7");
        String data = "7;nome1-1,nome2-2,nome3-3"; // PLACEHOLDER PRE-SERVER
        data = data.substring(2);
        System.out.println(data);
        String[] players = data.split(",");

        for (String p : players) {
            String[] dati = p.split("-");
            JLabel nome = new JLabel(dati[0]);
            ImageIcon playerIcon = new ImageIcon(dati[1] + ".png");
            JLabel iconLabel = new JLabel(playerIcon);
            playerPanel.add(nome, BorderLayout.NORTH);
            playerPanel.add(iconLabel, BorderLayout.CENTER);
        }

        JButton avviaPartita = new JButton("Avvia partita");
        playerPanel.add(avviaPartita, BorderLayout.SOUTH);

        frame.setContentPane(playerPanel);
        frame.setVisible(true);
    }
}