package rubicCube.model;

import com.jogamp.opengl.GL2;
import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Vec3Df;

import static com.jogamp.opengl.GL2ES3.GL_QUADS;

/**
 * Logical representation of the small cube within Rubic's cube
 */
public class Cube {

    private final int index;
    private Vec3Df position;
    private final Vec3Df originalPosition;
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
    }

    public Cube(Cube cube) {
        this.index = cube.getIndex();
        this.size = cube.getSize();
        this.space = cube.getSpace();
        this.position = new Vec3Df(cube.getPosition());
        this.originalPosition = new Vec3Df(cube.getPosition());
        this.colorTopology = new ColorTopology(cube.getColorTopology());
    }

    /**
     * Generates the cube's geometry with the right colors
     * and topology.
     *
     * @param gl reference to main OpenGL pipeline
     */
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

    /**
     * Generates faces' color based on the original position of
     * the cube within the Rubic's cube.
     *
     * @return the right ColorTopology object that can be used in
     * the cube geometry generation.
     */
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

    /**
     * Checks whether this cube is the same logical cube as the one
     * filled in the parameter. It depends on the index only.
     *
     * @param cube the tested cube
     * @return true if it is the same cube
     */
    public boolean isTheSameCube(Cube cube) {
        if (cube.getIndex() == index)
            return true;
        return false;
    }

    /**
     * Apply the effect of the X plane rotation.
     *
     * @param direction the direction of rotation
     */
    public void rotateX(Direction direction) {
        colorTopology.rotateX(direction);
    }

    /**
     * Apply the effect of the Y plane rotation.
     *
     * @param direction the direction of rotation
     */
    public void rotateY(Direction direction) {
        colorTopology.rotateY(direction);
    }

    /**
     * Apply the effect of the Z plane rotation.
     *
     * @param direction the direction of rotation
     */
    public void rotateZ(Direction direction) {
        colorTopology.rotateZ(direction);
    }

    /**
     * Simply returns the absolute size of the cube including
     * the space around.
     * @return
     */
    public float getSizeWithSpace() {
        return size + space;
    }

    public float getSpace() {
        return space;
    }

    public Vec3Df getPosition() {
        return position;
    }

    public ColorTopology getColorTopology() {
        return colorTopology;
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

    public float getSize() {
        return size;
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
