package hw2;

public class EditDiagramController {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        LayoutAlgorithm layoutAlgorithm;

        StateDiagram stateDiagram = new StateDiagram();
        layoutAlgorithm = new Force_BasedLayout();
        layoutAlgorithm.setDiagram(stateDiagram);
        stateDiagram.setLayoutAlgo(layoutAlgorithm);
        Graphics g = new Graphics();

        stateDiagram.draw(g);
    }
}