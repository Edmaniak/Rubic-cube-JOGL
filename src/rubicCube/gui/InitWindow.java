package rubicCube.gui;

import rubicCube.app.App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Dialog that is showed in the beginning of the game runtime.
 */
public class InitWindow extends JDialog {

    private final JButton start;
    private final JButton close;
    private final JButton mix;
    private final JLabel title;
    private final JPanel container;
    private final JPanel bottom;
    private final JPanel center;
    private final JTextField size = new JTextField("5.0", 2);
    private final JTextField count = new JTextField("3", 2);
    private final JTextField space = new JTextField("0.2", 2);
    private final JTextField random = new JTextField("3", 2);
    private static final Font h1 = new Font("Arial", Font.BOLD, 22);
    private static final String message = "Vítejte v simulaci rubikovi kostky";

    public InitWindow(JFrame parent) {
        super(parent, "Nastavení modelu", true);
        setLocationRelativeTo(parent);
        container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(30, 30, 30, 30));
        bottom = new JPanel(new GridLayout(1, 3));
        center = new JPanel(new GridLayout(4, 2));

        center.add(new JLabel("Velikost kostek: "));
        center.add(size);
        center.add(new JLabel("Mezera mezi kostkami: "));
        center.add(space);
        center.add(new JLabel("Nahodilost rozmixování"));
        center.add(random);

        start = new JButton("Generovat kostku");
        start.addActionListener(e -> {
            try {

                float sizeV = Float.parseFloat(size.getText());
                float spaceV = Float.parseFloat(space.getText());
                App.getRubicCube().generateStructure(spaceV, sizeV);
                App.getHelpWindow().setVisible(true);
                setVisible(false);

            } catch (Exception ex) {
                JOptionPane.showConfirmDialog(this, "Vyplněná pole musí obsahovat jen čísla", "Chyba", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            }

        });

        close = new JButton("Zavřít aplikaci");
        close.addActionListener(e -> parent.dispose());

        mix = new JButton("Generovat a namixovat");
        mix.addActionListener(e -> {
            start.doClick();
            try {
                int steps = Integer.parseInt(random.getText());
                App.getRubicCube().shuffle(steps);
            } catch (Exception ex) {
                JOptionPane.showConfirmDialog(this, "Vyplněná pole musí obsahovat jen čísla", "Chyba", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        });

        bottom.add(start);
        bottom.add(mix);
        bottom.add(close);

        title = new JLabel(message);
        title.setFont(h1);
        title.setBorder(new EmptyBorder(0, 0, 30, 0));

        container.add(title, BorderLayout.NORTH);
        container.add(bottom, BorderLayout.SOUTH);
        container.add(center, BorderLayout.CENTER);

        add(container);
        pack();
        setVisible(true);
    }
}
