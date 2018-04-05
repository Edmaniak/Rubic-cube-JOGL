package rubicCube;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import jogl01start.TestRenderer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

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

            // The canvas is the widget that's drawn in the JFrame
            GLCanvas canvas = new GLCanvas(capabilities);
            Renderer ren = new Renderer();
            canvas.addGLEventListener(ren);
            canvas.addMouseListener(ren);
            canvas.addMouseMotionListener(ren);
            canvas.addKeyListener(ren);
            canvas.setSize(1280, 768);

            JPanel gui = new JPanel();

            frame.add(gui, BorderLayout.NORTH);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new rubicCube.App().start());
    }
}