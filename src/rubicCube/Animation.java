package rubicCube;


import javax.swing.*;
import java.util.concurrent.Callable;

public class Animation {

    private int target;
    private int playDirection;
    private Callable<Void> callWhenStopped;
    private Segment segment;
    private boolean isPlaying;

    public Animation(int target, Segment segment, PlayDirection playDirection, Callable<Void> callWhenStopped) {
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
        try {
            callWhenStopped.call();
            callWhenStopped = null;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(App.mainWindow, "Nebyla přiřazena funkce po rotaci", "FATAL ERROR", JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
        }
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
