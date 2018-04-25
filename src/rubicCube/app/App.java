package rubicCube.app;

import rubicCube.animation.Animator;
import rubicCube.gui.*;
import rubicCube.model.RubicCube;

import javax.swing.*;

public class App {
    public static final int FPS = 60; // animator's target frames per second
    public static boolean debug = false;

    private static MainWindow mainWindow;
    private static InitWindow initWindow;
    private static WinnerWindow winnerWindow;
    private static ControlsWindow controlsWindow;
    private static TurnWindow turnWindow;

    private static RubicCube rubicCube;
    private static Renderer renderer;
    private static Animator animator;
    private static GameManager gameManager;

    public App() {
        animator = new Animator(() -> gameManager.nextTurn());
        rubicCube = new RubicCube();
        renderer = new Renderer(animator, rubicCube);
        mainWindow = new MainWindow(renderer);
        turnWindow = new TurnWindow(mainWindow, rubicCube);
        initWindow = new InitWindow(mainWindow);
        controlsWindow = new ControlsWindow(mainWindow);
        gameManager = new GameManager(rubicCube, mainWindow, animator);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
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

    public static Animator getAnimator() {
        return animator;
    }

    public static ControlsWindow getControlsWindow() {
        return controlsWindow;
    }

    public static InitWindow getInitWindow() {
        return initWindow;
    }

    public static TurnWindow getTurnWindow() {
        return turnWindow;
    }

    public static WinnerWindow getWinnerWindow() {
        return winnerWindow;
    }
}