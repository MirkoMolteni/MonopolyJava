package src.client;

import javax.swing.*;
import java.awt.*;

public class mainMenu {
    public static void main(String[] args) {
        // Creazione della finestra
        JFrame frame = new JFrame("Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Creazione del logo in SVG
        ImageIcon logoIcon = new ImageIcon("C:/Scuola/5AI/Progettazione/MonopolyJava/client/logo.svg");
        JLabel logo = new JLabel(logoIcon);

        // Creazione della textbox per l'indirizzo IP
        JLabel ipLabel = new JLabel("Indirizzo IP:");
        JTextField ipField = new JTextField(20);

        // Creazione della textbox per la porta
        JLabel portLabel = new JLabel("Porta:");
        JTextField portField = new JTextField(5);

        // Creazione del pulsante "Connetti"
        JButton connectButton = new JButton("Connetti");

        // Creazione del pannello e aggiunta dei componenti
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(logo, constraints);
        constraints.gridy = 1;
        panel.add(ipLabel, constraints);
        constraints.gridx = 1;
        panel.add(ipField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(portLabel, constraints);
        constraints.gridx = 1;
        panel.add(portField, constraints);
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(connectButton, constraints);

        // Aggiunta del pannello alla finestra e visualizzazione
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
