package rubicCube.model;

import rubicCube.app.Turn;
import rubicCube.app.TurnParser;
import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Orientation;
import rubicCube.model.geometry.Vec3Df;

import java.util.ArrayList;
import java.util.Random;

public class RubicCube {

    private final Cube[] cubes = new Cube[27];
    private final int[][][] buffer = new int[3][3][3];
    private final ArrayList<Turn> turns;
    private final Segments segments = new Segments();

    public static final int SHUFFLE_PARAMETER = 20;

    private float space;
    private float cubeSize;
    private int cubeCount;
    private static Random r = new Random();

    public RubicCube(float space, float cubeSize) {
        this.turns = new ArrayList<>();
        generateStructure(space, cubeSize);
        generateSegments();
    }

    public RubicCube() {
        this(0, 0);
    }

    public void rotateX(int x, Direction direction) {

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

        turns.add(new Turn(Orientation.X,direction,x));
    }

    public void rotateY(int y, Direction direction) {

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

        turns.add(new Turn(Orientation.Y, direction, y));
    }


    public void rotateZ(int z, Direction direction) {

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

        turns.add(new Turn(Orientation.Z,direction,z));
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

    public Cube[] getPlate(Orientation orientation, int index) {
        switch (orientation) {
            case X:
                return getXPlate(index);
            case Y:
                return getYPlate(index);
            case Z:
                return getZPlate(index);
        }
        return null;
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

    public void generateStructure(float space, float cubeSize) {
        this.space = space;
        this.cubeSize = cubeSize;
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

    public void generateStructure() {
        generateStructure(space, cubeSize);
    }


    private void generateSegments() {
        for (int x = 0; x < 3; x++)
            segments.add(new Segment(getXPlate(x), Orientation.X, x));
        for (int y = 0; y < 3; y++)
            segments.add(new Segment(getYPlate(y), Orientation.Y, y));
        for (int z = 0; z < 3; z++)
            segments.add(new Segment(getZPlate(z), Orientation.Z, z));
    }


    public void shuffle() {

        for (int r1 = 0; r1 < r.nextInt(SHUFFLE_PARAMETER); r1++)
            rotateY(r.nextInt(2), Direction.LEFT);

        for (int r1 = 0; r1 < r.nextInt(SHUFFLE_PARAMETER); r1++)
            rotateX(r.nextInt(2), Direction.LEFT);

        for (int r1 = 0; r1 < r.nextInt(SHUFFLE_PARAMETER); r1++)
            rotateZ(r.nextInt(2), Direction.LEFT);

    }

    public Cube[] getCubes() {
        return cubes;
    }

    public Segment getSegment(Orientation orientation, int index) {
        Segment segment = segments.getSegment(orientation, index).get();
        segment.setCubes(getPlate(orientation, index));
        return segment;
    }

    public ArrayList<Segment> getSegments() {
        return segments.getSegments();
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    public int getCubeCount() {
        return cubeCount;
    }
}
