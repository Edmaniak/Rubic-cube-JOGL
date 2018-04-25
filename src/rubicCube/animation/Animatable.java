package rubicCube.animation;

public interface Animatable<T> {

    State getState();
    T getObject();

}
