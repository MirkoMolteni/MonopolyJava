package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class mainMenu extends JFrame {
    public mainMenu(){
        // ottengo l'istanza di netUtil - init
        netUtil net = netUtil.getInstance();

        // creazione frame
        JFrame frame = new JFrame("Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setMaximumSize(new Dimension(1280, 720));
        frame.setResizable(false);

        // creazione pannello sfondo - immagine
        ImageIcon backgroundImage = new ImageIcon("src/client/resources/bg.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // creazione immagine logo
        ImageIcon logoImage = new ImageIcon("src/client/resources/logo.png");
        JLabel logoLabel = new JLabel(logoImage);
        logoLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // creazione layoutmanager
        GridBagLayout layout = new GridBagLayout();
        backgroundLabel.setLayout(layout);

        // creazione label
        JLabel labelIP = new JLabel("IP:");
        JLabel labelPorta = new JLabel("Porta:");
        JLabel labelUser = new JLabel("Nome:");

        // creazione textbox
        JTextField textBoxIP = new JTextField();
        JTextField textBoxPorta = new JTextField();
        JTextField textBoxNome = new JTextField();

        // MARK: valori default - debug 
        textBoxIP.setText("127.0.0.1");
        textBoxPorta.setText("8080");
        textBoxNome.setText("test");

        // creazione bottone
        JButton button = new JButton("Connetti");

        // creazione constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // aggiunta delle componenti al pannello di sfondo con le constraints
        // so che è brutto ma con il borderLayout scompariva tutto - e comunque funziona
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        backgroundLabel.add(logoLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        backgroundLabel.add(labelIP, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        backgroundLabel.add(textBoxIP, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        backgroundLabel.add(labelPorta, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        backgroundLabel.add(textBoxPorta, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        backgroundLabel.add(labelUser, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        backgroundLabel.add(textBoxNome, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        backgroundLabel.add(button, constraints);

        // listener per avviare connessione
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // stabilisco la connessione TCP
                try {
                    net.connect(textBoxIP.getText(), Integer.parseInt(textBoxPorta.getText()));
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
                // creo il giocatore (stampo output) e rimando alla lobby
                net.send("1;" + textBoxNome.getText());
                try {
                    System.out.println(net.receive());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //lobby lobby = new lobby();
            }
        });

        // aggiungo il panel dello sfondo al frame
        frame.add(backgroundLabel);

        // visualizza finestra
        frame.setVisible(true);
    }
}
