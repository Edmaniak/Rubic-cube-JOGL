package rubicCube.gui;

import rubicCube.app.App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dialog that is showed when the cube is solved
 */
public class WinnerWindow extends JDialog {

    private int turnCount;
    private final JPanel container;
    private final JPanel bottom;
    private final JButton newGame;
    private final JButton close;
    private JLabel messageLabel;
    private static final String message = "Skvěle! Podařilo se Vám kostku složit v ";
    private static final Font h1 = new Font("Arial", Font.BOLD, 20);

    public WinnerWindow(JFrame parent, int turnCount) {
        super(parent, "Výborně", true);
        setLocationRelativeTo(parent);
        this.turnCount = turnCount;
        container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(30, 30, 30, 30));
        bottom = new JPanel(new GridLayout(1,2));
        bottom.setBorder(new EmptyBorder(30,0,0,0));

        newGame = new JButton("Nová hra");
        close = new JButton("Zavřít");
        newGame.addActionListener(e -> {
            dispose();
            App.getInitWindow().setVisible(true);
        });
        close.addActionListener(e -> dispose());


        messageLabel = new JLabel(message + turnCount + " tazích", JLabel.CENTER);
        messageLabel.setFont(h1);
        bottom.add(newGame);
        bottom.add(close);
        container.add(bottom, BorderLayout.SOUTH);
        container.add(messageLabel, BorderLayout.CENTER);
        add(container);
        pack();
    }


    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        messageLabel.setText("");
    }
}
