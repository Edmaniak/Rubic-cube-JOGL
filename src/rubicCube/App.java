package rubicCube;

import rubicCube.gui.InitWindow;
import rubicCube.gui.MainWindow;
import rubicCube.gui.WinnerWindow;
import rubicCube.model.RubicCube;

import javax.swing.*;

public class App {
    public static final int FPS = 60; // animator's target frames per second
    public static boolean debug = false;

    public static MainWindow mainWindow;
    public static InitWindow initWindow;
    public static WinnerWindow winnerFrame;

    private static RubicCube rubicCube;
    private static Renderer renderer;

    public App() {
        rubicCube = new RubicCube();
        renderer = new Renderer();
        mainWindow = new MainWindow(renderer);
        initWindow = new InitWindow(mainWindow);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new rubicCube.App());
    }

    public static Renderer getRenderer() {
        return renderer;
    }

    public static RubicCube getRubicCube() {
        return rubicCube;
    }


}