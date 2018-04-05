package rubicCube;

import javax.swing.*;
import java.util.concurrent.Callable;

public class Animation {

    private int target;
    private int state;
    private boolean play;
    private int direction = 1;
    private Callable<Void> callWhenStopped;

    public Animation(int target) {
        this.target = target;
    }

    public void positive(Callable<Void> func) {
        direction = 1;
        play = true;
        state++;
        callWhenStopped = func;
    }

    public void negative(Callable<Void> func) {
        direction = -1;
        play = true;
        state--;
        callWhenStopped = func;
    }

    public void stop() {
        try {
            callWhenStopped.call();
            callWhenStopped = null;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(App.frame, "Nebyla přiřazena funkce po rotaci", "FATAL ERROR", JOptionPane.ERROR_MESSAGE);
        }
        play = false;
        state = 0;
    }

    public int animate() {
        if (callWhenStopped == null)
            return state;
        if (play && state % target != 0)
            state += (1 * direction);
        else
            stop();
        return state;
    }


}
