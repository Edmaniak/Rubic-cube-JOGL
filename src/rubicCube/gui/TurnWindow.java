package rubicCube.gui;

import rubicCube.app.turn.Turn;
import rubicCube.model.cube.RubicCube;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Show the turn history based on standard notaion
 */
public class TurnWindow extends JDialog {

    private final JLabel moves;
    private final JLabel heading = new JLabel("Tahy dle standartní notace");
    private final Font h1 = new Font("Arial", Font.BOLD, 20);
    private String movesString;
    private final JPanel panel;
    private final RubicCube rubicCube;

    public TurnWindow(JFrame parent, RubicCube rubicCube) {
        super(parent, "Tahy", false);
        setLocationRelativeTo(parent);
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
