package hw2;

import java.util.ArrayList;

public abstract class LayoutAlgorithm {
    StateDiagram stateDiagram;

    public void setDiagram(StateDiagram d) {
        stateDiagram = d;
    }

    public abstract void layout();
}
