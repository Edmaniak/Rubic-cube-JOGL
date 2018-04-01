package rubicCube;

import java.util.Arrays;
import java.util.Optional;

public class RubicCube {

    private final Cube[] cubes = new Cube[27];
    private final int[][][] buffer = new int[3][3][3];


    public RubicCube() {

        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                for (int x = 0; x < 3; x++) {
                    buffer[x][y][z] = x + (z * 3) + (y * 9);
                    float xf = (1 - x) * 2.1f;
                    float yf = (1 - y) * 2.1f;
                    float zf = (1 - z) * 2.1f;
                    cubes[i++] = new Cube(i-1, xf, yf, zf);
                }
    }

    public void rotateV(int column, Direction direction) {

        for (int y = 0; y < 3; y++) {
            for (int z = 0; z < 3; z++) {
                switch (direction) {
                    case THERE:
                        buffer[column][2 - z][y] = buffer[column][y][z];
                        break;
                    case BACK:
                        buffer[column][z][2 - y] = buffer[column][y][z];
                        break;
                }
            }
        }
    }

    public void rotateH(int row, Direction direction) {

        int[][][] tempBuffer = generateTemp();

        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
                switch (direction) {
                    case LEFT:
                        buffer[z][row][2 - x] = tempBuffer[x][row][z];
                        break;
                    case RIGHT:
                        buffer[2 - z][row][x] = tempBuffer[x][row][z];
                }

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

    public Cube[] getColumn(int index) {
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
}
