package rubicCube.animation;

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

    public void increase() {
        value += 0.1f;
    }

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
