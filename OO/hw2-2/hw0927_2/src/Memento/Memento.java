package Memento;

public class Memento {
	private int pass;
	private State state;
	//public void setState(DiagramState s, String pass) {
	public void setState(State s, String pass) {

		state = s;
		this.pass = pass.hashCode();
	}
	//public DiagramState getState(int pass) {
	public State getState(String pass) throws IllegalAccessException {
		int h = pass.hashCode();
		if(this.pass == h) {
			return state;
		}else {
			//error
			throw new IllegalAccessException();
		}
	}
}
