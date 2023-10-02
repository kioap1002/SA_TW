package Memento;

public class StateDiagram {
	private State state = new State();
	String pass = "null";
	private Memento m;
	public void setSDState(State s) {
		this.state = s;
		this.pass = state.getContext();
	}
	public State getSDState() {
		return state;
	}
	public Memento createMemento() {
		m = new Memento();
		State clone = state.clone();
		m.setState(clone, pass);
		return m;
	}
	public void setMemento(Memento memento) {
		try {
			state = memento.getState(memento.getState().getContext());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
