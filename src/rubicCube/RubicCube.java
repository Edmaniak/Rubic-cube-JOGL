package rubicCube;

import java.util.Arrays;
import java.util.Optional;

public class RubicCube {

    private Cube[] cubes = new Cube[21];
    private int[][][] buffer = new int[3][3][3];

    public RubicCube() {

        for (int i = 0; i < cubes.length; i++)
            cubes[i] = new Cube(i);

        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                for (int z = 0; z < 3; z++)
                    buffer[x][y][z] = x + (z * 3) + (y * 6);

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
        for (int x = 0; x < 3; x++)
            for (int z = 0; z < 3; z++)
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

}
