package hw2;


public class EditDiagramController {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, World!");
        
        LayoutAlgorithm layoutAlgorithm;
        StateDiagram stateDiagram = new StateDiagram();
        
        layoutAlgorithm = new Force_BasedLayout();
        stateDiagram.setLayoutAlgo(layoutAlgorithm);
        System.out.println("setting Force_BasedLayout ...");
        layoutAlgorithm = new OrthogonalLayout();
        stateDiagram.setLayoutAlgo(layoutAlgorithm);
        System.out.println("setting OrthogonalLayout ...");
        layoutAlgorithm = new HierarchicalLayout();
        stateDiagram.setLayoutAlgo(layoutAlgorithm);
        System.out.println("setting HierarchicalLayout ...");
        
        Graphics g = new Graphics();
        stateDiagram.draw(g);
    }
}
