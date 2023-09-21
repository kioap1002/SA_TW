package hw2;

import java.util.ArrayList;

public class StateDiagram {
    LayoutAlgorithm layoutAlgorithm;
    ArrayList<LayoutAlgorithm> layouts = new ArrayList<LayoutAlgorithm>();

    public void draw(Graphics g) {
        System.out.println("Drawing State Diagram Graphics");
        for (LayoutAlgorithm l : layouts) {
            l.layout();
        }
    }

    public void setLayoutAlgo(LayoutAlgorithm a) {
        layoutAlgorithm = a;
        layoutAlgorithm.setDiagram(this);
        layouts.add(layoutAlgorithm);
    }
}
