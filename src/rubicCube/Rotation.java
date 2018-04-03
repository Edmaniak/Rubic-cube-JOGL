package rubicCube;

public class Rotation {

    private int rotX;
    private int rotY;
    private int rotZ;

    public Rotation() {

    }

    public void rotateWithRow(Direction direction) {
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

    public void rotateWithColumn(Direction direction) {
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

    public Vec3D getRotVecForRow() {
        int x = 0;
        int y = 0;
        int z = 0;

        return new Vec3D(x, y, z);
    }

    public Vec3D getRotVecForCol() {
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
        return new Vec3D(x, y, z);
    }

    public int getRotY() {
        return rotY;
    }

    @Override
    public String toString() {
        return "X: " + rotX + " Y: " + rotY + " Z: " + rotZ;
    }
}
