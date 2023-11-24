package src.client.gamePanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class buttons extends JPanel{
    protected JButton purchaseButton;
    protected JButton drawButton;
    protected JButton ipotecaButton;
    protected JButton improveButton;
    protected JButton rollDice;
    protected JButton endTurn;
    protected JButton pauseButton;

    public buttons() {
        JPanel panel = new JPanel();

        purchaseButton = new JButton("Compra");
        ipotecaButton = new JButton("Ipoteca carta");
        improveButton = new JButton("Migliora");
        rollDice = new JButton("Tira i dadi");
        endTurn = new JButton("Termina turno");

        panel.add(rollDice);
        panel.add(endTurn);
        panel.add(purchaseButton, BorderLayout.CENTER);
        panel.add(ipotecaButton);
        panel.add(improveButton);

        this.setMaximumSize(panel.getMaximumSize());

        this.add(panel);

        // buttonConnetti.addActionListener(new ActionListener() {
        purchaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        ipotecaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

		improveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

        rollDice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        endTurn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        purchaseButton.setEnabled(false);
        endTurn.setEnabled(false);
        improveButton.setEnabled(false);
        rollDice.setEnabled(false);
        ipotecaButton.setEnabled(false);

        this.setVisible(true);
    }
}
