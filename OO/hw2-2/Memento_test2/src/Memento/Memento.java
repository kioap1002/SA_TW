package Memento;

public class Memento {
	State state;
	
	public void setNowState(State s) {
		System.out.println("setting Memento state...");
		this.state = s;
		System.out.println("now state is " + s.text);
	}
	public State getState() {
		return this.state;
	}
}
