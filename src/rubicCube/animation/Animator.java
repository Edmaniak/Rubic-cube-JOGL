package rubicCube.animation;

import rubicCube.model.cube.Segment;

import java.util.ArrayList;
import java.util.List;

/**
 * PropAnimation manager that serves as a coordinator for
 * synchronous animations. It checks if the animations
 * can be played together and manages the animation playlist.
 */
public class Animator {

    private final List<PropAnimation<Segment>> segmentPropAnimationList;
    private final List<PropAnimation> toRemove;
    private final Runnable turnFunction;

    public Animator(Runnable turnFunction) {
        segmentPropAnimationList = new ArrayList<>();
        toRemove = new ArrayList<>();
        this.turnFunction = turnFunction;
    }

    /**
     * Play all the animations from the playlist. If the animations
     * are in the same rotation plane they can be played synchronously.
     *
     * @param speed speed parameter that comes from the actual FPS
     */
    public void play(float speed) {
        for (PropAnimation a : segmentPropAnimationList) {
            if (!a.play(speed)) {
                toRemove.add(a);
                turnFunction.run();
            }
            if (a.getPlayMode() == PlayMode.SINGLE)
                break;
        }
        check();
    }

    /**
     * Add the propAnimation to the queue
     *
     * @param propAnimation
     */
    public void addToPlaylist(PropAnimation<Segment> propAnimation) {

        if (canBeAdded(propAnimation)) {
            segmentPropAnimationList.add(propAnimation);
        }

    }

    /**
     * It checks whether there're the animations to be removed
     * and it removes them from the playlist
     */
    private void check() {
        for (PropAnimation a : toRemove)
            segmentPropAnimationList.remove(a);
        toRemove.clear();
    }

    /**
     * It checks whether the segment is rotating in the current time
     *
     * @param segment the tested segment
     * @return
     */
    private boolean isSegmentRotating(Segment segment) {
        for (PropAnimation propAnimation : segmentPropAnimationList)
            if (segment.equals(propAnimation.getAnimatable()))
                return true;
        return false;
    }

    /**
     * It checks whether an another segment is rotating within the same orientation plane
     * eg. Row 1 and Row 2.
     *
     * @param segment the tested segment
     * @return
     */
    private boolean isCrossAnimation(Segment segment) {
        for (PropAnimation<Segment> propAnimation : segmentPropAnimationList)
            if (propAnimation.getAnimatable().getOrientation() != segment.getOrientation())
                return true;
        return false;
    }

    /**
     * It checks whether the propAnimation (rotation) can be added, thus
     * segment that is animated is not rotated right now and there is no
     * propAnimation going on in the other rotation plane
     *
     * @param propAnimation the desired propAnimation for addition
     * @return
     */
    private boolean canBeAdded(PropAnimation<Segment> propAnimation) {
        return propAnimation.getPlayMode() == PlayMode.SINGLE || !isSegmentRotating(propAnimation.getAnimatable()) && !isCrossAnimation(propAnimation.getAnimatable());
    }


}
