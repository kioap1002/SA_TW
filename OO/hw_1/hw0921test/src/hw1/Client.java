package hw1;


public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        DiagramElement stateDiagram = new StateDiagram();
        DiagramElement state1 = new State();
        stateDiagram.add(state1);
        
        DiagramElement state2 = new State();
        stateDiagram.add(state2);
        DiagramElement Transition1 = new Transition();
        stateDiagram.add(Transition1);
        DiagramElement Transition2 = new Transition();
        stateDiagram.add(Transition2);
        
        Graphics g = new Graphics();
        stateDiagram.draw(g);
        // DiagramElement e = stateDiagram.get(...);
        // stateDiagram.remove(e);
    }
}
