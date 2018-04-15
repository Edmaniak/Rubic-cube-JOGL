package rubicCube.app;

import rubicCube.gui.MainWindow;
import rubicCube.model.RubicCube;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<Turn> turns;
    private RubicCube rubicCube;
    private MainWindow gui;

    public GameManager(RubicCube rubicCube, MainWindow gui) {
        this.rubicCube = rubicCube;
        this.gui = gui;
        this.turns = new ArrayList<>();
    }

    public void nextTurn() {
        turns.add(new Turn());
        gui.increaseTurnCount();

    }

    public void reset() {
        rubicCube.generateStructure();
        gui.setTurnCount(0);
    }
}
