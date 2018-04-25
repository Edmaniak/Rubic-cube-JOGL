package rubicCube.model.geometry;

public class Vec3Df {

    private final float x;
    private final float y;
    private final float z;

    public Vec3Df(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3Df() {
        this(0, 0, 0);
    }

    public Vec3Df(Vec3Df vec3Df) {
        this.x = vec3Df.getX();
        this.y = vec3Df.getY();
        this.z = vec3Df.getZ();
    }

    public Vec3Df add(float x, float y, float z) {
        return new Vec3Df(this.x + x, this.y + y, this.z + z);
    }

    public Vec3Df add(Vec3Df vec3Df) {
        return add(vec3Df.x, vec3Df.y, vec3Df.z);
    }

    public Vec3Df mul(Vec3Df vec3Df) {
        return mul(vec3Df.x, vec3Df.y, vec3Df.z);
    }

    public Vec3Df mul(float x, float y, float z) {
        return new Vec3Df(this.x * x, this.y * y, this.z * z);
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
