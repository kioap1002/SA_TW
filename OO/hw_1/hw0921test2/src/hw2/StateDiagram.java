package hw2;

public class StateDiagram {
    LayoutAlgorithm layoutAlgorithm;

    public void draw(Graphics g) {
        System.out.println("Drawing State Diagram");
        layoutAlgorithm.layout();
    }

    public void setLayoutAlgo(LayoutAlgorithm a) {
        layoutAlgorithm = a;
        a.setDiagram(this);
    }
}
