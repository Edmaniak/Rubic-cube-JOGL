package rubicCube;

public class Vec3Df {


    private final float x;
    private final float y;
    private final float z;

    public Vec3Df(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3Df(Vec3Df vec3Df) {
        this.x = vec3Df.getX();
        this.y = vec3Df.getY();
        this.z = vec3Df.getZ();
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

    @Override
    public String toString() {
        return " " + x + " " + y + " " + z;
    }


}
