package src.client.gamePanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import src.client.client;
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

        // buttonConnetti.addActionListener(new ActionListener() {
        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            //Acquisto casella
            //Il client invia: BUY;
            //Il server risponde: 0;TESTOERRORE = errore
            //BUY;INFOTUTTIPLAYER
            }
        });

        ipotecaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            net.send("IP;"); //current casella
            String data = "";
            try {
                data = net.receive();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            String[] splittedData = data.split(";");
            condivisa.parsePlayers(splittedData[1]);
            }
        });

        improveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                if(data.charAt(5) == 0) {
                    // proprietà
                }else if (data.charAt(5) == 1) {
                    String testo = splittedData[2];
                }

                condivisa.parsePlayers(splittedData[1]);
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
                System.out.println("è il turno di " + splittedData[1]);
            }
        });

        this.setVisible(true);
    }

}
