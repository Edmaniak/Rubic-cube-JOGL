package rubicCube;

import java.util.*;
import java.util.function.Consumer;

public class Segments {

    private ArrayList<Segment> segments;
    private int count;

    public Segments() {
        segments = new ArrayList<>();
    }

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
