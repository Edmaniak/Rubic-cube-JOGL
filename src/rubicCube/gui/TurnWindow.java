package rubicCube.gui;

import rubicCube.app.Turn;
import rubicCube.model.RubicCube;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TurnWindow extends JDialog {

    private JLabel moves;
    private JLabel heading = new JLabel("Odehrané tahy dle standartní notace");
    private Font h1 = new Font("Arial", Font.BOLD, 20);
    private String movesString;
    private JPanel panel;
    private RubicCube rubicCube;

    public TurnWindow(JFrame parent, RubicCube rubicCube) {
        super(parent, "Tahy", false);
        this.rubicCube = rubicCube;
        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        heading.setFont(h1);
        panel.add(heading, BorderLayout.NORTH);
        moves = new JLabel(movesString,SwingConstants.CENTER);
        refresh();
        panel.add(moves, BorderLayout.CENTER);
        add(panel);
        pack();
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        refresh();
        pack();
    }

    public void refresh() {
        movesString = "";
        for (Turn turn : rubicCube.getTurns())
            movesString += turn.getNotation() + " ";
        moves.setText(movesString);
    }
}
