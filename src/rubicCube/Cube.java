package rubicCube;

import java.util.Random;

public class Cube {

    private final int index;
    private Vec3Df position;
    private final float r;
    private final float g;
    private final float b;
    private Random rand = new Random();
    private Rotation rotation = new Rotation();

    public Cube(int index, Vec3Df position) {
        this.index = index;
        this.position = new Vec3Df(position);
        if (index == 0) {
            this.r = 1;
            this.g = 0;
            this.b = 0;
        } else if (index == 1) {
            this.r = 0;
            this.g = 0;
            this.b = 1;
        } else if (index == 2) {
            this.r = 0;
            this.g = 1;
            this.b = 1;
        } else if (index == 2) {
            this.r = 0;
            this.g = 1;
            this.b = 1;
        } else if (index == 12) {
            this.r = 0;
            this.g = 0;
            this.b = 0;
        } else if (index == 18) {
            this.r = 1;
            this.g = 1;
            this.b = 0;
        } else {
            this.r = rand.nextFloat();
            this.g = rand.nextFloat();
            this.b = rand.nextFloat();
        }
    }

    public Cube(Cube cube) {
        this.index = cube.index;
        this.position = new Vec3Df(cube.position);
        this.r = cube.r;
        this.g = cube.g;
        this.b = cube.b;
    }

    public int getIndex() {
        return index;
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }

    public float getZ() {
        return position.getZ();
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public Vec3D getRotVecForRow() {
        return rotation.getRotVecForRow();
    }

    public Vec3D getRotCol() {
        return rotation.getRotVecForCol();
    }

    public Vec3Df getPosition() {
        return position;
    }

    public void setPosition(Vec3Df position) {
        this.position = new Vec3Df(position);
    }

    public void setPosition(Cube cube) {
        this.position = new Vec3Df(cube.getPosition());
    }

    @Override
    public String toString() {
        return "Cube" + getIndex();
    }
}
