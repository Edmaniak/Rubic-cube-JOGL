package rubicCube;

import rubicCube.model.Cube;

public class Segment {

    private Cube[] cubes;
    private Orientation orientation;
    private State state;
    private Vec3Di rotationVector;
    private int index;

    public Segment(Cube[] cubes, Orientation orientation, int index) {
        this.cubes = cubes;
        this.state = new State();
        this.orientation = orientation;
        this.index = index;
        switch (orientation) {
            case X:
                this.rotationVector = new Vec3Di(1, 0, 0);
                break;
            case Y:
                this.rotationVector = new Vec3Di(0, 1, 0);
                break;
            case Z:
                this.rotationVector = new Vec3Di(0, 0, 1);
                break;
        }
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

    public Vec3Di getRotationVector() {
        return rotationVector;
    }

    public void setCubes(Cube[] cubes) {
        this.cubes = cubes;
    }
}
