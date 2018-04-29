package rubicCube.model.cube;

import rubicCube.model.geometry.Orientation;

import java.util.*;

/**
 * Collection for rubic cube segments
 */
public class Segments {

    private final ArrayList<Segment> segments;

    public Segments() {
        segments = new ArrayList<>();
    }

    /**
     * Special method for getting desired segment based on the
     * orientation and index parameter
     * @param orientation the orientation plane of the segment
     * @param index the index of the segment
     * @return
     */
    public Optional<Segment> getSegment(Orientation orientation, int index) {
        for (Segment segment : segments)
            if (segment.getIndex() == index && segment.getOrientation() == orientation)
                return Optional.of(segment);
        return Optional.empty();
    }

    public void add(Segment segment) {
        segments.add(segment);
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }


}
