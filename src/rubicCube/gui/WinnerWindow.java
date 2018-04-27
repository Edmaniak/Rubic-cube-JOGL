package rubicCube.gui;

import rubicCube.app.App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WinnerWindow extends JDialog {

    private int turnCount;
    private JPanel container;
    private JPanel bottom;
    private JButton newGame;
    private JButton close;
    private JLabel messageLabel;
    private static String message = "Skvěle! Podařilo se Vám kostku složit v ";
    private static Font h1 = new Font("Arial", Font.BOLD, 20);

    public WinnerWindow(JFrame parent, int turnCount) {
        super(parent, "Výborně", true);
        this.turnCount = turnCount;
        container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(30, 30, 30, 30));
        bottom = new JPanel(new GridLayout(1,2));
        bottom.setBorder(new EmptyBorder(30,0,0,0));
        newGame = new JButton("Nová hra");
        close = new JButton("Zavřít");
        messageLabel = new JLabel(message + App.getGameManager().getTurnCount() + " tazích", JLabel.CENTER);
        messageLabel.setFont(h1);
        bottom.add(newGame);
        bottom.add(close);
        container.add(bottom, BorderLayout.SOUTH);
        container.add(messageLabel, BorderLayout.CENTER);
        add(container);
        pack();
        setVisible(true);
    }
}
