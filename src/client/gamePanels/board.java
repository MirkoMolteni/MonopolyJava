package src.client.gamePanels;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class board extends JPanel {
    // variabili d'istanza
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private File board = new File("src/client/resources/board.png");
    private Image image;
    private int boardHeight = screenSize.height - 100;
    private int boardWidth = screenSize.height - 100;
    //private JPanel imagePanel;

    public board() {        
        this.setPreferredSize(new Dimension(boardHeight, boardWidth));
        try {
            image = ImageIO.read(board);
            image = image.getScaledInstance(boardHeight, boardWidth, Image.SCALE_SMOOTH);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, boardHeight, boardWidth, null);
    }   
}
