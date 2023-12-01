package src.client.gamePanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import src.client.condivisa;
import src.client.netUtil;

public class buttons extends JPanel {
    protected JButton purchaseButton;
    protected JButton drawButton;
    protected JButton ipotecaButton;
    protected JButton improveButton;
    protected JButton rollDice;
    protected JButton endTurn;
    protected JButton pauseButton;

    public buttons(dice dadi) {
        netUtil net = netUtil.getInstance();
        condivisa condivisa = src.client.condivisa.getInstance();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        purchaseButton = new JButton("Compra");
        ipotecaButton = new JButton("Ipoteca carta");
        improveButton = new JButton("Migliora");
        rollDice = new JButton("Tira i dadi");
        endTurn = new JButton("Termina turno");

        panel.add(rollDice);
        panel.add(endTurn);
        panel.add(purchaseButton);
        panel.add(ipotecaButton);
        panel.add(improveButton);
        this.setMaximumSize(panel.getMaximumSize());

        purchaseButton.setEnabled(false);
        endTurn.setEnabled(false);
        improveButton.setEnabled(false);
        rollDice.setEnabled(true);
        ipotecaButton.setEnabled(false);

        this.add(panel);

        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Acquisto casella
                net.send("BUY;");
                String data = "";
                try {
                    data = net.receive();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                String[] splittedData = data.split(";");

                condivisa.parsePlayers(splittedData[1]);
                purchaseButton.setEnabled(false);

                // aggiorno il pannello dei player
                playerPanel.updatePlayerPanel();
                console.setText("Hai acquistato la casella!");
            }
        });

        ipotecaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: ri-ottieni la proprietà
                net.send("IP;"); // current casella
                String data = "";
                try {
                    data = net.receive();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                String[] splittedData = data.split(";");
                condivisa.parsePlayers(splittedData[1]);
                ipotecaButton.setEnabled(false);
                console.setText("Hai ipotecato la casella!");
            }
        });

        improveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // meccanica not yet implemented
            }
        });

        rollDice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String result = dadi.lancioDadi();
                net.send("ROLL;" + result);
                rollDice.setEnabled(false);
                String data = "";
                try {
                    data = net.receive();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }

                String[] splittedData = data.split(";");
                if (data.charAt(5) == 0) {
                    if (data.charAt(7) == 1) {
                        // comprabile
                        purchaseButton.setEnabled(true);
                    } else if (data.charAt(7) == 2){
                        // ipotecabile
                        purchaseButton.setEnabled(false);
                        ipotecaButton.setEnabled(true);
                    } else if (data.charAt(7) == 3){
                        // paga affitto
                        purchaseButton.setEnabled(false);
                        ipotecaButton.setEnabled(false);
                    }
                } else if (data.charAt(5) == 1) {
                    String testo = splittedData[3];
                    // altro tipo casella, da implementare
                    System.out.println(testo);
                }

                condivisa.parsePlayers(splittedData[1]);

                // aggiorno il pannello dei player
                playerPanel.updatePlayerPanel();
                console.setText("Hai tirato " + result + "!");
                endTurn.setEnabled(true);
            }
        });

        endTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                net.send("CH;");
                String data = "";
                try {
                    data = net.receive();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                String[] splittedData = data.split(";");
                endTurn.setEnabled(false);
                console.setText("E' il turno di " + splittedData[1] + "!");
            }
        });

        this.setVisible(true);
    }

}
