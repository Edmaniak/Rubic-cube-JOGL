package rubicCube.model;

import rubicCube.animation.Animatable;
import rubicCube.animation.State;
import rubicCube.model.Cube;
import rubicCube.model.geometry.Orientation;
import rubicCube.model.geometry.Vec3Di;

public class Segment implements Animatable<Segment> {

    private Cube[] cubes;
    private final Orientation orientation;
    private final State state;
    private Vec3Di rotationVector;
    private final int index;

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

    @Override
    public Segment getObject() {
        return null;
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
