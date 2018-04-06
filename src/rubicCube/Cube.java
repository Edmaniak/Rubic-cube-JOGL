package rubicCube;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

import java.util.Random;

import static com.jogamp.opengl.GL2ES3.GL_QUADS;

public class Cube {

    private final int index;
    private Vec3Df position;
    private final Vec3Df originalPosition;
    private final Rotation rotation = new Rotation();
    private float size;

    public Cube(int index, Vec3Df position, float size) {
        this.index = index;
        this.size = size;
        this.position = new Vec3Df(position);
        this.originalPosition = new Vec3Df(position);
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
        // front red
        gl.glColor3f(1, 0, 0);
        gl.glVertex3f(-size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, -size / 2, -size / 2);
        gl.glVertex3f(-size / 2, size / 2, -size / 2);
        gl.glVertex3f(-size / 2, size / 2, size / 2);
        // back blu
        gl.glColor3f(0, 1, 0);
        gl.glVertex3f(size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, size / 2);
        gl.glVertex3f(size / 2, size / 2, size / 2);
        gl.glVertex3f(size / 2, size / 2, -size / 2);
        // right
        gl.glColor3f(0, 0, 1);
        gl.glVertex3f(-size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, size / 2, -size / 2);
        gl.glVertex3f(-size / 2, size / 2, -size / 2);
        // left
        gl.glColor3f(1, 1, 0);
        gl.glVertex3f(size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, size / 2, size / 2);
        gl.glVertex3f(size / 2, size / 2, size / 2);
        // top
        gl.glColor3f(0, 1, 1);
        gl.glVertex3f(-size / 2, size / 2, size / 2);
        gl.glVertex3f(-size / 2, size / 2, -size / 2);
        gl.glVertex3f(size / 2, size / 2, -size / 2);
        gl.glVertex3f(size / 2, size / 2, size / 2);
        // bottom
        gl.glColor3f(1, 0, 1);
        gl.glVertex3f(-size / 2, -size / 2, size / 2);
        gl.glVertex3f(-size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, -size / 2);
        gl.glVertex3f(size / 2, -size / 2, size / 2);

        gl.glEnd();
    }

    private void generateColor() {
        //front

        //back

        // left side
        if (originalPosition.getX() == -size) {

        }
        // right side

        // top

        // bottom

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
