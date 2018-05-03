package rubicCube.app.manager;

import rubicCube.animation.PropAnimation;
import rubicCube.animation.Animator;
import rubicCube.app.App;
import rubicCube.app.turn.Turn;
import rubicCube.gui.MainWindow;
import rubicCube.model.cube.RubicCube;

import java.util.ArrayList;

/**
 * Handles the game state, turns and solving. Singleton object
 * that directs the game flow.
 */
public class GameManager {

    private ArrayList<Turn> turns;
    private RubicCube rubicCube;
    private final MainWindow gui;
    private final Animator animator;
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

        if (gameState != GameState.AUTO_SOLVING) {
            if (!rubicCube.isSolved()) {
                gui.increaseTurnCount();
                turnCount++;
            } else {
                turnCount++;
                App.showWinnerWindow(turnCount);
                gameState = GameState.SOLVED;
            }
        }

        if (gameState == GameState.AUTO_SOLVING && rubicCube.isSolved()) {
            gameState = GameState.SOLVED;
            App.getInitWindow().setVisible(true);
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
        setTurnCount(0);
    }

    /**
     * Routine needed for the game restart. Regenerates the rubic cube a clears turn
     * records
     */
    public void reset() {
        rubicCube.generateStructure();
        start();

    }

    public GameState getGameState() {
        return gameState;
    }

    public void setTurnCount(int turnCount) {
        gui.setTurnCount(turnCount);
        this.turnCount = turnCount;
    }

    public int getTurnCount() {
        return turnCount;
    }

}
