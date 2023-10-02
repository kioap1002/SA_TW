package Visitor;

public class Controller {
	public static void main(String[] args) throws Exception {
		Checker s = new SyntaxChecker();
		Checker r = new RelationChecker();
		
		DiagramElement stateDiagram = new StateDiagram();  //composite
		//State
		DiagramElement diagram = new State();
		diagram.accept(s);
		diagram.accept(r);
		stateDiagram.add(diagram);
		System.out.println("--------------------------");
		
		//Transition
		diagram = new Transition();
		diagram.accept(s);
		diagram.accept(r);
		stateDiagram.add(diagram);
		System.out.println("--------------------------");
		
		//StateDiagram
		diagram = new StateDiagram();
		diagram.accept(s);
		diagram.accept(r);
		
		System.out.println("--------------------------");
		
		stateDiagram.accept(s);
		stateDiagram.accept(r);
	}

}
