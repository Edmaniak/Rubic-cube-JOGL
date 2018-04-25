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

    public GameManager(RubicCube rubicCube, MainWindow gui, Animator animator) {
        this.rubicCube = rubicCube;
        this.gui = gui;
        this.turns = rubicCube.getTurns();
        this.animator = animator;
    }

    public void nextTurn() {
        gui.increaseTurnCount();
    }

    public void solve() {
        for (int i = turns.size() - 1; i >= 0; i--) {
            PropAnimation solvingPropAnimation = rubicCube.solveTurn(turns.get(i));
            animator.addToPlaylist(solvingPropAnimation);
        }
        rubicCube.getTurns().clear();
    }

    public void reset() {
        rubicCube.generateStructure();
        gui.setTurnCount(0);
    }
}
