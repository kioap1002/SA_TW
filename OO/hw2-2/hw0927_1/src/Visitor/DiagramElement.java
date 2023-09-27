package Visitor;

public abstract class DiagramElement {
	public abstract void accept(Checker c);
	public void add(DiagramElement diagram) {
		throw new UnsupportedOperationException();
	}
}
