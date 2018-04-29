package rubicCube.model.cube;

import rubicCube.animation.PlayMode;
import rubicCube.animation.PropAnimation;
import rubicCube.app.turn.Turn;
import rubicCube.model.color.Col;
import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Orientation;
import rubicCube.model.geometry.Vec3Df;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents structure, state and turn history linked to
 * rubic cube. Either all the associated actions in terms
 * of generating and rotating.
 */
public class RubicCube {

    private final Cube[] cubes = new Cube[27];
    private final int[][][] buffer = new int[3][3][3];
    private final ArrayList<Turn> turns;
    private final Segments segments = new Segments();
    private Vec3Df objectRotation = new Vec3Df();

    public static final int SHUFFLE_PARAMETER = 5;

    private float space;
    private float cubeSize;
    private static final Random r = new Random();

    public RubicCube(float space, float cubeSize) {
        this.turns = new ArrayList<>();
        generateStructure(space, cubeSize);
    }

    /**
     * Non visible rubic cube
     */
    public RubicCube() {
        this(0, 0);
    }

    /**
     * Rotates the segment logically around y axis.
     *
     * @param x         index of the concrete segment
     * @param direction of the rotation eg. LEFT, RIGHT
     * @param record    determines if the rotation should be added into the turns records
     */
    public void rotateX(int x, Direction direction, boolean record) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] colCubes = getXPlate(x);
        for (Cube colCube : colCubes) colCube.rotateX(direction);

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
        if (record)
            turns.add(new Turn(getSegment(Orientation.X, x), direction));
    }

    /**
     * Rotates the segment logically around y axis.
     *
     * @param y         index of the concrete segment
     * @param direction of the rotation eg. LEFT, RIGHT
     * @param record    determines if the rotation should be added into the turns records
     */
    public void rotateY(int y, Direction direction, boolean record) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] yCubes = getYPlate(y);
        for (Cube yCube : yCubes) yCube.rotateY(direction);


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
        if (record)
            turns.add(new Turn(getSegment(Orientation.Y, y), direction));
    }

    /**
     * Rotates the segment logically around z axis.
     *
     * @param z         index of the concrete segment
     * @param direction of the rotation eg. THERE, BACK
     * @param record    determines if the rotation should be added into the turns records
     */
    public void rotateZ(int z, Direction direction, boolean record) {

        // Arrays before rotation
        int[][][] tempBuffer = generateTempBuffer();
        Cube[] tempCubes = generateTempCubes();

        Cube[] zCubes = getZPlate(z);
        for (Cube zCube : zCubes) zCube.rotateZ(direction);

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
        if (record)
            turns.add(new Turn(getSegment(Orientation.Z, z), direction));
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

    /**
     * Universal way to get the concrete partial cubes array
     * based on the orientation and index of the plate
     *
     * @param orientation
     * @param index
     * @return
     */
    public Cube[] getPlate(Orientation orientation, int index) {
        switch (orientation) {
            case X:
                return getXPlate(index);
            case Y:
                return getYPlate(index);
            case Z:
                return getZPlate(index);
            default:
                return null;
        }
    }

    /**
     * Get all the partial cubes in the Y(index) plane
     *
     * @param index
     * @return
     */
    private Cube[] getYPlate(int index) {
        Cube[] plate = new Cube[9];
        int i = 0;
        for (int z = 0; z < 3; z++)
            for (int x = 0; x < 3; x++)
                plate[i++] = cubes[buffer[x][index][z]];

        return plate;
    }

    /**
     * Get all the partial cubes in the X(index) plane
     *
     * @param index
     * @return
     */
    private Cube[] getXPlate(int index) {
        Cube[] plate = new Cube[9];
        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int z = 0; z < 3; z++)
                plate[i++] = cubes[buffer[index][y][z]];

        return plate;
    }

    /**
     * Get all the partial cubes in the Z(index) plane
     *
     * @param index
     * @return
     */
    private Cube[] getZPlate(int index) {
        Cube[] plate = new Cube[9];
        int i = 0;
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                plate[i++] = cubes[buffer[x][y][index]];

        return plate;
    }

    /**
     * Generates all the logical back-end for rubic's cube based ont
     * the space between partial cubes and cubeSize
     *
     * @param space    between cubes
     * @param cubeSize the size of one partial cube within the rubic's cube
     */
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
        generateSegments();
        turns.clear();
    }

    public void generateStructure() {
        generateStructure(space, cubeSize);
    }

    /**
     * Generates logical segments of the cube 3*3
     */
    private void generateSegments() {
        for (int x = 0; x < 3; x++)
            segments.add(new Segment(getXPlate(x), Orientation.X, x));
        for (int y = 0; y < 3; y++)
            segments.add(new Segment(getYPlate(y), Orientation.Y, y));
        for (int z = 0; z < 3; z++)
            segments.add(new Segment(getZPlate(z), Orientation.Z, z));
    }

    /**
     * Mix the rubic cube randomly based on the steps parameter
     *
     * @param steps the amount of randomness
     */
    public void shuffle(int steps) {
        for (int i = 0; i < steps; i++) {
            rotateY(r.nextInt(2), Direction.LEFT, true);
            rotateZ(r.nextInt(2), Direction.LEFT, true);
            rotateX(r.nextInt(2), Direction.THERE, true);
        }
    }

    /**
     * Mix the rubic cube randomly based on the default
     * parameter RubicCube.SHUFFLE_PARAMETER
     */
    public void shuffle() {
        shuffle(SHUFFLE_PARAMETER);
    }

    /**
     * Get the concrete segment
     *
     * @param orientation rotation axis of the cubes
     * @param index       of the segment within the plane
     * @return
     */
    public Segment getSegment(Orientation orientation, int index) {
        Segment segment = segments.getSegment(orientation, index).get();
        segment.setCubes(getPlate(orientation, index));
        return segment;
    }

    /**
     * Get the appropriate "reverse-solving" function that is meant to be called
     * after segment animation is finished. The specific function
     * is defined by the turn that it is called for.
     *
     * @param turn the turn to be solved
     * @return
     */
    private Runnable getSolvingCallback(Turn turn) {
        switch (turn.getOrientation()) {
            case X:
                return () -> rotateX(turn.getIndex(), turn.getReverseDirection(), false);
            case Y:
                return () -> rotateY(turn.getIndex(), turn.getReverseDirection(), false);
            case Z:
                return () -> rotateZ(turn.getIndex(), turn.getReverseDirection(), false);
            default:
                return null;
        }
    }

    /**
     * Get the right animation in order to solve the turn
     *
     * @param turn the turn to be solved
     * @return
     */
    public PropAnimation solveTurn(Turn turn) {
        return new PropAnimation(90, turn.getSegment(), turn.getReversePlayDirection(), getSolvingCallback(turn), PlayMode.SINGLE);
    }

    /**
     * Simply checks if two border planes are in the same color.
     * If they are the rubic's cube's solved
     * @return true if it is solved
     */
    public boolean isSolved() {

        Cube[] cubesTop = getYPlate(2);
        Col topRefCol = cubesTop[0].getColorTopology().getTopColor();

        for (Cube cube : cubesTop)
            if (!cube.getColorTopology().getTopColor().isTheSame(topRefCol))
                return false;

        Cube[] cubesLeft = getXPlate(0);
        Col leftRefCol = cubesLeft[0].getColorTopology().getLeftColor();

        for(Cube cube : cubesLeft)
            if(!cube.getColorTopology().getLeftColor().isTheSame(leftRefCol))
                return false;

        return true;

    }

    public ArrayList<Segment> getSegments() {
        return segments.getSegments();
    }

    public ArrayList<Turn> getTurns() {
        return turns;
    }

    public Cube[] getCubes() {
        return cubes;
    }

    public void rotateCube(float dx, float dy) {
        objectRotation = objectRotation.add(dx, dy, 0);
    }
}
