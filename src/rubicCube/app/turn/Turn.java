package rubicCube.app.turn;

import rubicCube.animation.PlayDirection;
import rubicCube.model.cube.Segment;
import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Orientation;

/**
 * Represents the data structure needed for turn representation.
 * One turn is one rotation done by player
 */
public class Turn {

    private final String notation;
    private static int order;
    private final Segment segment;
    private final int index;
    private final Orientation orientation;
    private final Direction direction;

    public Turn(Segment segment, Direction direction) {
        this.segment = segment;
        this.direction = direction;
        this.index = segment.getIndex();
        this.orientation = segment.getOrientation();
        this.notation = TurnParser.parseTurn(orientation, direction, index);
        order++;
    }

    /**
     * Returns the reverse direction compared to the actual
     * turn direction.
     * @return
     */
    public Direction getReverseDirection() {
        if (direction == Direction.LEFT) return Direction.RIGHT;
        if (direction == Direction.RIGHT) return Direction.LEFT;
        if (direction == Direction.THERE) return Direction.BACK;
        if (direction == Direction.BACK) return Direction.THERE;
        return null;
    }

    /**
     * Returns the reverse direction compared to the actual
     * turn direction.
     * @return
     */
    public PlayDirection getReversePlayDirection() {
        if (direction == Direction.RIGHT && orientation == Orientation.Y) return PlayDirection.BACKWARDS;
        if(direction == Direction.LEFT && orientation == Orientation.Z) return PlayDirection.BACKWARDS;
        if(direction == Direction.BACK && orientation == Orientation.X) return PlayDirection.BACKWARDS;
        return PlayDirection.FORWARDS;
    }

    public String getNotation() {
        return notation;
    }

    public Direction getDirection() {
        return direction;
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
