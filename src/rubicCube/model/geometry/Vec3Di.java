package rubicCube.model.geometry;

public class Vec3Di {

    private final int x;
    private final int y;
    private final int z;

    public Vec3Di(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3Di() {
        this(0, 0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return " " + x + " " + y + " " + z;
    }
}
