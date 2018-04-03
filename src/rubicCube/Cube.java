package rubicCube;

import java.util.Random;

public class Cube {

    private final int index;
    private final float x;
    private final float y;
    private final float z;
    private final float r;
    private final float g;
    private final float b;
    private Random rand = new Random();
    private Rotation rotation = new Rotation();

    public Cube(int index, float x, float y, float z) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
        if (index == 0) {
            this.r = 1;
            this.g = 0;
            this.b = 0;
        } else if(index == 1) {
            this.r = 0;
            this.g = 0;
            this.b = 1;
        } else if(index == 2) {
            this.r = 0;
            this.g = 1;
            this.b = 1;
        }
        else if(index == 2) {
            this.r = 0;
            this.g = 1;
            this.b = 1;
        }
        else if(index == 12) {
            this.r = 0;
            this.g = 0;
            this.b = 0;
        }
        else if(index == 18) {
            this.r = 1;
            this.g = 1;
            this.b = 0;
        }
        else {
            this.r = rand.nextFloat();
            this.g = rand.nextFloat();
            this.b = rand.nextFloat();
        }
    }

    public int getIndex() {
        return index;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
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




}
