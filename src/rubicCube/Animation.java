package rubicCube;


import javax.swing.*;
import java.util.concurrent.Callable;

public class Animation {

    private int target;
    private boolean play;
    private int direction = 1;
    private Callable<Void> callWhenStopped;
    private State state;
    private Status groupRotating;

    public Animation(int target, State state, Status groupRotating) {
        this.target = target;
        this.state = state;
        this.groupRotating = groupRotating;
    }

    public void positive(Callable<Void> func) {
        if (state.getStatus() == Status.IDLE && groupRotating == Status.IDLE) {
            launch(func);
            direction = 1;
            state.increase();
        }
    }

    public void negative(Callable<Void> func) {
        if (state.getStatus() == Status.IDLE && groupRotating == Status.IDLE) {
            launch(func);
            state.decrease();
            direction = -1;
        }

    }

    public void stop() {
        try {
            callWhenStopped.call();
            callWhenStopped = null;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(App.frame, "Nebyla přiřazena funkce po rotaci", "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
        }
        play = false;
        state.zero();
        state.setStatus(Status.IDLE);
        groupRotating = Status.IDLE;
    }

    public void animate() {
        if (callWhenStopped == null)
            return;
        if (play && state.getValue() % target != 0)
            state.increase(direction);
        else
            stop();
    }

    private void launch(Callable<Void> func) {
        play = true;
        callWhenStopped = func;
        state.setStatus(Status.RUNNING);
        groupRotating = Status.RUNNING;
    }


}
