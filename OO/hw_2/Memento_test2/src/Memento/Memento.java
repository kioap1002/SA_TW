package Memento;

public class Memento {
	private State state;
	private int pass;
	public void setState(State s, String pas) {
		System.out.println("adding Memento...");
		this.state = s;
		System.out.println("now state is " + s.getContext());
		this.pass = pas.hashCode();
	}
	public State getState() {
		return this.state;
	}
	public State getState(String pass) throws IllegalAccessException{
		int h = pass.hashCode();
		if(this.pass == h) {
			return state;
		}else {
			throw new IllegalAccessException();
		}
	}
}
