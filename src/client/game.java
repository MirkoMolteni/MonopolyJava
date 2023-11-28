package src.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import src.client.gamePanels.board;
import src.client.gamePanels.buttons;
import src.client.gamePanels.dice;
import src.client.gamePanels.player;

public class game extends JFrame {
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
        dadi = new dice();
        giocatore = new player();

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
        rightPanel.add(giocatore);
        centerPanel.add(Box.createVerticalGlue());

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
