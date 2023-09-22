package hw2;

public abstract class LayoutAlgorithm {
    StateDiagram stateDiagram;

    public void setDiagram(StateDiagram d) {
        stateDiagram = d;
    }

    public abstract void layout();
}
