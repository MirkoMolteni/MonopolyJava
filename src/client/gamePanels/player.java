package src.client.gamePanels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class player extends JPanel {
    private JPanel panel;
    private JScrollPane scrollPane;
    private JPanel playerPanel;
    private JTextArea textArea;

    public player() {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(500, 310));
        this.setMaximumSize(new Dimension(500, 310));

        panel = new JPanel();
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        panel = new JPanel();
        playerPanel = new JPanel();
        
        playerPanel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(playerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.setPreferredSize(new Dimension(100, 300));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.black);
        panel.add(scrollPane);
        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(380, 300));
        textArea.setEditable(false);
        
        this.add(panel);
        this.add(textArea);
    }
}
