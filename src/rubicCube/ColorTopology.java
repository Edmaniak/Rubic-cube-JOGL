package rubicCube;

public class ColorTopology {

    public static final float DEFAULT_DARKNESS = 0.1f;

    private Side front;
    private Side back;
    private Side left;
    private Side right;
    private Side bottom;
    private Side top;

    private int[] bufferX = new int[4];
    private int[] bufferY = new int[4];
    private int[] bufferZ = new int[4];

    private Side[] sides = new Side[6];


    // FRONT RIGHT BACK LEFT TOP BOTTOM
    public ColorTopology(Col backColor, Col frontColor, Col leftColor, Col rightColor, Col bottomColor, Col topColor) {

        front = new Side(0, Topology.FRONT, frontColor);
        back = new Side(2, Topology.BACK, backColor);
        left = new Side(3, Topology.LEFT, leftColor);
        right = new Side(1, Topology.RIGHT, rightColor);
        bottom = new Side(5, Topology.BOTTOM, bottomColor);
        top = new Side(4, Topology.TOP, topColor);

        sides[0] = front;
        sides[1] = right;
        sides[2] = back;
        sides[3] = left;
        sides[4] = top;
        sides[5] = bottom;

        bufferZ[0] = 3; // left
        bufferZ[1] = 4; // top
        bufferZ[2] = 1; // right
        bufferZ[3] = 5; // bottom

        bufferY[0] = 3; // left
        bufferY[1] = 0; // front
        bufferY[2] = 1; // right
        bufferY[3] = 2; // back

        bufferX[0] = 0; // front
        bufferX[1] = 4; // top
        bufferX[2] = 2; // back
        bufferX[3] = 5; // bottom

    }

    public ColorTopology() {
        this(
                new Col(DEFAULT_DARKNESS),
                new Col(DEFAULT_DARKNESS),
                new Col(DEFAULT_DARKNESS),
                new Col(DEFAULT_DARKNESS),
                new Col(DEFAULT_DARKNESS),
                new Col(DEFAULT_DARKNESS)
        );
    }

    public ColorTopology(ColorTopology ct) {
        this(
                ct.getBackColor(),
                ct.getFrontColor(),
                ct.getLeftColor(),
                ct.getRightColor(),
                ct.getBottomColor(),
                ct.getTopColor()
        );
    }

    private void shiftPositiveRight(int[] array) {
        Side[] tempSides = generateTempSides();
        int temp = array[array.length - 1];
        for (int i = array.length - 2; i >= 0; i--) {
            sides[array[i + 1]].setColor(tempSides[array[i]].getColor());
            array[i + 1] = array[i];
        }
        sides[array[0]] = tempSides[temp];
        array[0] = temp;
    }

    private void shiftNegativeLeft(int[] array) {
        Side[] tempSides = generateTempSides();
        int temp = array[0];
        for (int i = 0; i < array.length - 1; i++) {
            sides[array[i]].setColor(tempSides[array[i + 1]].getColor());
            array[i] = array[i + 1];
        }
        sides[array[array.length - 1]] = tempSides[temp];
        array[array.length - 1] = temp;
    }

    private Side[] generateTempSides() {
        Side[] tempSides = new Side[6];
        for (int i = 0; i < sides.length; i++)
            tempSides[i] = new Side(sides[i]);
        return tempSides;
    }


    public void rotateY(Direction direction) {
        switch (direction) {
            case LEFT:
                shiftNegativeLeft(bufferY);
                break;
            case RIGHT:
                shiftPositiveRight(bufferY);
                break;
        }
    }

    public void rotateX(Direction direction) {
        switch (direction) {
            case THERE:
                shiftNegativeLeft(bufferX);
                break;
            case BACK:
                shiftPositiveRight(bufferX);
                break;
        }
    }

    public void rotateZ(Direction direction) {
        switch (direction) {
            case LEFT:
                shiftNegativeLeft(bufferZ);
                break;
            case RIGHT:
                shiftPositiveRight(bufferZ);
                break;
        }
    }

    public Side getFront() {
        return front;
    }

    public Side getBack() {
        return back;
    }

    public Side getBottom() {
        return bottom;
    }

    public Side getTop() {
        return top;
    }

    public Side getLeft() {
        return left;
    }

    public Side getRight() {
        return right;
    }

    public Col getFrontColor() {
        return front.getColor();
    }

    public Col getBackColor() {
        return back.getColor();
    }

    public Col getBottomColor() {
        return bottom.getColor();
    }

    public Col getTopColor() {
        return top.getColor();
    }

    public Col getLeftColor() {
        return left.getColor();
    }

    public Col getRightColor() {
        return right.getColor();
    }

    public void setFrontColor(Col col) {
        front.setColor(col);
    }

    public void setBackColor(Col col) {
        back.setColor(col);
    }

    public void setBottomColor(Col col) {
        bottom.setColor(col);
    }

    public void setTopColor(Col col) {
        top.setColor(col);
    }

    public void setLeftColor(Col col) {
        left.setColor(col);
    }

    public void setRightColor(Col col) {
        right.setColor(col);
    }
}
