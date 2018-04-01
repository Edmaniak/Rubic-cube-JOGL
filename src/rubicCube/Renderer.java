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

/**
 * trida pro zobrazeni sceny v OpenGL:
 * inicializace, prekresleni, udalosti, viewport
 * @author PGRF FIM UHK
 * @version 2015
 */

public class Renderer implements GLEventListener, MouseListener,
        MouseMotionListener, KeyListener {

    GLUT glut;
    GLU glu;
    int dx, dy, ox, oy;

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

        gl.glNewList(1,GL2.GL_COMPILE);



        gl.glEndList();


    }

    /**
     * metoda zobrazeni, volana pri prekresleni kazdeho snimku
     */
    @Override
    public void display(GLAutoDrawable glDrawable) {
        GL2 gl = glDrawable.getGL().getGL2();

        // mazeme image buffer i z-buffer
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glColor3f(1, 0, 0);
        gl.glPushMatrix();
        gl.glRotatef(25,0,1,0);
        gl.glTranslatef(0,0,0);
        glut.glutSolidCube(2);
        gl.glPopMatrix();

        gl.glColor3f(0, 1, 0);
        gl.glPushMatrix();
        gl.glRotatef(25,0,1,0);
        gl.glTranslatef(2.1f,0,0);
        glut.glutSolidCube(2);
        gl.glPopMatrix();

        gl.glColor3f(0, 0, 1);
        gl.glPushMatrix();
        gl.glRotatef(25,0,1,0);
        gl.glTranslatef(-2.1f,0,0);
        glut.glutSolidCube(2);
        gl.glPopMatrix();

        gl.glColor3f(1, 0, 0);
        gl.glPushMatrix();
        gl.glRotatef(25,0,1,0);
        gl.glTranslatef(-2.1f,0,2.1f);
        glut.glutSolidCube(2);
        gl.glPopMatrix();

        gl.glColor3f(1, 1, 1);
        gl.glPushMatrix();
        gl.glRotatef(25,0,1,0);
        gl.glTranslatef(0,0,2.1f);
        glut.glutSolidCube(2);
        gl.glPopMatrix();

       // gl.glRotatef(1,0,1,0);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45, 1280 / (float) 728, 0.1f, 100.0f);
        glu.gluLookAt(0, 25, 0, 0, 0, 0, 1, 0, 0);









    }


    /**
     * metoda volana pri zmene velikosti okna a pri prvnim vykresleni
     */
    @Override
    public void reshape(GLAutoDrawable glDrawable, int x, int y, int width,
                        int height) {
        GL2 gl = glDrawable.getGL().getGL2();
        gl.glViewport(0,0,width,height);
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
        if (e.getButton() == MouseEvent.BUTTON1) {
        }
        ox = e.getX();
        oy = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
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
        // switch (e.getKeyCode()) {
        // case KeyEvent.VK_A: //A
        // ;
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // switch (e.getKeyCode()) {}
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
    }
}