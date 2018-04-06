package rubicCube;

import java.util.Arrays;

public class RubicCube {

    private final Cube[] cubes = new Cube[27];
    private final int[][][] buffer = new int[3][3][3];

    private final State[] xRots = new State[3];
    private final State[] yRots = new State[3];
    private final State[] zRots = new State[3];

    private float space;
    private float cubeSize;

    public RubicCube(float space, float cubeSize) {
        generateRubicCube(space, cubeSize);
        generateStateValues(xRots);
        generateStateValues(yRots);
        generateStateValues(zRots);
        this.space = space;
        this.cubeSize = cubeSize;
    }


    public Void rotateX(int column, Direction direction) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] colCubes = getXPlate(column);
        for (int c = 0; c < colCubes.length; c++)
            colCubes[c].getRotation().rotateWithColumn(direction);

        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case THERE:
                        cubes[buffer[column][y][z]].setPosition(tempCubes[tempBuffer[column][2 - z][y]].getPosition());
                        break;
                    case BACK:
                        cubes[buffer[column][y][z]].setPosition(tempCubes[tempBuffer[column][z][2 - y]].getPosition());
                        break;
                }

        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case THERE:
                        buffer[column][2 - z][y] = tempBuffer[column][y][z];
                        break;
                    case BACK:
                        buffer[column][z][2 - y] = tempBuffer[column][y][z];
                        break;
                }

        for (int d = 0; d < cubes.length; d++) {
            System.out.println("Cube " + cubes[d].getIndex() + " => " + cubes[d].getRotation());
        }
        System.out.println(Arrays.deepToString(buffer));
        return null;
    }

    public Void rotateY(int row, Direction direction) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] rowCubes = getYPlate(row);
        for (int c = 0; c < rowCubes.length; c++)
            rowCubes[c].getRotation().rotateWithRow(direction);


        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case LEFT:
                        cubes[buffer[x][row][z]].setPosition(tempCubes[tempBuffer[z][row][2 - x]]);
                        break;
                    case RIGHT:
                        cubes[buffer[x][row][z]].setPosition(tempCubes[tempBuffer[2 - z][row][x]]);
                        break;
                }


        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case LEFT:
                        buffer[z][row][2 - x] = tempBuffer[x][row][z];
                        break;
                    case RIGHT:
                        buffer[2 - z][row][x] = tempBuffer[x][row][z];
                        break;
                }

        for (int d = 0; d < rowCubes.length; d++) {
            System.out.println("Cube " + rowCubes[d].getIndex() + " => " + rowCubes[d]);
        }
        System.out.println(Arrays.deepToString(buffer));

        return null;
    }


    public Void rotateZ(int index, Direction direction) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                switch (direction) {
                    case LEFT:
                        cubes[buffer[x][y][index]].setPosition(tempCubes[tempBuffer[2 - y][x][index]]);
                        break;
                    case RIGHT:
                        cubes[buffer[x][y][index]].setPosition(tempCubes[tempBuffer[y][2 - x][index]]);
                        break;
                }


        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                switch (direction) {
                    case LEFT:
                        buffer[2 - y][x][index] = tempBuffer[x][y][index];
                        break;
                    case RIGHT:
                        buffer[y][2 - x][index] = tempBuffer[x][y][index];
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

    private void generateRubicCube(float space, float cubeSize) {
        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                for (int x = 0; x < 3; x++) {
                    buffer[x][y][z] = x + (z * 3) + (y * 9);
                    float xf = (x - 1) * (cubeSize + space);
                    float yf = (y - 1) * (cubeSize + space);
                    float zf = (1 - z) * (cubeSize + space);
                    cubes[i++] = new Cube(i - 1, new Vec3Df(xf, yf, zf), cubeSize);
                }
    }

    private void generateStateValues(State[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = new State();
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
