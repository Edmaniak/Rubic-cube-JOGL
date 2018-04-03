package rubicCube;

public class Animation {

    private int target;
    private int state;
    private boolean play;
    private int direction = 1;


    public Animation(int target) {
        this.target = target;
    }

    public void play() {
        direction = 1;
        play = true;
        state++;
    }

    public void playBackwadrs() {
        direction = -1;
        play = true;
        state--;
    }

    public void stop() {
        play = false;
    }

    public int animate() {
        if (play && state % target != 0)
            state += (1 * direction);
        else
            stop();
        return state;
    }


}
