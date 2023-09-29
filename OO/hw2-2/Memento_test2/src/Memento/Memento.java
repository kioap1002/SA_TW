package Memento;

public class Memento {
	State state;
	int pass;
	public void setNowState(State s, String pas) {
		System.out.println("setting Memento state...");
		this.state = s;
		System.out.println("now state is " + s.text);
		
		this.pass = pas.hashCode();
	}
	public State getState() {
		return this.state;
	}
	
	public State getState(String pass){
		int h = pass.hashCode();
		if(this.pass == h) {
			return state;
		}else {
			System.out.println("error in Memento.getState(pass)..");
		}
		return null;
	}
}
