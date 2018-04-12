package rubicCube.gui;

import rubicCube.App;
import rubicCube.model.RubicCube;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class InitWindow extends JDialog {

    private JButton start;
    private JButton close;
    private JLabel title;
    private JPanel container;
    private JPanel bottom;
    private JPanel center;
    private JTextField size = new JTextField("5.0", 2);
    private JTextField count = new JTextField("3", 2);
    private JTextField space = new JTextField("0.2", 2);
    private static Font h1 = new Font("Arial", Font.BOLD, 22);
    private static String message = "Vítejte v simulaci rubikovi kostky";

    public InitWindow(JFrame parent, RubicCube rubicCube) {
        super(parent, "Nastavení modelu", true);
        container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(30, 30, 30, 30));
        bottom = new JPanel(new BorderLayout());
        center = new JPanel(new GridLayout(5, 2));

        center.add(new JLabel("Velikost kostek: "));
        center.add(size);
        center.add(new JLabel("Počet kostek v segmentu: "));
        center.add(count);
        center.add(new JLabel("Mezera mezi kostkami: "));
        center.add(space);

        start = new JButton("Generovat kostku");
        start.addActionListener(e -> {
            try {

                float sizeV = Float.parseFloat(size.getText());
                int countV = Integer.valueOf(count.getText());
                float spaceV = Float.parseFloat(space.getText());
                rubicCube.generateNew(spaceV, sizeV);

                setVisible(false);

            } catch (Exception ex) {
                JOptionPane.showConfirmDialog(this, "Vyplněná pole musí obsahovat jen čísla", "Chyba", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
            }

        });

        close = new JButton("Zavřít aplikaci");
        close.addActionListener(e -> parent.dispose());

        bottom.add(start, BorderLayout.WEST);
        bottom.add(close, BorderLayout.EAST);

        title = new JLabel(message);
        title.setFont(h1);

        container.add(title, BorderLayout.NORTH);
        container.add(bottom, BorderLayout.SOUTH);
        container.add(center, BorderLayout.CENTER);

        add(container);
        pack();
        setVisible(true);
    }
}
