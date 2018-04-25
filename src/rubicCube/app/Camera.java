package rubicCube.app;


import rubicCube.animation.State;
import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Vec3Df;

public class Camera {

    private Vec3Df vectorActual;
    private Vec3Df vectorDesired;
    private Vec3Df[] views = new Vec3Df[4];
    private float distance = 40;
    private float moveSpeed;
    private int pointer = 0;

    public Camera(Vec3Df vector, float moveSpeed) {
        this.vectorDesired = vector;
        this.vectorActual = vector;
        this.moveSpeed = moveSpeed;
        this.views[0] = new Vec3Df(-distance, distance, distance);
        this.views[1] = new Vec3Df(-distance, distance, -distance);
        this.views[2] = new Vec3Df(distance, distance, -distance);
        this.views[3] = new Vec3Df(distance, distance, distance);
    }

    public Camera(float x, float y, float z, float moveSpeed) {
        this(new Vec3Df(x, y, z), moveSpeed);
    }

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
        }
    }

    public void animate(float speed) {
        float x = anim(vectorDesired.getX(), vectorActual.getX(), speed);
        float y = anim(vectorDesired.getY(), vectorActual.getY(), speed);
        float z = anim(vectorDesired.getZ(), vectorActual.getZ(), speed);
        vectorActual = new Vec3Df(x, y, z);
    }


    private float anim(float desired, float actual, float speed) {
        return canContinue(desired, actual) ? shift(desired, actual, speed) : actual;
    }

    private float shift(float desired, float actual, float speed) {
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



    private int right() {
        if (--pointer == -1)
            pointer = 3;
        return pointer;

    }

    private int left() {
        if (++pointer == 4)
            pointer = 0;
        return pointer;
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
}
