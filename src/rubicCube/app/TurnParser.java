package rubicCube.app;

import rubicCube.model.geometry.Direction;
import rubicCube.model.geometry.Orientation;

/**
 * Static class for parsing the turn to the standardized
 * rubic cube notation
 */
public class TurnParser {
    public static String parseTurn(Orientation orientation, Direction direction, int index) {
        String notation = "";

        if (orientation == Orientation.X && index == 0)
            notation += "F";
        if (orientation == Orientation.X && index == 1)
            notation += "S";
        if (orientation == Orientation.X && index == 2)
            notation += "B";

        if (orientation == Orientation.Y && index == 0)
            notation += "D";
        if (orientation == Orientation.Y && index == 1)
            notation += "E";
        if (orientation == Orientation.Y && index == 2)
            notation += "U";

        if (orientation == Orientation.Z && index == 0)
            notation += "R";
        if (orientation == Orientation.Z && index == 1)
            notation += "M";
        if (orientation == Orientation.Z && index == 2)
            notation += "L";

        if(direction == Direction.RIGHT || direction == Direction.THERE)
            notation += "'";

        return notation;
    }


}
