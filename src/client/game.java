package src.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.client.gamePanels.board;
import src.client.gamePanels.buttons;
import src.client.gamePanels.console;
import src.client.gamePanels.dice;
import src.client.gamePanels.playerPanel;

public class game extends JFrame {
    // variabili d'istanza
    netUtil net = netUtil.getInstance();
    condivisa condivisa = src.client.condivisa.getInstance();
    
    private board tabellone;
    private buttons bottoni;
    private dice dadi;
    private playerPanel giocatore;
    private console console;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int)screenSize.getWidth();
    private int height = (int)screenSize.getHeight();
    
    public game() {
        // informo il server che voglio iniziare la partita e ottengo la risposta
        net.send("START;");
        String data = "";
        try {
            data = net.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // parse dei dati ricevuti dal server
        condivisa.parsePlayers(data);

        // inizializzo gli elementi da mettere nel tabellone
        tabellone = new board();
        dadi = new dice();
        giocatore = new playerPanel();
        console = new console();

        // creo il frame che conterrà tutti gli elementi del gioco
        JFrame frame = new JFrame("Monopoly - In partita");
        frame.getContentPane().setBackground(new Color(208, 233, 218));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);      

        // pannello per il tabellone
        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension((width / 100) * 60, height));
        
        leftPanel.add(tabellone);

        // pannello centrale per i bottoni e i dadi
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setPreferredSize(new Dimension((width / 100) * 10, height));
        
        // istanzio i bottoni passandogli i parametri necessari
        bottoni = new buttons(dadi);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(bottoni);
        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(dadi);
        centerPanel.add(Box.createVerticalGlue());


        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension((width / 100) * 30, height));
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(giocatore);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(console);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createVerticalGlue());

        frame.setLayout(new BorderLayout());
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
		
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setVisible(true);
	}

    public void paint(Graphics g) {
        super.paint(g);
    }
}
