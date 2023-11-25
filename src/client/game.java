package src.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.client.gamePanels.board;
import src.client.gamePanels.buttons;
import src.client.gamePanels.dice;
import src.client.gamePanels.player;

public class game extends JPanel {
    private board tabellone;
    private buttons bottoni;
    private dice dadi;
    private player giocatore;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int)screenSize.getWidth();
    private int height = (int)screenSize.getHeight();
    
    public game() {
        // inizializzo gli elementi da mettere nel tabellone
        tabellone = new board();
        bottoni = new buttons();
        dadi = new dice();
        giocatore = new player();

        // creo il frame che conterrà tutti gli elementi del gioco
        JFrame frame = new JFrame("Monopoly - In partita");
        frame.setBackground(new Color(187, 229, 206));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);      

        // pannello per il tabellone
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setPreferredSize(new Dimension(width, height));
        
        frame.add(tabellone);
        mainPanel.add(bottoni);
        mainPanel.add(dadi);
        mainPanel.add(giocatore);
		
		frame.add(mainPanel);
		setOpaque(false);

        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setVisible(true);
	}

    public void paint(Graphics g) {
        super.paint(g);
    }

    private int lancioDadi(){
        int dado1 = (int)(Math.random() * 6) + 1;
        int dado2 = (int)(Math.random() * 6) + 1;
        dadi.setDadi(dado1, dado2);
        return dado1 + dado2;
    }
}
