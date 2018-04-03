package rubicCube;

import java.util.Arrays;

public class RubicCube {

    private final Cube[] cubes = new Cube[27];
    private final int[][][] buffer = new int[3][3][3];

    private final Integer[] rowRots = new Integer[3];
    private final Integer[] colRots = new Integer[3];


    public RubicCube() {

        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                for (int x = 0; x < 3; x++) {
                    buffer[x][y][z] = x + (z * 3) + (y * 9);
                    float xf = (x - 1) * 2.1f;
                    float yf = (y - 1) * 2.1f;
                    float zf = (1 - z) * 2.1f;
                    cubes[i++] = new Cube(i - 1, xf, yf, zf);
                }
    }

    public void rotateCol(int column, Direction direction) {

        int[][][] tempBuffer = generateTemp();

        Cube[] rowCubes = getCol(column);
        for (int c = 0; c < rowCubes.length; c++) {
            rowCubes[c].getRotation().rotateWithColumn(direction);
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

    }

    public void rotateRow(int row, Direction direction) {

        int[][][] tempBuffer = generateTemp();

        Cube[] rowCubes = getRow(row);
        for (int c = 0; c < rowCubes.length; c++) {
            rowCubes[c].getRotation().rotateWithRow(direction);
        }

        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case LEFT:
                        buffer[z][row][2 - x] = tempBuffer[x][row][z];
                        break;
                    case RIGHT:
                        buffer[2 - z][row][x] = tempBuffer[x][row][z];
                }

        for (int d = 0; d < cubes.length; d++) {
            System.out.println("Cube " + cubes[d].getIndex() + " => " + cubes[d].getRotation());
        }
        System.out.println(Arrays.deepToString(buffer));


    }

    private int[][][] generateTemp() {
        int[][][] array = new int[3][3][3];
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 3; z++)
                    array[x][y][z] = buffer[x][y][z];

        return array;
    }

    public Cube[] getRow(int index) {
        Cube[] row = new Cube[9];
        int i = 0;
        for (int z = 0; z < 3; z++)
            for (int x = 0; x < 3; x++)
                row[i++] = cubes[buffer[x][index][z]];

        return row;
    }

    public Cube[] getCol(int index) {
        Cube[] column = new Cube[9];
        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                column[i++] = cubes[buffer[index][y][z]];

        return column;
    }

    public Cube[] getCubes() {
        return cubes;
    }

    public int getColRot(int colIndex) {
        return colRots[colIndex];
    }

    public int getRowRot(int rowIndex) {
        return rowRots[rowIndex];
    }

    public void setRowRot(int rowIndex, int value) {
        rowRots[rowIndex] = value;
    }

    public void setColRot(int colIndex, int value) {
        colRots[colIndex] = value;
    }
}
