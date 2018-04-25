package rubicCube.animation;

/**
 * Class that represents the state of any dynamically changing value
 * with the time. In this context it serves exclusively for segments' rotations.
 */
public class PropAnimation<T> {

    private Runnable callWhenStopped;
    private Animatable segment;
    private int target;
    private int playDirection;
    private PlayMode playMode = PlayMode.MULTIPLE;

    public PropAnimation(int target, Animatable animatable, PlayDirection playDirection) {
        this.segment = animatable;
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

    public PropAnimation(int target, Animatable animatable, PlayDirection playDirection, Runnable callWhenStopped) {
        this(target, animatable, playDirection);
        this.callWhenStopped = callWhenStopped;
    }

    public PropAnimation(int target, Animatable animatable, PlayDirection playDirection, Runnable callWhenStopped, PlayMode playMode) {
        this(target, animatable, playDirection);
        this.callWhenStopped = callWhenStopped;
        this.playMode = playMode;
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
     *
     * @param speed parameter influencing the speed (comes from FPS)
     * @return true if the animation can play the current "frame" -> else false
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
     *
     * @return true if can continue playing
     */
    private boolean canContinue() {
        return (segment.getState().getValue() <= target && playDirection == 1)
                || (segment.getState().getValue() >= target && playDirection == -1);
    }

    public T getAnimatable() {
        return (T) segment;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }
}
