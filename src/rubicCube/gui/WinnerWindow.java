package rubicCube.gui;

import javax.swing.*;
import java.awt.*;

public class WinnerWindow extends JDialog {

    private int turnCount;
    private JPanel container;
    private JPanel bottom;
    private JButton newGame;
    private JButton close;
    private JLabel messageLabel;
    private static String message = "Skvěle! Podařilo se vám kostku složit - počet tahů - ";
    private static Font h1 = new Font("Arial", Font.BOLD, 22);

    public WinnerWindow(JFrame parent, int turnCount) {
        super(parent, "Výborně", true);
        this.turnCount = turnCount;
        container = new JPanel(new BorderLayout());
        bottom = new JPanel(new BorderLayout());
        newGame = new JButton("Nová hra");
        close = new JButton("Zavřít");
        messageLabel = new JLabel(message + turnCount);
        messageLabel.setFont(h1);
        bottom.add(newGame, BorderLayout.WEST);
        bottom.add(close, BorderLayout.EAST);
        container.add(bottom, BorderLayout.SOUTH);
        container.add(messageLabel, BorderLayout.CENTER);
        add(container);
        setVisible(true);
    }
}
