package Memento;

public class StateDiagram {
	State state = new State();
	String pass;
	//Memento m;
	public void setState(String s) {
	//public void setState(State s) {
		//this.state = s;//更新的
		state.text = s;
	}
	public State getState() {
		return this.state;
	}
	public Memento createMemento() {
		Memento m = new Memento();
		m.setNowState(state);
		//m.setpass();
		return m;
	}
}
