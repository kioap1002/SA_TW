public class EditDiagrmController {
    LayoutAlgorithm layoutAlgorithm;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        StateDiagram stateDiagram = new StateDiagram();
        layoutAlgorithm = new Force_BasedLayout();
        layoutAlgorithm.setDiagram(stateDiagram);
        stateDiagram.draw("");
    }
}
