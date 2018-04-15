package rubicCube.model;

import rubicCube.model.geometry.Topology;

public class Side {
    private Col color;
    private int index;
    private Topology topology;

    public Side(int index, Topology topology, Col color) {
        this.color = color;
        this.topology = topology;
        this.index = index;
    }

    public Side(Side side) {
        this(side.getIndex(), side.getTopology(), side.getColor());
    }

    public Col getColor() {
        return color;
    }

    public int getIndex() {
        return index;
    }

    public Topology getTopology() {
        return topology;
    }

    @Override
    public String toString() {
        return topology.toString() + " (" + index +")" + " - " + color.toString();
    }

    public void setColor(Col color) {
        this.color = color;
    }
}
