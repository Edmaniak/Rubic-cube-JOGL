package rubicCube;

public class Rotation {

    private int rotX;
    private int rotY;
    private int rotZ;

    public Rotation() {

    }

    public Rotation(Rotation rotation) {
        this.rotY = rotation.rotY;
        this.rotZ = rotation.rotZ;
        this.rotX = rotation.rotX;
    }

    public void rotateY(Direction direction) {
        switch (direction) {
            case RIGHT:
                rotY += 90;
                if (rotY >= 360)
                    rotY = 0;
                break;
            case LEFT:
                rotY -= 90;
                if (rotY <= -360)
                    rotY = 0;
                break;
        }
    }

    public void rotateX(Direction direction) {
        switch (direction) {
            case BACK:
                rotX += 90;
                if (rotX >= 360)
                    rotX = 0;
                break;
            case THERE:
                rotX -= 90;
                if (rotX <= -360)
                    rotX = 0;
                break;
        }
    }

    public Vec3Di getRotVecForRow() {
        int x = 0;
        int y = 0;
        int z = 0;

        return new Vec3Di(x, y, z);
    }

    public Vec3Di getRotVecForCol() {
        int x = 0;
        int y = 0;
        int z = 0;
        if ((rotY % 180) == 0) {
            x = 1;
            z = 0;
        } else {
            x = 0;
            z = 1;
        }
        return new Vec3Di(x, y, z);
    }

    public int getRotY() {
        return rotY;
    }

    public int getRotX() {
        return rotX;
    }

    public int getRotZ() {
        return rotZ;
    }

    @Override
    public String toString() {
        return "X: " + " ("+ getRotVecForCol().getX()+ ") " + rotX + " Y: " + " ("+ getRotVecForCol().getY()+ ") " + + rotY + " Z: " + " ("+ getRotVecForCol().getZ()+ ") " + + rotZ;
    }
}
