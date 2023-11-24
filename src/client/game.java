package src.client;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import src.client.gamePanels.board;

public class game extends JPanel {
    private board board;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int width = (int)screenSize.getWidth();
    private int height = (int)screenSize.getHeight();
    
    public game() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		board = new board();
		contentPane.setPreferredSize(new Dimension(width, height));
		//contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(board);  
    }
}
