package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class mainMenu extends JFrame {
    int pedinaIndex = 0;

    public mainMenu() {
        // ottengo l'istanza di netUtil - init
        netUtil net = netUtil.getInstance();

        // creazione frame
        JFrame frame = new JFrame("Monopoly");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(clientConfig.resWmenu, clientConfig.resHmenu);
        frame.setMaximumSize(new Dimension(clientConfig.resWmenu, clientConfig.resHmenu));
        frame.setResizable(false);

        // creazione pannello sfondo - immagine
        ImageIcon backgroundImage = new ImageIcon("src/client/resources/bg.png");
        JLabel mainLabel = new JLabel(backgroundImage);
        mainLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // creazione immagine logo
        ImageIcon logoImage = new ImageIcon("src/client/resources/logo.png");
        JLabel logoLabel = new JLabel(logoImage);
        logoLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        // creazione layoutmanager
        GridBagLayout layout = new GridBagLayout();
        mainLabel.setLayout(layout);

        // creazione label
        JLabel labelIP = new JLabel("IP:");
        JLabel labelPorta = new JLabel("Porta:");
        JLabel labelUser = new JLabel("Nome:");
        JLabel labelPedina = new JLabel("Pedina:");

        // creazione textbox
        JTextField textBoxIP = new JTextField();
        JTextField textBoxPorta = new JTextField();
        JTextField textBoxNome = new JTextField();

        // MARK: valori default - debug
        textBoxIP.setText("127.0.0.1");
        textBoxPorta.setText("8080");
        textBoxNome.setText("test");

        // creazione bottone
        JButton buttonConnetti = new JButton("Entra nel server");
        buttonConnetti.setBackground(new Color(225, 0, 25));
        buttonConnetti.setForeground(Color.WHITE);
        buttonConnetti.setFont(new Font("Tahoma", Font.BOLD, 20));

        // gestione carousel di immagini per selezione pedina
        JButton buttonCambia = new JButton("cambia pedina");
        buttonCambia.setBackground(new Color(176, 0, 20));
        buttonCambia.setForeground(Color.WHITE);
        buttonCambia.setFont(new Font("Tahoma", Font.PLAIN, 15));

        ImageIcon pedine[] = new ImageIcon[clientConfig.nPedine];
        for (int i = 0; i < pedine.length; i++) {
            pedine[i] = new ImageIcon("src/client/resources/pedine/" + i + ".png");
            pedine[i].getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        }
        ImageIcon tmp = new ImageIcon(pedine[0].getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JLabel pedinaLabel = new JLabel(tmp);
        pedinaLabel.setBounds(0, 0, 50, 50);

        // creazione constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        // aggiunta delle componenti al pannello di sfondo con le constraints
        // so che è brutto ma con il borderLayout scompariva tutto - e comunque funziona
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        mainLabel.add(logoLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        mainLabel.add(labelIP, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        mainLabel.add(textBoxIP, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        mainLabel.add(labelPorta, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        mainLabel.add(textBoxPorta, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        mainLabel.add(labelUser, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        mainLabel.add(textBoxNome, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        mainLabel.add(labelPedina, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        mainLabel.add(pedinaLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        mainLabel.add(buttonCambia, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 2;
        mainLabel.add(buttonConnetti, constraints);

        // listener per avviare connessione
        buttonConnetti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // stabilisco la connessione TCP
                try {
                    net.connect(textBoxIP.getText(), Integer.parseInt(textBoxPorta.getText()));
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                }
                // creo il giocatore (stampo output) e rimando alla lobby
                net.send("1;" + textBoxNome.getText() + ";" + pedinaIndex);
                try {
                    System.out.println(net.receive());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                if (net.getStatus()) {
                    // chiudo la finestra
                    frame.dispose();
                    // avvio la lobby
                    lobby l = new lobby();
                } else {
                    // mostro errore se non connesso
                    String msg = "Errore di connessione: " + net.getLastError();
                    JOptionPane.showMessageDialog(frame, msg, "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // listener per il cambio pedina
        buttonCambia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (pedinaIndex == 0) {
                    pedinaIndex = pedine.length - 1;
                } else {
                    pedinaIndex--;
                    ImageIcon tmp = new ImageIcon(
                            pedine[pedinaIndex].getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                    pedinaLabel.setIcon(tmp);
                }
            }
        });

        // aggiungo il panel dello sfondo al frame
        frame.add(mainLabel);

        // visualizza finestra
        frame.setVisible(true);
    }
}
