package rubicCube;

import rubicCube.model.Cube;

public class Segment {

    private Cube[] cubes;
    private Orientation orientation;
    private State state;
    private int index;

    public Segment(Cube[] cubes, Orientation orientation, int index) {
        this.cubes = cubes;
        this.state = new State();
        this.orientation = orientation;
        this.index = index;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getIndex() {
        return index;
    }

    public State getState() {
        return state;
    }

    public Cube[] getCubes() {
        return cubes;
    }
}
