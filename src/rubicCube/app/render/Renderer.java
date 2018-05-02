package rubicCube.app.render;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import rubicCube.animation.PropAnimation;
import rubicCube.animation.Animator;
import rubicCube.animation.PlayDirection;
import rubicCube.animation.Status;
import rubicCube.app.App;
import rubicCube.app.manager.GameState;
import rubicCube.model.cube.Cube;
import rubicCube.model.cube.RubicCube;
import rubicCube.model.cube.Segment;
import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Orientation;
import rubicCube.model.geometry.Vec3Di;
import utils.OglUtils;

import javax.swing.*;
import java.awt.event.*;

import static com.jogamp.opengl.GL.*;

/**
 * Main openGL canvas for displaying purposes
 */
public class Renderer implements GLEventListener, MouseListener,
        MouseMotionListener, KeyListener {

    private GLU glu;
    private GL2 gl;
    private int dx, dy, dz, ox, oy, oz;
    private long oldmils;
    private long oldFPSmils;
    private RubicCube rubicCube;
    private float m[] = new float[16];
    private Animator animator;

    public static float ROTATION_SPEED = 60;
    public static float OBJECT_ROTATION_SPEED = 0.3f;
    private float delay;
    private Camera camera;

    public Renderer(Animator animator, RubicCube rubicCube) {
        this.animator = animator;
        this.rubicCube = rubicCube;
        this.camera = new Camera(-40, 40, 40, 2);
    }

    @Override
    public void init(GLAutoDrawable glDrawable) {
        gl = glDrawable.getGL().getGL2();
        glu = new GLU();

        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);

        gl.glFrontFace(GL2.GL_CCW);
        gl.glPolygonMode(GL2.GL_FRONT, GL2.GL_FILL);
        gl.glDisable(GL2.GL_CULL_FACE);
        gl.glDisable(GL2.GL_TEXTURE_2D);
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_POLYGON_SMOOTH);
        gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glLoadIdentity();
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, m, 0);

    }

    @Override
    public void display(GLAutoDrawable glDrawable) {
        gl = glDrawable.getGL().getGL2();

        long mils = System.currentTimeMillis();
        if ((mils - oldFPSmils) > 300) {
            oldFPSmils = mils;
        }

        float step = ROTATION_SPEED * (mils - oldmils) / 1000.0f;
        oldmils = mils;

        animator.play(step);

        // mazeme image buffer i z-buffer
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);

        // rotace podle zmeny pozice mysi, osy rotace zustavaji svisle a vodorovne
        if (App.freeMotion) {
            gl.glLoadIdentity();
            gl.glRotatef((dx * delay) * OBJECT_ROTATION_SPEED, 0, 1, 0);
            gl.glRotatef((dy * delay) * OBJECT_ROTATION_SPEED, 0, 0, 1);
            gl.glMultMatrixf(m, 0);
            gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, m, 0);
        }

        // Delaying the mouse camera rotation
        if (delay >= 0)
            delay -= 0.02f;
        else {
            dx = 0;
            dy = 0;
        }

        if (App.debug)
            drawAxis(10f);


        for (Cube cube : rubicCube.getCubes()) {

            gl.glPushMatrix();

            for (int i = 0; i < 3; i++) {
                handleRotation(cube, rubicCube.getSegment(Orientation.X, i));
                handleRotation(cube, rubicCube.getSegment(Orientation.Y, i));
                handleRotation(cube, rubicCube.getSegment(Orientation.Z, i));
            }

            gl.glTranslatef(cube.getX(), cube.getY(), cube.getZ());

            if (App.debug)
                drawAxis(5);

            cube.render(gl);

            gl.glPopMatrix();
        }

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, 1280 / (float) 728, 0.1f, 100.0f);
        camera.animate();
        glu.gluLookAt(camera.getVActX(), camera.getVActY(), camera.getVActZ(), 0, 0, 0, 0, 1, 0);

        OglUtils.drawStr2D(glDrawable, 20, 20, "Závěrečný projekt FIM - PGRF2 [Adam Ouhrabka] [2.5.2018]");

    }

    /**
     * The main logic for rubic's cube interaction. Basically we're asking...
     *
     * @param cube    the partial cube of rubic cube
     * @param segment segment the partial cube is in
     */
    private void handleRotation(Cube cube, Segment segment) {
        if (segment.getState().getStatus() == Status.INMOTION)
            for (Cube segmentCube : segment.getCubes())
                if (segmentCube.isTheSameCube(cube)) {
                    Vec3Di rotVec = segment.getRotationVector();
                    gl.glRotatef(segment.getState().getValue(), rotVec.getX(), rotVec.getY(), rotVec.getZ());
                }
    }


    @Override
    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }


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
        if (SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e)) {
            ox = e.getX();
            oy = e.getY();
            delay = 1;
        }
        if (SwingUtilities.isMiddleMouseButton(e)) {
            oz = e.getY();
            delay = 1;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isMiddleMouseButton(e)) {
            oz = 0;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e)) {
            delay = 1;
            dx = e.getX() - ox;
            dy = e.getY() - oy;
            ox = e.getX();
            oy = e.getY();
        }

        if (SwingUtilities.isMiddleMouseButton(e)) {
            delay = 1;
            dz = e.getY() - oz;
            oz = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Axis drawing snippet. Mainly for debugging.
     *
     * @param size size of the axis
     */
    private void drawAxis(float size) {
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

    @Override
    public void keyPressed(KeyEvent e) {
        if (App.getGameManager().getGameState() == GameState.AUTO_SOLVING)
            return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Y, 2),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateY(2, Direction.LEFT, true)
                        ));
                break;
            case KeyEvent.VK_E:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Y, 2),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateY(2, Direction.RIGHT, true)
                        ));
                break;
            case KeyEvent.VK_A:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Y, 1),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateY(1, Direction.LEFT, true)
                        ));
                break;
            case KeyEvent.VK_D:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Y, 1),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateY(1, Direction.RIGHT, true)
                        ));
                break;
            case KeyEvent.VK_Y:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Y, 0),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateY(0, Direction.LEFT, true)
                        ));
                break;
            case KeyEvent.VK_C:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Y, 0),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateY(0, Direction.RIGHT, true)
                        ));
                break;
            case KeyEvent.VK_J:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.X, 0),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateX(0, Direction.BACK, true)
                        ));
                break;
            case KeyEvent.VK_U:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.X, 0),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateX(0, Direction.THERE, true)
                        ));
                break;
            case KeyEvent.VK_K:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.X, 1),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateX(1, Direction.BACK, true)
                        ));
                break;
            case KeyEvent.VK_I:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.X, 1),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateX(1, Direction.THERE, true)
                        ));
                break;
            case KeyEvent.VK_L:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.X, 2),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateX(2, Direction.BACK, true)
                        ));
                break;
            case KeyEvent.VK_O:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.X, 2),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateX(2, Direction.THERE, true)
                        ));
                break;
            case KeyEvent.VK_R:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Z, 2),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateZ(2, Direction.LEFT, true)
                        ));
                break;
            case KeyEvent.VK_T:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Z, 2),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateZ(2, Direction.RIGHT, true)
                        ));
                break;
            case KeyEvent.VK_F:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Z, 1),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateZ(1, Direction.LEFT, true)
                        ));
                break;
            case KeyEvent.VK_G:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Z, 1),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateZ(1, Direction.RIGHT, true)
                        ));
                break;
            case KeyEvent.VK_V:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Z, 0),
                                PlayDirection.FORWARDS,
                                () -> rubicCube.rotateZ(0, Direction.LEFT, true)
                        ));
                break;
            case KeyEvent.VK_B:
                animator.addToPlaylist(
                        new PropAnimation<>(
                                90,
                                rubicCube.getSegment(Orientation.Z, 0),
                                PlayDirection.BACKWARDS,
                                () -> rubicCube.rotateZ(0, Direction.RIGHT, true)
                        ));
                break;
            case KeyEvent.VK_LEFT:
                camera.move(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                camera.move(Direction.RIGHT);
                break;
            case KeyEvent.VK_UP:
                camera.move(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                camera.move(Direction.DOWN);
                break;
            case KeyEvent.VK_PAGE_UP:
                camera.zoomIn();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                camera.zoomOut();
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

    public static void setRotationSpeed(float rotationSpeed) {
        ROTATION_SPEED = rotationSpeed;
    }

    public void resetCamera() {
        this.camera = new Camera(-40, 40, 40, 2);
    }
}