package src.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class lobby extends JFrame {
        // ottengo l'istanza di netUtil - init
        netUtil net = netUtil.getInstance();
        
        // variabile di appoggio per i player totali 
        int totalPlayers = 0;

    public lobby() {
        // creazione frame
        JFrame frame = new JFrame("Monopoly - Lobby");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(clientConfig.resWmenu, clientConfig.resHmenu);
        frame.setMaximumSize(new Dimension(clientConfig.resWmenu, clientConfig.resHmenu));
        frame.setResizable(false);

        // creazione pannello sfondo - immagine
        ImageIcon backgroundImage = new ImageIcon("src/client/resources/bg.png");
        JLabel mainLabel = new JLabel(backgroundImage);
        mainLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // creazione immagine logo
        ImageIcon logoImage = new ImageIcon("src/client/resources/logo.png");
        JLabel logoLabel = new JLabel(logoImage);
        logoLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // creazione layoutmanager
        GridBagLayout layout = new GridBagLayout();
        mainLabel.setLayout(layout);

        // parse delle informazioni sui player
        net.send("7;");
        String data = "";
        try {
            data = net.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = data.substring(2);
        System.out.println(data);
        String[] players = data.split(",");

        for (String p : players) {
            totalPlayers++;
            String[] dati = p.split("-");
            JPanel playerInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            JLabel nome = new JLabel(dati[0]);
            nome.setFont(new Font("Arial", Font.PLAIN, 14));
            ImageIcon playerIcon = new ImageIcon("src/client/resources/pedine/" + dati[1] + ".png");
            Image playerImage = playerIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledPlayerIcon = new ImageIcon(playerImage);
            JLabel iconLabel = new JLabel(scaledPlayerIcon);

            playerInfoPanel.add(nome);
            playerInfoPanel.add(iconLabel);

            //playerPanel.add(playerInfoPanel);
        }

        // creazione bottoni 
        JButton buttonConnetti = new JButton("Aggiorna lista giocatori");
        buttonConnetti.setBackground(new Color(176, 0, 20)); 
        buttonConnetti.setForeground(Color.WHITE);
        buttonConnetti.setFont(new Font("Tahoma", Font.BOLD, 20));

        JButton buttonCambia = new JButton("Avvia partita!");
        buttonCambia.setBackground(new Color(225, 0, 25));
        buttonCambia.setForeground(Color.WHITE);
        buttonCambia.setFont(new Font("Tahoma", Font.PLAIN, 15));
        frame.add(mainLabel);

        // visualizza finestra
        frame.setVisible(true);
    }
}
