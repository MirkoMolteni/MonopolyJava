package src.client.gamePanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class dice extends JPanel {
    // variabili d'istanza
    private JPanel dado1, dado2;
    private ImageIcon imgDado1, imgDado2;
    private int dadoSize = 40;
    private JLabel dado1Label, dado2Label;
    private Image[] diceImages = new Image[7];

    public dice() {
        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension((3 * dadoSize), dadoSize));
        this.setBackground(Color.gray);

        Dimension panelSizeSquare = new Dimension(dadoSize, dadoSize);

        // ottengo le immagini dei dadi
        for(int i = 0; i < 7; i++) {
            File dado = new File("src/client/resources/dadi/" + i + ".jpg");
            Image img = null;
            try {
                img = ImageIO.read(dado);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img = img.getScaledInstance(((BufferedImage) img).getWidth() / 15, ((BufferedImage) img).getHeight() / 15, Image.SCALE_SMOOTH);
            diceImages[i] = img;
        }

        // creo i pannelli per i dadi
        dado1 = new JPanel();
        dado2 = new JPanel();

        // panello dado 1
        dado1.setMaximumSize(panelSizeSquare);
        dado1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        imgDado1 = new ImageIcon(diceImages[1]);

        dado1Label = new JLabel();
        dado1Label.setIcon(imgDado1);
        dado1.add(dado1Label);
        this.add(dado1, BorderLayout.WEST);

        // panello dado 2
        dado2.setMaximumSize(panelSizeSquare);
        dado2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        imgDado2 = new ImageIcon(diceImages[2]);
        dado2Label = new JLabel();
        dado2Label.setIcon(imgDado2);
        dado2.add(dado2Label);
        this.add(dado2, BorderLayout.CENTER);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    private void setDadi(int dado1, int dado2) {
        imgDado1.setImage(diceImages[dado1]);
        imgDado2.setImage(diceImages[dado2]);
        repaint();
    }

    public String lancioDadi(){
        Random r = new Random();
        int dado1 = r.nextInt(6) + 1;
        int dado2 = r.nextInt(6) + 1;
        setDadi(dado1, dado2);
        return dado1 + ";" + dado2;
    }
}
