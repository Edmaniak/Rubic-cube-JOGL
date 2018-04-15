package rubicCube.animation;

import rubicCube.model.Segment;

/**
 * Class that represents the state of any dynamically changing value
 * in the time. In this context it serves exclusively for segments rotations.
 */
public class Animation {

    private int target;
    private int playDirection;
    private Runnable callWhenStopped;
    private Segment segment;

    public Animation(int target, Segment segment, PlayDirection playDirection, Runnable callWhenStopped) {
        this.segment = segment;
        this.callWhenStopped = callWhenStopped;
        switch (playDirection) {
            case FORWARDS:
                this.playDirection = 1;
                this.segment.getState().increase();
                break;
            case BACKWARDS:
                this.playDirection = -1;
                this.segment.getState().decrease();
                break;
        }
        this.target = this.playDirection * target;
    }

    /**
     * Stops the going animation
     */
    public void stop() {
        if (segment.getState().getStatus() == Status.INMOTION) {
            callWhenStopped.run();
            callWhenStopped = null;
            segment.getState().zero();
            segment.getState().setStatus(Status.IDLE);
        }
    }

    /**
     * Plays the animation. It will automatically stop when the
     * target is reached.
     * @param speed parameter influencing the speed (comes from FPS)
     * @return
     */
    public boolean play(float speed) {
        if (canContinue()) {
            segment.getState().increase(playDirection * speed);
            segment.getState().setStatus(Status.INMOTION);
            return true;
        } else {
            stop();
            return false;
        }
    }

    /**
     * Checks whether the animation can keep going. The logic depends on the
     * current value, defined target and playDirection;
     * @return
     */
    private boolean canContinue() {
        return (segment.getState().getValue() <= target && playDirection == 1)
                || (segment.getState().getValue() >= target && playDirection == -1);
    }

    public Segment getSegment() {
        return segment;
    }
}
