package rubicCube;

public class RotationManager {

    private Boolean isRotatingX = new Boolean(false);
    private Boolean isRotatingY = new Boolean(false);
    private Boolean isRotatingZ = new Boolean(false);

    public Boolean getIsRotatingX() {
        return isRotatingX;
    }

    public Boolean getIsRotatingY() {
        return isRotatingY;
    }

    public Boolean getIsRotatingZ() {
        return isRotatingZ;
    }

    public boolean canRotateX() {
        return (isRotatingY && isRotatingZ) == false;
    }

    public boolean canRotateY() {
        return (isRotatingX && isRotatingZ) == false;
    }

    public boolean canRotateZ() {
        return (isRotatingX && isRotatingY) == false;
    }
}
