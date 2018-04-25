package rubicCube.animation;

import java.util.ArrayList;


public class Animation {

    private ArrayList<PropAnimation> propAnimations;

    public Animation(ArrayList<PropAnimation> propAnimations) {
        this.propAnimations = propAnimations;
    }

    public void addPropAnimation(PropAnimation propAnimation) {
        propAnimations.add(propAnimation);
    }

    public ArrayList<PropAnimation> getPropAnimations() {
        return propAnimations;
    }
}
