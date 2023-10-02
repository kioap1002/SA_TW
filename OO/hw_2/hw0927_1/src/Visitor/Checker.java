package Visitor;

public interface Checker{
	public void check(State s);
	public void check(Transition t);
	public void check(StateDiagram sd);
}
