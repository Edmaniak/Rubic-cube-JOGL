package rubicCube.model;

import rubicCube.Direction;
import rubicCube.State;
import rubicCube.Vec3Df;

import java.util.Random;

public class RubicCube {

    private final Cube[] cubes = new Cube[27];
    private final int[][][] buffer = new int[3][3][3];

    private final State[] xRots = new State[3];
    private final State[] yRots = new State[3];
    private final State[] zRots = new State[3];

    public static final int SHUFFLE_PARAMETER = 20;

    private float space;
    private float cubeSize;

    public RubicCube(float space, float cubeSize) {
        generateNew(space, cubeSize);
    }

    public RubicCube() {
        this(0, 0);
    }

    public Void rotateX(int x, Direction direction) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] colCubes = getXPlate(x);
        for (int c = 0; c < colCubes.length; c++)
            colCubes[c].rotateX(direction);

        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case THERE:
                        cubes[buffer[x][y][z]].setPosition(tempCubes[tempBuffer[x][2 - z][y]].getPosition());
                        break;
                    case BACK:
                        cubes[buffer[x][y][z]].setPosition(tempCubes[tempBuffer[x][z][2 - y]].getPosition());
                        break;
                }

        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case THERE:
                        buffer[x][2 - z][y] = tempBuffer[x][y][z];
                        break;
                    case BACK:
                        buffer[x][z][2 - y] = tempBuffer[x][y][z];
                        break;
                }

        return null;
    }

    public Void rotateY(int y, Direction direction) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] yCubes = getYPlate(y);
        for (int c = 0; c < yCubes.length; c++)
            yCubes[c].rotateY(direction);


        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case LEFT:
                        cubes[buffer[x][y][z]].setPosition(tempCubes[tempBuffer[z][y][2 - x]]);
                        break;
                    case RIGHT:
                        cubes[buffer[x][y][z]].setPosition(tempCubes[tempBuffer[2 - z][y][x]]);
                        break;
                }


        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case LEFT:
                        buffer[z][y][2 - x] = tempBuffer[x][y][z];
                        break;
                    case RIGHT:
                        buffer[2 - z][y][x] = tempBuffer[x][y][z];
                        break;
                }

        return null;
    }


    public Void rotateZ(int z, Direction direction) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] zCubes = getZPlate(z);
        for (int c = 0; c < zCubes.length; c++)
            zCubes[c].rotateZ(direction);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                switch (direction) {
                    case LEFT:
                        cubes[buffer[x][y][z]].setPosition(tempCubes[tempBuffer[2 - y][x][z]]);
                        break;
                    case RIGHT:
                        cubes[buffer[x][y][z]].setPosition(tempCubes[tempBuffer[y][2 - x][z]]);
                        break;
                }


        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                switch (direction) {
                    case LEFT:
                        buffer[2 - y][x][z] = tempBuffer[x][y][z];
                        break;
                    case RIGHT:
                        buffer[y][2 - x][z] = tempBuffer[x][y][z];
                        break;
                }

        return null;
    }

    private int[][][] generateTempBuffer() {
        int[][][] array = new int[3][3][3];
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 3; z++)
                    array[x][y][z] = buffer[x][y][z];

        return array;
    }

    private Cube[] generateTempCubes() {
        Cube[] tempCubes = new Cube[cubes.length];
        for (int i = 0; i < cubes.length; i++)
            tempCubes[i] = new Cube(cubes[i]);
        return tempCubes;
    }

    public Cube[] getYPlate(int index) {
        Cube[] plate = new Cube[9];
        int i = 0;
        for (int z = 0; z < 3; z++)
            for (int x = 0; x < 3; x++)
                plate[i++] = cubes[buffer[x][index][z]];

        return plate;
    }

    public Cube[] getXPlate(int index) {
        Cube[] plate = new Cube[9];
        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                plate[i++] = cubes[buffer[index][y][z]];

        return plate;
    }

    public Cube[] getZPlate(int index) {
        Cube[] plate = new Cube[9];
        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                plate[i++] = cubes[buffer[x][y][index]];

        return plate;
    }

    public void generateNew(float space, float cubeSize) {
        this.space = space;
        this.cubeSize = cubeSize;
        generateStateValues(xRots);
        generateStateValues(yRots);
        generateStateValues(zRots);
        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                for (int x = 0; x < 3; x++) {
                    buffer[x][y][z] = x + (z * 3) + (y * 9);
                    float xf = (x - 1) * (cubeSize + space);
                    float yf = (y - 1) * (cubeSize + space);
                    float zf = (1 - z) * (cubeSize + space);
                    cubes[i++] = new Cube(i - 1, new Vec3Df(xf, yf, zf), cubeSize, space);
                }
    }

    private void generateStateValues(State[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = new State();
        }
    }

    public void shuffle() {
        Random r = new Random();

        for (int r1 = 0; r1 < r.nextInt(SHUFFLE_PARAMETER); r1++) {
            int y = r.nextInt(2);
            rotateY(y, Direction.LEFT);
        }

        for (int r1 = 0; r1 < r.nextInt(SHUFFLE_PARAMETER); r1++) {
            int x = r.nextInt(2);
            rotateX(x, Direction.LEFT);
        }

        for (int r1 = 0; r1 < r.nextInt(SHUFFLE_PARAMETER); r1++) {
            int z = r.nextInt(2);
            rotateZ(z, Direction.LEFT);
        }
    }

    public Cube[] getCubes() {
        return cubes;
    }

    public State getXRot(int yIndex) {
        return xRots[yIndex];
    }

    public State getYRot(int xIndex) {
        return yRots[xIndex];
    }

    public State getZRot(int zIndex) {
        return zRots[zIndex];
    }


}
