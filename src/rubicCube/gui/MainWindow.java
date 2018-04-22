package rubicCube.gui;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import rubicCube.app.App;
import rubicCube.app.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private int turnCount;
    private JLabel turn;

    public MainWindow(Renderer ren) {
        try {
            setSize(1280, 768);
            setTitle("Rubic's cube <PGRF2 UHK>");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // setup OpenGL Version 2
            GLProfile profile = GLProfile.get(GLProfile.GL2);
            GLCapabilities capabilities = new GLCapabilities(profile);
            capabilities.setRedBits(8);
            capabilities.setBlueBits(8);
            capabilities.setGreenBits(8);
            capabilities.setAlphaBits(8);
            capabilities.setDepthBits(24);
            capabilities.setSampleBuffers(true);

            // The canvas is the widget that's drawn in the JFrame
            GLCanvas canvas = new GLCanvas(capabilities);

            canvas.addGLEventListener(ren);
            canvas.addMouseListener(ren);
            canvas.addMouseMotionListener(ren);
            canvas.addKeyListener(ren);
            canvas.setSize(1280, 768);

            initGui();
            add(canvas, BorderLayout.CENTER);

            final FPSAnimator animator = new FPSAnimator(canvas, App.FPS, true);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    new Thread(() -> {
                        if (animator.isStarted()) animator.stop();
                        System.exit(0);
                    }).start();
                }
            });
            pack();
            setVisible(true);
            animator.start(); // start the animation loop


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGui() {
        JPanel gui = new JPanel(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        JPanel right = new JPanel();
        JPanel left = new JPanel();

        turn = new JLabel("Počet tahů: 0");
        infoPanel.add(turn);

        gui.add(right, BorderLayout.EAST);
        gui.add(left, BorderLayout.WEST);
        gui.add(infoPanel, BorderLayout.CENTER);

        JButton shuffle = new JButton("Rozmixovat");
        JButton menu = new JButton("Menu");
        JButton reset = new JButton("Reset");
        JButton debug = new JButton("Debug");
        JButton solve = new JButton("Vyřešit");

        shuffle.addActionListener(e -> App.getRubicCube().shuffle());
        reset.addActionListener(e -> App.reset());
        menu.addActionListener(e -> App.initWindow.setVisible(true));
        debug.addActionListener(e -> App.debug = !App.debug);


        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 60);
        JLabel speed = new JLabel("Rotating speed:");

        left.add(menu);
        left.add(shuffle);
        left.add(reset);
        left.add(debug);

        right.add(speed);
        right.add(speedSlider);

        add(gui, BorderLayout.NORTH);

    }

    public void increaseTurnCount() {
        turnCount++;
        turn.setText("Počet tahů: " + turnCount);
    }

    public void setTurnCount(int amount) {
        turnCount = amount;
        turn.setText("Počet tahů: " + turnCount);
    }
}
