package Visitor;

public class Transition extends DiagramElement {
	public void accept(Checker c) {
		c.check(this);
	}
}