package rubicCube.app;

import rubicCube.gui.ControlsWindow;
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
    public static ControlsWindow controlsWindow;

    private static RubicCube rubicCube;
    private static Renderer renderer;
    private static GameManager gameManager;

    public App() {
        rubicCube = new RubicCube();
        renderer = new Renderer();
        mainWindow = new MainWindow(renderer);
        initWindow = new InitWindow(mainWindow);
        controlsWindow = new ControlsWindow(mainWindow);
        gameManager = new GameManager(rubicCube, mainWindow);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }

    public static void reset() {
        rubicCube.generateStructure();
        mainWindow.setTurnCount(0);
    }

    public static Renderer getRenderer() {
        return renderer;
    }

    public static RubicCube getRubicCube() {
        return rubicCube;
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public static GameManager getGameManager() {
        return gameManager;
    }
}