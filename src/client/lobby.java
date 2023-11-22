package src.client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class lobby extends JFrame {
    netUtil net = netUtil.getInstance();

    public lobby() {
        JFrame frame = new JFrame("Lobby - Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(clientConfig.resWmenu, clientConfig.resHmenu);
        frame.setMaximumSize(new Dimension(clientConfig.resWmenu, clientConfig.resHmenu));
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        BufferedImage backgroundImage;
        try {
            backgroundImage = ImageIO.read(new File("src/client/resources/bg.png"));
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            frame.setContentPane(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        } 

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Monopoly - Lobby pre-partita");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        JPanel subtitlePanel = new JPanel();
        subtitlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel subtitleLabel = new JLabel("Connected Players:");
        subtitleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        subtitlePanel.add(subtitleLabel);

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(0, 1));

        // Parse player information received from the serverù
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

            playerPanel.add(playerInfoPanel);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton reloadButton = new JButton("Aggiorna lista giocatori");
        JButton startGameButton = new JButton("Avvia partita");

        buttonPanel.add(reloadButton);
        buttonPanel.add(startGameButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(subtitlePanel, BorderLayout.CENTER);
        mainPanel.add(playerPanel, BorderLayout.WEST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public static void init() {
        SwingUtilities.invokeLater(() -> new lobby());
    }
}
