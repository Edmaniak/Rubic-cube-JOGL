package rubicCube.app;

import rubicCube.animation.PropAnimation;
import rubicCube.animation.Animator;
import rubicCube.gui.MainWindow;
import rubicCube.model.RubicCube;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<Turn> turns;
    private RubicCube rubicCube;
    private MainWindow gui;
    private Animator animator;
    private GameState gameState;
    private int turnCount;

    public GameManager(RubicCube rubicCube, MainWindow gui, Animator animator) {
        this.gameState = GameState.IDLE;
        this.rubicCube = rubicCube;
        this.gui = gui;
        this.turns = rubicCube.getTurns();
        this.animator = animator;
    }

    public void nextTurn() {
        if (!checkForSolved()) {
            gui.increaseTurnCount();
            turnCount++;
        }
    }

    public void solve() {
        this.gameState = GameState.AUTO_SOLVING;
        for (int i = turns.size() - 1; i >= 0; i--) {
            PropAnimation solvingPropAnimation = rubicCube.solveTurn(turns.get(i));
            animator.addToPlaylist(solvingPropAnimation);
        }
        rubicCube.getTurns().clear();
    }

    public void start() {
        this.gameState = GameState.SOLVING;
    }

    public void reset() {
        this.gameState = GameState.SOLVING;
        rubicCube.generateStructure();
        gui.setTurnCount(0);
        turnCount = 0;
    }

    public boolean checkForSolved() {
        if (rubicCube.isSolved()) {
            App.getWinnerWindow().setVisible(true);
            gameState = GameState.SOLVED;
            return true;
        }
        return false;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getTurnCount() {
        return turnCount;
    }
}
