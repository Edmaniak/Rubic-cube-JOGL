package rubicCube.app.render;

import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Vec3Df;

/**
 * Specific four angle view camera for displaying the cube
 * from 4 different angles. The movement is always by 90 degrees
 */
public class Camera {

    private Vec3Df vectorActual;
    private Vec3Df vectorDesired;
    private final Vec3Df[] views = new Vec3Df[4];
    private float distance = 40;
    private float moveSpeed;
    private int pointer = 0;

    public Camera(Vec3Df vector, float moveSpeed) {
        this.vectorDesired = vector;
        this.vectorActual = vector;
        this.moveSpeed = moveSpeed;
        generateViews();
    }

    public Camera(float x, float y, float z, float moveSpeed) {
        this(new Vec3Df(x, y, z), moveSpeed);
    }

    /**
     * Moves the camera about 90 degrees
     *
     * @param direction the direction of movement
     */
    public void move(Direction direction) {

        switch (direction) {
            case RIGHT:
                right();
                vectorDesired = new Vec3Df(views[pointer].getX(), vectorActual.getY(), views[pointer].getZ());
                break;
            case LEFT:
                left();
                vectorDesired = new Vec3Df(views[pointer].getX(), vectorActual.getY(), views[pointer].getZ());
                break;
            case UP:
                vectorDesired = new Vec3Df(vectorActual.getX(), distance, vectorActual.getZ());
                break;
            case DOWN:
                vectorDesired = new Vec3Df(vectorActual.getX(), -distance, vectorActual.getZ());
                break;
            case THERE:
                zoomIn();
                break;
            case BACK:
                zoomOut();
                break;
        }
    }

    /**
     * Function intended for the rendering pipeline. It animates the camera movement
     * when the desired angle is different from the actual angle (vector)
     */
    public void animate() {
        float x = anim(vectorDesired.getX(), vectorActual.getX());
        float y = anim(vectorDesired.getY(), vectorActual.getY());
        float z = anim(vectorDesired.getZ(), vectorActual.getZ());
        vectorActual = new Vec3Df(x, y, z);
    }

    private float anim(float desired, float actual) {
        return canContinue(desired, actual) ? shift(desired, actual) : actual;
    }

    private float shift(float desired, float actual) {
        if (desired < actual) {
            return actual - (moveSpeed);
        }
        return actual + (moveSpeed);
    }

    private boolean canContinue(float desired, float actual) {
        if (desired < actual)
            return desired < actual;
        return actual < desired;
    }

    private void generateViews() {
        this.views[0] = new Vec3Df(-distance, distance, distance);
        this.views[1] = new Vec3Df(-distance, distance, -distance);
        this.views[2] = new Vec3Df(distance, distance, -distance);
        this.views[3] = new Vec3Df(distance, distance, distance);
    }


    private void right() {
        if (--pointer == -1) pointer = 3;

    }

    private void left() {
        if (++pointer == 4) pointer = 0;
    }

    public void zoomIn() {
        distance -= moveSpeed;
        generateViews();
        vectorDesired = new Vec3Df(views[pointer].getX(), distance, views[pointer].getZ());
    }

    public void zoomOut() {
        distance += moveSpeed;
        generateViews();
        vectorDesired = new Vec3Df(views[pointer].getX(), distance, views[pointer].getZ());
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getVActX() {
        return vectorActual.getX();
    }

    public float getVActY() {
        return vectorActual.getY();
    }

    public float getVActZ() {
        return vectorActual.getZ();
    }

    public Vec3Df getVectorActual() {
        return vectorActual;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }


    public void zoom(int amount) {
        if (amount != 0)
            distance += amount > 0 ? moveSpeed : -moveSpeed;
        generateViews();
        vectorDesired = new Vec3Df(views[pointer].getX(), distance, views[pointer].getZ());
    }
}
