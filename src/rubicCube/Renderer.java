package rubicCube;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import utils.OglUtils;


import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import static com.jogamp.opengl.GL.GL_LINES;

/**
 * trida pro zobrazeni sceny v OpenGL:
 * inicializace, prekresleni, udalosti, viewport
 *
 * @author PGRF FIM UHK
 * @version 2015
 */

public class Renderer implements GLEventListener, MouseListener,
        MouseMotionListener, KeyListener {

    GLUT glut;
    GLU glu;
    int dx, dy, ox, oy;
    RubicCube rubicCube = new RubicCube(0.5f, 2);
    float m[] = new float[16];

    private Animation y0Anim;
    private Animation y1Anim;
    private Animation y2Anim;

    private Animation x0Anim;
    private Animation x1Anim;
    private Animation x2Anim;

    private Animation z0Anim;
    private Animation z1Anim;
    private Animation z2Anim;

    private RotationManager rotManager;

    public Renderer() {

        rotManager = new RotationManager();

        y0Anim = new Animation(90, rubicCube.getYRot(0), rotManager.getIsRotatingY());
        y1Anim = new Animation(90, rubicCube.getYRot(1), rotManager.getIsRotatingY());
        y2Anim = new Animation(90, rubicCube.getYRot(2), rotManager.getIsRotatingY());

        x0Anim = new Animation(90, rubicCube.getXRot(0), rotManager.getIsRotatingX());
        x1Anim = new Animation(90, rubicCube.getXRot(1), rotManager.getIsRotatingX());
        x2Anim = new Animation(90, rubicCube.getXRot(2), rotManager.getIsRotatingX());

        z0Anim = new Animation(90, rubicCube.getZRot(0), rotManager.getIsRotatingZ());
        z1Anim = new Animation(90, rubicCube.getZRot(1), rotManager.getIsRotatingZ());
        z2Anim = new Animation(90, rubicCube.getZRot(2), rotManager.getIsRotatingZ());

    }

    /**
     * metoda inicializace, volana pri vytvoreni okna
     */
    @Override
    public void init(GLAutoDrawable glDrawable) {
        GL2 gl = glDrawable.getGL().getGL2();
        glut = new GLUT();
        glu = new GLU();

        OglUtils.printOGLparameters(gl);

        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        // Setting counterclockwise polygons to front face
        gl.glFrontFace(GL2.GL_CCW);
        gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
        gl.glDisable(GL2.GL_CULL_FACE);
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glDisable(GL2.GL_LIGHTING);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glLoadIdentity();
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, m, 0);

    }

    /**
     * metoda zobrazeni, volana pri prekresleni kazdeho snimku
     */
    @Override
    public void display(GLAutoDrawable glDrawable) {
        GL2 gl = glDrawable.getGL().getGL2();
        handleAnimation();

        // mazeme image buffer i z-buffer
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

        // rotace podle zmeny pozice mysi, osy rotace zustavaji svisle a vodorovne
        gl.glLoadIdentity();
        gl.glRotatef(dx, 0, 1, 0);
        gl.glRotatef(dy, 1, 0, 0);
        gl.glMultMatrixf(m, 0);
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, m, 0);
        dx = 0;
        dy = 0;


        if (App.debug)
            drawAxis(gl, 10f);


        for (Cube cube : rubicCube.getCubes()) {

            gl.glPushMatrix();

            for (Cube cubeInRow : rubicCube.getYPlate(2))
                if (cube.getIndex() == cubeInRow.getIndex()) {
                    gl.glRotatef(rubicCube.getYRot(2).getValue(), 0, 1, 0);
                }

            for (Cube cubeInRow : rubicCube.getYPlate(1))
                if (cube.getIndex() == cubeInRow.getIndex())
                    gl.glRotatef(rubicCube.getYRot(1).getValue(), 0, 1, 0);

            for (Cube cubeInRow : rubicCube.getYPlate(0))
                if (cube.getIndex() == cubeInRow.getIndex()) {
                    gl.glRotatef(rubicCube.getYRot(0).getValue(), 0, 1, 0);
                }

            for (Cube cubeInCol : rubicCube.getXPlate(2))
                if (cube.getIndex() == cubeInCol.getIndex()) {
                    gl.glRotatef(rubicCube.getXRot(2).getValue(), 1, 0, 0);
                }

            for (Cube cubeInCol : rubicCube.getXPlate(1))
                if (cube.getIndex() == cubeInCol.getIndex()) {
                    gl.glRotatef(rubicCube.getXRot(1).getValue(), 1, 0, 0);
                }

            for (Cube cubeInCol : rubicCube.getXPlate(0))
                if (cube.getIndex() == cubeInCol.getIndex()) {
                    gl.glRotatef(rubicCube.getXRot(0).getValue(), 1, 0, 0);
                }

            for (Cube cubeInC : rubicCube.getZPlate(2))
                if (cube.getIndex() == cubeInC.getIndex()) {
                    gl.glRotatef(rubicCube.getZRot(2).getValue(), 0, 0, 1);
                }

            for (Cube cubeInC : rubicCube.getZPlate(1))
                if (cube.getIndex() == cubeInC.getIndex()) {
                    gl.glRotatef(rubicCube.getZRot(1).getValue(), 0, 0, 1);
                }

            for (Cube cubeInC : rubicCube.getZPlate(0))
                if (cube.getIndex() == cubeInC.getIndex()) {
                    gl.glRotatef(rubicCube.getZRot(0).getValue(), 0, 0, 1);
                }

            gl.glTranslatef(cube.getX(), cube.getY(), cube.getZ());


            if (cube.getIndex() != -1) {
                if (App.debug)
                    drawAxis(gl, 5);
                cube.render(gl);
            }

            gl.glPopMatrix();
        }


        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, 1280 / (float) 728, 0.1f, 100.0f);
        glu.gluLookAt(-20, 20, 20, 0, 0, 0, 0, 1, 0);


    }

    private void handleAnimation() {
        y0Anim.animate();
        y1Anim.animate();
        y2Anim.animate();

        x0Anim.animate();
        x1Anim.animate();
        x2Anim.animate();

        z0Anim.animate();
        z1Anim.animate();
        z2Anim.animate();

    }


    /**
     * metoda volana pri zmene velikosti okna a pri prvnim vykresleni
     */
    @Override
    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
                        int height) {
        GL2 gl = glDrawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
    }

    /*
     * metody pro obsluhu udalosti od mysi a klavesnice
     */

    // mouse listener
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
        }
        ox = e.getX();
        oy = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dx = e.getX() - ox;
        dy = e.getY() - oy;
        ox = e.getX();
        oy = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private void drawAxis(GL2 gl, float size) {
        // draw our axes
        gl.glBegin(GL_LINES);
        // draw line for x axis
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(size, 0.0f, 0.0f);
        // draw line for y axis
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, size, 0.0f);
        // draw line for Z axis
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, size);
        gl.glEnd();
    }

    // key listener
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                if (rotManager.canRotateY())
                    y2Anim.negative(() -> rubicCube.rotateY(2, Direction.LEFT));
                break;
            case KeyEvent.VK_E:
                if (rotManager.canRotateY())
                    y2Anim.positive(() -> rubicCube.rotateY(2, Direction.RIGHT));
                break;
            case KeyEvent.VK_A:
                if (rotManager.canRotateY())
                    y1Anim.negative(() -> rubicCube.rotateY(1, Direction.LEFT));
                break;
            case KeyEvent.VK_D:
                if (rotManager.canRotateY())
                    y1Anim.positive(() -> rubicCube.rotateY(1, Direction.RIGHT));
                break;
            case KeyEvent.VK_Z:
                if (rotManager.canRotateY())
                    y0Anim.negative(() -> rubicCube.rotateY(0, Direction.LEFT));
                break;
            case KeyEvent.VK_C:
                if (rotManager.canRotateY())
                    y0Anim.positive(() -> rubicCube.rotateY(0, Direction.RIGHT));
                break;
            case KeyEvent.VK_J:
                if (rotManager.canRotateX())
                    x0Anim.positive(() -> rubicCube.rotateX(0, Direction.BACK));
                break;
            case KeyEvent.VK_U:
                if (rotManager.canRotateX())
                    x0Anim.negative(() -> rubicCube.rotateX(0, Direction.THERE));
                break;
            case KeyEvent.VK_K:
                if (rotManager.canRotateX())
                    x1Anim.positive(() -> rubicCube.rotateX(1, Direction.BACK));
                break;
            case KeyEvent.VK_I:
                if (rotManager.canRotateX())
                    x1Anim.negative(() -> rubicCube.rotateX(1, Direction.THERE));
                break;
            case KeyEvent.VK_L:
                if (rotManager.canRotateX())
                    x2Anim.positive(() -> rubicCube.rotateX(2, Direction.BACK));
                break;
            case KeyEvent.VK_O:
                if (rotManager.canRotateX())
                    x2Anim.negative(() -> rubicCube.rotateX(2, Direction.THERE));
                break;
            case KeyEvent.VK_R:
                if (rotManager.canRotateZ())
                    z2Anim.positive(() -> rubicCube.rotateZ(2, Direction.LEFT));
                break;
            case KeyEvent.VK_T:
                if (rotManager.canRotateZ())
                    z2Anim.negative(() -> rubicCube.rotateZ(2, Direction.RIGHT));
                break;
            case KeyEvent.VK_F:
                if (rotManager.canRotateZ())
                    z1Anim.positive(() -> rubicCube.rotateZ(1, Direction.LEFT));
                break;
            case KeyEvent.VK_G:
                if (rotManager.canRotateZ())
                    z1Anim.negative(() -> rubicCube.rotateZ(1, Direction.RIGHT));
                break;
            case KeyEvent.VK_V:
                if (rotManager.canRotateZ())
                    z0Anim.positive(() -> rubicCube.rotateZ(0, Direction.LEFT));
                break;
            case KeyEvent.VK_B:
                if (rotManager.canRotateZ())
                    z0Anim.negative(() -> rubicCube.rotateZ(0, Direction.RIGHT));
                break;

            case KeyEvent.VK_F1:
                App.debug = !App.debug;
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
    }
}