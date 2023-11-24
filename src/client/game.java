package src.client;

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

public class game extends JPanel {
    private board board;
    private buttons buttons;
    private dice dice;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int)screenSize.getWidth();
    private int height = (int)screenSize.getHeight();
    
    public game() {
        // creo il frame che conterrà tutti gli elementi del gioco
        JFrame frame = new JFrame("Monopoly - In partita");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        
        // pannello per il tabellone
        board = new board();
        frame.add(board);

        // frame laterale
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        buttons = new buttons();
        sidePanel.add(buttons);

        dice = new dice();
        sidePanel.add(dice);
        sidePanel.add(Box.createRigidArea(new Dimension(10, 100)));
        sidePanel.setOpaque(false);
        frame.add(sidePanel);    // BorderLayout.EAST
        
        frame.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }
}
