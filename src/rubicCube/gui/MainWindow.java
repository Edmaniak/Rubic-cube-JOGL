package rubicCube.gui;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import rubicCube.App;
import rubicCube.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

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

        add(gui, BorderLayout.NORTH);

    }
}
