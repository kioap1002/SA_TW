package Visitor;

public class RelationChecker implements Checker{
	public void check(State s) {
		System.out.println("RelationChecker checking State...");
	}
	public void check(Transition t) {
		System.out.println("RelationChecker checking Transition...");
	}
	public void check(StateDiagram sd) {
		System.out.println("RelationChecker checking StateDiagram...");
	}
}