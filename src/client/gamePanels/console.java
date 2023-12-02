package src.client.gamePanels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class console extends JPanel{
    private JPanel panel;
    private static JTextArea textArea;
    private static String text = "";

    public console() {
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(500, 310));
        this.setMaximumSize(new Dimension(500, 310));

        panel = new JPanel();
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setPreferredSize(new Dimension(500, 310));
        textArea.setEditable(false);
        panel.add(textArea);
        this.add(panel);
    }

    public static void setText(String data) {
        data = data + "\n";
        text += data;
        textArea.setText(text);
    }
}
