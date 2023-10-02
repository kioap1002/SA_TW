package Visitor;

public class State extends DiagramElement {
	public void accept(Checker c) {
		c.check(this);
	}
}
