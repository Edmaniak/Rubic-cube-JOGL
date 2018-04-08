package rubicCube;

public class Col {

    private final float r;
    private final float g;
    private final float b;

    public Col(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Col(Col col) {
        this(col.getR(), col.getG(), col.getG());
    }

    public Col(float rgb) {
        this(rgb, rgb, rgb);
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

    @Override
    public String toString() {
        return "R: " + r + "G: " + g + "B: " + b;
    }
}
