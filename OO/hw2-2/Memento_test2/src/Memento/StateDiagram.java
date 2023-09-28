package Memento;

public class StateDiagram {
	State state = new State();
	String pass = "null";
	Memento m;
	public void setState(State s) {
	//public void setState(State s) {
		this.state = s;//更新的
		//state.setContext(s.text);
	}
	public State getState() {
		return this.state;
	}
	public Memento createMemento() {
		m = new Memento();
		m.setNowState(state);
		//m.setpass();
		return m;
	}
}
