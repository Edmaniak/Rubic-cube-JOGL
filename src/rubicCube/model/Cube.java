package rubicCube.model;

import com.jogamp.opengl.GL2;
import rubicCube.*;

import static com.jogamp.opengl.GL2ES3.GL_QUADS;

public class Cube {

    private final int index;
    private Vec3Df position;
    private final Vec3Df originalPosition;
    private final Rotation rotation;
    private float size;
    private float space;
    private ColorTopology colorTopology;

    public Cube(int index, Vec3Df position, float size, float space) {
        this.index = index;
        this.size = size;
        this.space = space;
        this.position = new Vec3Df(position);
        this.originalPosition = new Vec3Df(position);
        this.colorTopology = new ColorTopology(generateColors());
        this.rotation = new Rotation();
    }

    public Cube(Cube cube) {
        this.index = cube.getIndex();
        this.size = cube.getSize();
        this.space = cube.getSpace();
        this.position = new Vec3Df(cube.getPosition());
        this.originalPosition = new Vec3Df(cube.getPosition());
        this.rotation = new Rotation(cube.getRotation());
        this.colorTopology = new ColorTopology(cube.getColorTopology());
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

    public void render(GL2 gl) {

        gl.glBegin(GL_QUADS);

        // LEFT
        Col leftColor = colorTopology.getLeftColor();
        gl.glColor3f(leftColor.getR(), leftColor.getG(), leftColor.getB());
        gl.glVertex3f(-size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, -size / 2, -size / 2);
        gl.glVertex3f(-size / 2, size / 2, -size / 2);
        gl.glVertex3f(-size / 2, size / 2, size / 2);
        // RIGHT
        Col rightColor = colorTopology.getRightColor();
        gl.glColor3f(rightColor.getR(), rightColor.getG(), rightColor.getB());
        gl.glVertex3f(size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, size / 2);
        gl.glVertex3f(size / 2, size / 2, size / 2);
        gl.glVertex3f(size / 2, size / 2, -size / 2);
        // BACK
        Col backColor = colorTopology.getBackColor();
        gl.glColor3f(backColor.getR(), backColor.getG(), backColor.getB());
        gl.glVertex3f(-size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, size / 2, -size / 2);
        gl.glVertex3f(-size / 2, size / 2, -size / 2);
        // FRONT
        Col frontColor = colorTopology.getFrontColor();
        gl.glColor3f(frontColor.getR(), frontColor.getG(), frontColor.getB());
        gl.glVertex3f(size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, size / 2, size / 2);
        gl.glVertex3f(size / 2, size / 2, size / 2);
        // TOP
        Col topColor = colorTopology.getTopColor();
        gl.glColor3f(topColor.getR(), topColor.getG(), topColor.getB());
        gl.glVertex3f(-size / 2, size / 2, size / 2);
        gl.glVertex3f(-size / 2, size / 2, -size / 2);
        gl.glVertex3f(size / 2, size / 2, -size / 2);
        gl.glVertex3f(size / 2, size / 2, size / 2);
        // BOTTOM
        Col bottomColor = colorTopology.getBottomColor();
        gl.glColor3f(bottomColor.getR(), bottomColor.getG(), bottomColor.getB());
        gl.glVertex3f(-size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, size / 2);

        gl.glEnd();
    }

    private ColorTopology generateColors() {
        ColorTopology colorTopology = new ColorTopology();
        // GREEN FRONT
        if (originalPosition.getZ() == getSizeWithSpace())
            colorTopology.setFrontColor(new Col(0, 1, 0));

        // BLUE BACK
        if (originalPosition.getZ() == -getSizeWithSpace())
            colorTopology.setBackColor(new Col(0, 0, 1));

        // YELLOW LEFT
        if (originalPosition.getX() == -getSizeWithSpace())
            colorTopology.setLeftColor(new Col(1, 1, 0));

        // ORANGE RIGHT
        if (originalPosition.getX() == getSizeWithSpace())
            colorTopology.setRightColor(new Col(1, 0.5f, 0));

        // RED TOP
        if (originalPosition.getY() == getSizeWithSpace())
            colorTopology.setTopColor(new Col(1, 0, 0));

        // WHITE BOTTOM
        if (originalPosition.getY() == -getSizeWithSpace())
            colorTopology.setBottomColor(new Col(1, 1, 1));


        return colorTopology;

    }

    public Rotation getRotation() {
        return rotation;
    }

    public void rotateX(Direction direction) {
        colorTopology.rotateX(direction);
    }

    public void rotateY(Direction direction) {
        colorTopology.rotateY(direction);
    }

    public void rotateZ(Direction direction) {
        colorTopology.rotateZ(direction);
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

    public float getSize() {
        return size;
    }

    public float getSizeWithSpace() {
        return size + space;
    }

    public float getSpace() {
        return space;
    }

    public ColorTopology getColorTopology() {
        return colorTopology;
    }
}
