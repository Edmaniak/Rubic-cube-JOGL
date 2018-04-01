package rubicCube;

import java.util.Random;

public class Cube {

    private  final int index;
    private final float x;
    private final float y;
    private final float z;
    private final float r;
    private final float g;
    private final float b;
    private Random rand = new Random();

    public Cube(int index,float x, float y, float z) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = rand.nextFloat();
        this.g = rand.nextFloat();
        this.b = rand.nextFloat();
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
}
