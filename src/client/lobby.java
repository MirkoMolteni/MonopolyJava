package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class lobby extends JFrame {
    // ottengo l'istanza di netUtil - init
    netUtil net = netUtil.getInstance();

    // variabili di appoggio
    int totalPlayers = 0;
    int constraintsCounter = 1;

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

        // creazione layoutmanager
        GridBagLayout layout = new GridBagLayout();
        mainLabel.setLayout(layout);

        // creazione constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // immagine logo
        ImageIcon logoImage = new ImageIcon("src/client/resources/logo.png");
        JLabel logoLabel = new JLabel(logoImage);
        logoLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        mainLabel.add(logoLabel, constraints);

        // parse delle informazioni ricevute dal server sui player
        net.send("LST;");
        String data = "";
        try {
            data = net.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = data.substring(4);
        System.out.println(data);
        String[] players = data.split(",");

        // rappresento i singoli player + pedine
        for (String p : players) {
            totalPlayers++;
            String[] dati = p.split("-");
            JPanel playerInfoPanel = new JPanel();

            JLabel nome = new JLabel(dati[0]);
            nome.setFont(new Font("Arial", Font.PLAIN, 14));
            ImageIcon playerIcon = new ImageIcon("src/client/resources/pedine/" + dati[1] + ".png");
            Image playerImage = playerIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            ImageIcon scaledPlayerIcon = new ImageIcon(playerImage);
            JLabel iconLabel = new JLabel(scaledPlayerIcon);

            playerInfoPanel.add(iconLabel);
            playerInfoPanel.add(nome);

            constraints.gridx = 0;
            constraints.gridy = constraintsCounter;
            constraintsCounter++;
            mainLabel.add(playerInfoPanel, constraints);
        }

        // creazione bottoni
        JButton buttonAggiorna = new JButton("Aggiorna lista giocatori");
        buttonAggiorna.setBackground(new Color(225, 0, 25));
        buttonAggiorna.setForeground(Color.WHITE);
        buttonAggiorna.setFont(new Font("Tahoma", Font.PLAIN, 15));

        JButton buttonAvvia = new JButton("Avvia partita!");
        buttonAvvia.setBackground(new Color(225, 0, 25));
        buttonAvvia.setForeground(Color.WHITE);
        buttonAvvia.setFont(new Font("Tahoma", Font.BOLD, 15));

        // Aggiungi i bottoni al pannello principale
        constraints.gridx = 0;
        constraints.gridy = constraintsCounter + 1;
        constraints.gridwidth = 1;
        mainLabel.add(buttonAggiorna, constraints);

        constraints.gridx = 1;
        constraints.gridy = constraintsCounter + 1;
        constraints.gridwidth = 1;
        mainLabel.add(buttonAvvia, constraints);

        // aggiungi i listener ai bottoni
        buttonAggiorna.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // so che è bruttino ma funziona e non saprei come ricreare quella grid orribile in alto
                frame.dispose();
                lobby ci = new lobby();
            }
        });

        buttonAvvia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (net.getStatus()) {
                    // chiudo la finestra
                    frame.dispose();
                    // avvio la partita
                    game g = new game();
                } else {
                    // mostro errore se non connesso
                    String msg = "Errore di connessione: " + net.getLastError();
                    JOptionPane.showMessageDialog(frame, msg, "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // aggiungo il label principale alla finestra
        frame.add(mainLabel);

        // visualizza finestra
        frame.setVisible(true);
    }
}