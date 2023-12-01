package src.client.gamePanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import src.client.condivisa;
import src.client.player;

public class playerPanel extends JPanel {
    private JPanel panel;
    private JScrollPane scrollPane;
    private static JPanel playerPanel;
    private JTextArea textArea;
    private ArrayList<player> playerList = new ArrayList<player>();

    private condivisa condivisa = src.client.condivisa.getInstance();

    public playerPanel() {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(500, 310));
        this.setMaximumSize(new Dimension(500, 310));

        panel = new JPanel();
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        panel = new JPanel();
        playerPanel = new JPanel();
        playerList = condivisa.getPlayerList();

        // playerPanel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(playerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // aggiungi i player allo scrollpane
        for (player player : playerList) {
            JButton button = new JButton(player.getNome());
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textArea.setText("Info giocatore:" + "\n" + "Nome: " + player.getNome() + "\n" +
                            "ID: " + player.getID() + "\n" +
                            "Saldo: " + player.getSaldo() + "\n" +
                            "Casella: " + player.getPosizione() + "\n");
                    // "proprietà: " + player.getProprieta() + "\n" +
                    // "cartePrigione: " + player.getCartePrigione() + "\n");
                }
            });
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
            playerPanel.add(button);
        }

        panel.setPreferredSize(new Dimension(100, 300));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);
        panel.add(scrollPane);
        textArea.setPreferredSize(new Dimension(380, 300));
        textArea.setEditable(false);

        this.add(panel);
        this.add(textArea);
    }

    // funzione chiamata da varie classi per aggiornare i nuovi dati
    public static void updatePlayerPanel() {
        // Aggiorna gli elementi nel playerPanel
        playerPanel.revalidate();
        playerPanel.repaint();
    }
}
