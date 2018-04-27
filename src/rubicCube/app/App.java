package rubicCube.app;

import rubicCube.animation.Animator;
import rubicCube.gui.*;
import rubicCube.model.RubicCube;

import javax.swing.*;

public class App {

    public static final int FPS = 60;
    public static boolean debug = false;
    public static boolean freeMotion = false;

    private static MainWindow mainWindow;
    private static InitWindow initWindow;
    private static WinnerWindow winnerWindow;
    private static TurnWindow turnWindow;
    private static HelpWindow helpWindow;

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
        helpWindow = new HelpWindow(mainWindow);
        initWindow = new InitWindow(mainWindow);
        gameManager = new GameManager(rubicCube, mainWindow, animator);
        winnerWindow = new WinnerWindow(mainWindow,0);
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

    public static GameManager getGameManager() {
        return gameManager;
    }

    public static Animator getAnimator() {
        return animator;
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
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

    public static HelpWindow getHelpWindow() {
        return helpWindow;
    }
}