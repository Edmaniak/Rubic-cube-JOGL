package rubicCube.app;

import rubicCube.animation.Animation;
import rubicCube.animation.Animator;
import rubicCube.gui.MainWindow;
import rubicCube.model.RubicCube;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<Turn> turns;
    private RubicCube rubicCube;
    private MainWindow gui;
    private Animator animator;

    public GameManager(RubicCube rubicCube, MainWindow gui, Animator animator) {
        this.rubicCube = rubicCube;
        this.gui = gui;
        this.turns = new ArrayList<>();
    }

    public void nextTurn() {
        gui.increaseTurnCount();
    }

    public void solve() {
        for (Turn turn : rubicCube.getTurns()) {
            Animation solvingAnimation = rubicCube.solveTurn(turn);

        }
    }

    public void reset() {
        rubicCube.generateStructure();
        gui.setTurnCount(0);
    }
}
