package rubicCube.animation;

/**
 * State of animatable object value. As a referable object
 * it is very convinient since it can be linked to multiple
 * objects as one dynamic value.
 */
public class State {

    private float value;
    private Status status = Status.IDLE;

    public float getValue() {
        return value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void increase() { value += 0.1f; }

    public void decrease() {
        value -= 0.1f;
    }

    public void increase(float amount) {
        value += amount;
    }

    public void decrease(float amount) {
        value -= amount;
    }

    public void zero() {
        value = 0;
    }
}
