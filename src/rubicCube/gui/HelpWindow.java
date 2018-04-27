package rubicCube.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dialog that shows how the cube can be controlled
 */
public class HelpWindow extends JDialog {

    private JLabel helpTextLbl;
    private String text;
    private JPanel panel;

    public HelpWindow(JFrame parent) {
        super(parent, "Nápověda", false);
        panel = new JPanel(new BorderLayout());
        text = "<html>Rotování kamerou: <br>" +
                "Kurzorové šipky / myš <br> <br>" +
                "Rotace kostky po ose Y <br>" +
                "(3) [Q] VPRAVO - VLEVO [E] <br>" +
                "(2) [A] VRPAVO - VLEVO [D] <br>" +
                "(1) [Z] VPRAVO - VLEVO [C] <br> <br>" +
                "Rotace kostky po ose X  <br>" +
                "(3) [O] TAM - K SOBĚ [L] <br>" +
                "(2) [I] TAM - K SOBĚ [K] <br>" +
                "(1) [U] TAM - K SOBĚ [J] <br> <br>" +
                "Rotace kostky po ose Y  <br>" +
                "(3) [E] VPRAVO - VLEVO [R] <br>" +
                "(2) [D] VPRAVO - VLEVO [F] <br>" +
                "(1) [C] VPRAVO - VLEVO [V] <br> <br></html>";
        helpTextLbl = new JLabel(text, JLabel.CENTER);
        panel.add(helpTextLbl);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        add(panel);
        pack();
    }

}
