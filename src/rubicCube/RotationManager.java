package rubicCube;

public class RotationManager {

    private Status isRotatingX = Status.IDLE;
    private Status isRotatingY = Status.IDLE;
    private Status isRotatingZ = Status.IDLE;

    public Status getIsRotatingX() {
        return isRotatingX;
    }

    public Status getIsRotatingY() {
        return isRotatingY;
    }

    public Status getIsRotatingZ() {
        return isRotatingZ;
    }

    public boolean canRotateX() {
        return (isRotatingY == Status.IDLE && isRotatingZ == Status.IDLE);
    }

    public boolean canRotateY() {
        return (isRotatingX == Status.IDLE && isRotatingZ == Status.IDLE);
    }

    public boolean canRotateZ() {
        return (isRotatingX == Status.IDLE && isRotatingY == Status.IDLE);
    }
}
