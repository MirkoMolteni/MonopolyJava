package src.client.gamePanels;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
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

    public board() {
        try {
            image = ImageIO.read(board);
            image = image.getScaledInstance(screenSize.height - 50, screenSize.height - 50, Image.SCALE_SMOOTH);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, screenSize.height - 50, screenSize.height - 50, null);
    }   
}
