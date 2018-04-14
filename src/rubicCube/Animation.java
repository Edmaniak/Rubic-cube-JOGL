package rubicCube;


import javax.swing.*;
import java.util.concurrent.Callable;

public class Animation {

    private int target;
    private int playDirection;
    private Callable<Void> callWhenStopped;
    private State state;

    public Animation(int target, State state, PlayDirection playDirection, Callable<Void> callWhenStopped) {
        this.state = state;
        this.callWhenStopped = callWhenStopped;
        switch (playDirection) {
            case FORWARDS:
                this.playDirection = 1;
                this.state.increase();
                break;
            case BACKWARDS:
                this.playDirection = -1;
                this.state.decrease();
                break;
        }
        this.target = this.playDirection * target;
    }

    public void stop() {
        try {
            callWhenStopped.call();
            callWhenStopped = null;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(App.mainWindow, "Nebyla přiřazena funkce po rotaci", "FATAL ERROR",JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        state.zero();
    }

    public boolean play(float speed) {
        if (state.getValue() - target >= 0) {
            state.increase(playDirection * speed);
            return true;
        } else {
            stop();
            return false;
        }
    }

}
