package rubicCube;

public class State {

    private int value;
    private Status status = Status.IDLE;


    public int getValue() {
        return value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void increase() {
        value++;
    }

    public void decrease() {
        value--;
    }

    public void increase(int amount) {
        value += amount;
    }

    public void decrease(int amount) {
        value -= amount;
    }

    public void zero() {
        value = 0;
    }
}
