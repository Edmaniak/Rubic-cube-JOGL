package rubicCube.model;

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

    public boolean isTheSame(Col col) {
        return r == col.r && b == col.b && g == col.g;
    }

    @Override
    public String toString() {
        if (r == 1 && g == 0 && b == 0)
            return "RED";
        if (r == 0 && g == 1 && b == 0)
            return "GREEN";
        if (r == 0 && g == 0 && b == 1)
            return "BLUE";
        if (r == 1 && g == 1 && b == 1)
            return "WHITE";
        if (r == 1 && g == 1 && b == 0)
            return "YELLOW";
        if (r == 0 && g == 1 && b == 1)
            return "CYAN";
        if (r == 1 && g == 0 && b == 1)
            return "MAGENTA";

        return "R: " + r + "G: " + g + "B: " + b;
    }
}
