package rubicCube.app;

import rubicCube.animation.Animation;
import rubicCube.animation.PlayDirection;
import rubicCube.model.Segment;
import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Orientation;

public class Turn {

    private String notation;
    private static int order;
    private Segment segment;
    private int index;
    private Orientation orientation;
    private Direction direction;

    public Turn(Segment segment, Direction direction) {
        this.segment = segment;
        this.direction = direction;
        this.index = segment.getIndex();
        this.orientation = segment.getOrientation();
        this.notation = TurnParser.parseTurn(orientation, direction, index);
        this.order++;
    }

    public String getNotation() {
        return notation;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getReverseDirection() {
        if (direction == Direction.LEFT)
            return Direction.RIGHT;
        if (direction == Direction.RIGHT)
            return Direction.LEFT;
        if (direction == Direction.THERE)
            return Direction.BACK;
        if (direction == Direction.BACK)
            return Direction.THERE;
        return null;
    }

    public PlayDirection getReversePlayDirection() {
        if (notation.contains("'"))
            return PlayDirection.FORWARDS;
        return PlayDirection.BACKWARDS;
    }

    public Segment getSegment() {
        return segment;
    }

    public int getIndex() {
        return index;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public static int getOrder() {
        return order;
    }
}
