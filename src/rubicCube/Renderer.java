package rubicCube;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.math.Quaternion;
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
    RubicCube rubicCube = new RubicCube();
    boolean rotate;
    float m[] = new float[16];

    Quaternion q1 = new Quaternion();

    Animation row0Anim = new Animation(90);
    Animation row1Anim = new Animation(90);
    Animation row2Anim = new Animation(90);
    Animation col0Anim = new Animation(90);

    int i = 0;


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

        // draw our axes
        gl.glBegin(GL_LINES);
// draw line for x axis
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(10.0f, 0.0f, 0.0f);
// draw line for y axis
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 10.0f, 0.0f);
// draw line for Z axis
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 10.0f);
        gl.glEnd();


        for (Cube cube : rubicCube.getCubes()) {

            gl.glColor3f(cube.getR(), cube.getG(), cube.getB());
            gl.glPushMatrix();

            for (Cube cubeInRow : rubicCube.getRow(2))
                if (cube.getIndex() == cubeInRow.getIndex()) {
                    gl.glRotatef(rubicCube.getRowRot(2), 0, 1, 0);
                }

            for (Cube cubeInRow : rubicCube.getRow(1))
                if (cube.getIndex() == cubeInRow.getIndex())
                    gl.glRotatef(rubicCube.getRowRot(1), 0, 1, 0);

            for (Cube cubeInRow : rubicCube.getRow(0))
                if (cube.getIndex() == cubeInRow.getIndex()) {
                    gl.glRotatef(rubicCube.getRowRot(0), 0, 1, 0);
                }

            for (Cube cubeInCol : rubicCube.getCol(0))
                if (cube.getIndex() == cubeInCol.getIndex()) {
                    gl.glRotatef(rubicCube.getColRot(0), cube.getRotCol().getX(), cube.getRotCol().getY(), cube.getRotCol().getZ());
                }

            gl.glTranslatef(cube.getX(), cube.getY(), cube.getZ());
            glut.glutSolidCube(2);

            gl.glPopMatrix();
        }


        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, 1280 / (float) 728, 0.1f, 100.0f);
        glu.gluLookAt(-20, 20, 20, 0, 0, 0, 0, 1, 0);


    }

    private void handleAnimation() {
        rubicCube.setRowRot(0, row0Anim.animate());
        rubicCube.setRowRot(1, row1Anim.animate());
        rubicCube.setRowRot(2, row2Anim.animate());
        rubicCube.setColRot(0, col0Anim.animate());
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

    // key listener
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                rubicCube.rotateRow(2, Direction.LEFT);
                row2Anim.playBackwadrs();
                rotate = true;
                break;
            case KeyEvent.VK_E:
                rubicCube.rotateRow(2, Direction.RIGHT);
                row2Anim.play();
                rotate = true;
                break;
            case KeyEvent.VK_A:
                rubicCube.rotateRow(1, Direction.LEFT);
                row1Anim.play();
                rotate = true;
                break;
            case KeyEvent.VK_D:
                rubicCube.rotateRow(1, Direction.RIGHT);
                row1Anim.playBackwadrs();
                rotate = true;
                break;
            case KeyEvent.VK_Z:
                rubicCube.rotateRow(0, Direction.LEFT);
                row0Anim.playBackwadrs();
                rotate = true;
                break;
            case KeyEvent.VK_C:
                rubicCube.rotateRow(0, Direction.RIGHT);
                row0Anim.play();
                rotate = true;
                break;

            case KeyEvent.VK_J:
                rubicCube.rotateCol(0, Direction.BACK);
                col0Anim.play();
                rotate = true;
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