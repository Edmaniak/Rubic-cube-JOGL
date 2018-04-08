package rubicCube;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App {
    private static final int FPS = 60; // animator's target frames per second
    public static boolean debug = true;
    public static JFrame frame;

    public void start() {
        try {
            frame = new JFrame();
            frame.setSize(1280, 768);
            frame.setTitle("Rubic's cube <PGRF2 UHK>");

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
            Renderer ren = new Renderer();
            canvas.addGLEventListener(ren);
            canvas.addMouseListener(ren);
            canvas.addMouseMotionListener(ren);
            canvas.addKeyListener(ren);
            canvas.setSize(1280, 768);

            initGui();
            frame.add(canvas, BorderLayout.CENTER);

            final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    new Thread(() -> {
                        if (animator.isStarted()) animator.stop();
                        System.exit(0);
                    }).start();
                }
            });
            frame.pack();
            frame.setVisible(true);
            animator.start(); // start the animation loop


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGui() {
        JPanel gui = new JPanel(new BorderLayout());
        JPanel right = new JPanel();
        JPanel left = new JPanel();

        gui.add(right, BorderLayout.EAST);
        gui.add(left, BorderLayout.WEST);

        JButton shuffle = new JButton("Shuffle");
        JButton reset = new JButton("Reset");
        JButton debug = new JButton("Debug");
        debug.addActionListener(e -> App.debug = !App.debug);
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        JLabel speed = new JLabel("Rotating speed:");

        left.add(shuffle);
        left.add(reset);
        left.add(debug);

        right.add(speed);
        right.add(speedSlider);

        frame.add(gui, BorderLayout.NORTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new rubicCube.App().start());
    }
}