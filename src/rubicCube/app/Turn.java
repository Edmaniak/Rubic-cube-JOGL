package rubicCube.app;

import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Orientation;

public class Turn {
    private String notation;
    private static int order;

    public Turn(Orientation orientation, Direction direction, int index) {
        this.notation = TurnParser.parseTurn(orientation, direction, index);
        this.order++;
    }

    public String getNotation() {
        return notation;
    }

    public static int getOrder() {
        return order;
    }
}
