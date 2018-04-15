package rubicCube.animation;

import rubicCube.model.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Animator {

    private final List<Animation> playList;
    private final List<Animation> toRemove;
    private final Runnable turnFunction;


    public Animator(Runnable turnFunction) {
        playList = new ArrayList<>();
        toRemove = new ArrayList<>();
        this.turnFunction = turnFunction;
    }

    public void play(float speed) {
        for (Animation a : playList)
            if (!a.play(speed)) {
                toRemove.add(a);
                turnFunction.run();
            }
        check();
    }

    public void addToPlaylist(Animation animation) {
        if (canBeAdded(animation))
            playList.add(animation);
    }

    public void check() {
        for (Animation a : toRemove)
            playList.remove(a);
        toRemove.clear();
    }

    private boolean isSegmentRotating(Segment segment) {
        for (Animation animation : playList)
            if (segment.equals(animation.getSegment()))
                return true;
        return false;
    }

    private boolean isCrossAnimation(Segment segment) {
        for (Animation animation : playList)
            if (animation.getSegment().getOrientation() != segment.getOrientation())
                return true;
        return false;
    }

    private boolean canBeAdded(Animation animation) {
        return !isSegmentRotating(animation.getSegment()) && !isCrossAnimation(animation.getSegment());
    }

}
