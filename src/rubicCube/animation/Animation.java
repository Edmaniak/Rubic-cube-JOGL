package rubicCube.animation;


import rubicCube.app.App;
import rubicCube.model.Segment;

import javax.swing.*;
import java.util.concurrent.Callable;

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

    public void stop() {
        callWhenStopped.run();
        callWhenStopped = null;
        segment.getState().zero();
        segment.getState().setStatus(Status.IDLE);
    }

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

    private boolean canContinue() {
        return (segment.getState().getValue() <= target && playDirection == 1)
                || (segment.getState().getValue() >= target && playDirection == -1);
    }

    public Segment getSegment() {
        return segment;
    }
}
