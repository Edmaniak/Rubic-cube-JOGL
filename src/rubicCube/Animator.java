package rubicCube;

import java.util.ArrayList;
import java.util.List;

public class Animator {

    private final List<Animation> playList;
    private final List<Animation> toRemove;

    public Animator() {
        playList = new ArrayList<>();
        toRemove = new ArrayList<>();
    }

    public void play(float speed) {
        for (Animation a : playList) if (!a.play(speed)) toRemove.add(a);
        check();
    }

    public void addToPlayList(Animation animation) {
        playList.add(animation);
    }

    public void check() {
        for(Animation a : toRemove) playList.remove(a);
        toRemove.clear();
    }

}
