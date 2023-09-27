package Visitor;

public class SyntaxChecker implements Checker {
	public void check(State s) {
		System.out.println("SyntaxChecker checking State...");
	}
	public void check(Transition t) {
		System.out.println("SyntaxChecker checking Transition...");
	}
	public void check(StateDiagram sd) {
		System.out.println("SyntaxChecker checking StateDiagram...");
	}
}