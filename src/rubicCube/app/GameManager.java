package rubicCube.app;

import rubicCube.animation.PropAnimation;
import rubicCube.animation.Animator;
import rubicCube.gui.MainWindow;
import rubicCube.model.RubicCube;

import java.util.ArrayList;

/**
 * Handles the game state, turns and solving
 */
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

    /**
     * Routine needed for next turn
     */
    public void nextTurn() {
        if (!checkForSolved()) {
            gui.increaseTurnCount();
            turnCount++;
        }
    }

    /**
     * Calls the routine for the rubic cube to be solved.
     */
    public void solve() {
        this.gameState = GameState.AUTO_SOLVING;
        for (int i = turns.size() - 1; i >= 0; i--) {
            PropAnimation solvingPropAnimation = rubicCube.solveTurn(turns.get(i));
            animator.addToPlaylist(solvingPropAnimation);
        }
        rubicCube.getTurns().clear();
    }

    /**
     * Starts the game logically.
     */
    public void start() {
        this.gameState = GameState.SOLVING;
    }

    /**
     * Routine needed for the game restart. Regenerates the rubic cube a clears turn
     * records
     */
    public void reset() {
        this.gameState = GameState.SOLVING;
        rubicCube.generateStructure();
        gui.setTurnCount(0);
        turnCount = 0;
    }

    /**
     * Checks whether the cube is solved
     * @return true if it is solved
     */
    public boolean checkForSolved() {
        if (rubicCube.isSolved() && gameState != GameState.AUTO_SOLVING) {
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
