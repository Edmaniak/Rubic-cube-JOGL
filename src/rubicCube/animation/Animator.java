package rubicCube.animation;

import rubicCube.model.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Animation manager that serves as a coordinator for
 * synchronous animations. It checks if the animations
 * can be played together and manages the animation playlist.
 */
public class Animator {

    private final List<Animation> playList;
    private final List<Animation> toRemove;
    private final Runnable turnFunction;

    public Animator(Runnable turnFunction) {
        playList = new ArrayList<>();
        toRemove = new ArrayList<>();
        this.turnFunction = turnFunction;
    }

    /**
     * Play all the animations from the playlist. If the animations
     * are in the same rotation plane they can be played synchronously.
     * @param speed speed parameter that comes from the actual FPS
     */
    public void play(float speed) {
        for (Animation a : playList)
            if (!a.play(speed)) {
                toRemove.add(a);
                turnFunction.run();
            }
        check();
    }

    /**
     * Add the animation to the queue
     * @param animation
     */
    public void addToPlaylist(Animation animation) {
        if (canBeAdded(animation))
            playList.add(animation);
    }

    /**
     * It checks whether there're the animations to be removed
     * and it removes them from the playlist
     */
    private void check() {
        for (Animation a : toRemove)
            playList.remove(a);
        toRemove.clear();
    }

    /**
     * It checks whether the segment is rotating in the current time
     * @param segment the tested segment
     * @return
     */
    private boolean isSegmentRotating(Segment segment) {
        for (Animation animation : playList)
            if (segment.equals(animation.getSegment()))
                return true;
        return false;
    }

    /**
     * It checks whether an another segment is rotating within the same orientation plane
     * eg. Row 1 and Row 2.
     * @param segment the tested segment
     * @return
     */
    private boolean isCrossAnimation(Segment segment) {
        for (Animation animation : playList)
            if (animation.getSegment().getOrientation() != segment.getOrientation())
                return true;
        return false;
    }

    /**
     * It checks whether the animation (rotation) can be added, thus
     * segment that is animated is not rotated right now and there is no
     * animation going on in the other rotation plane
     * @param animation the desired animation for addition
     * @return
     */
    private boolean canBeAdded(Animation animation) {
        return !isSegmentRotating(animation.getSegment()) && !isCrossAnimation(animation.getSegment());
    }

}
