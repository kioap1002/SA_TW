package Memento;

public class StateDiagram {
	private String pass;
	private State state;
	StateDiagram(String s){
		this.pass = s;
	}
	public Memento createMemento() {
		Memento m = new Memento();
		//State s = (DiagramState)state.clone();
		//State s = (State)state.clone();
		State s_c = state;
		state.setState("test");
		m.setState(s_c, pass);
		return m;
	}
	public Memento setMemento(Memento memento) {
		try {
			state = memento.getState(pass);
		}catch (IllegalAccessException e) {}
		return memento;
	}
}
