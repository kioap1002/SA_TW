package Memento;

public class StateDiagram {
	private State state = new State();
	String pass = "null";
	Memento m;
	public void setSDState(State s) {
		this.state = s;//更新的
		this.pass = state.getContext();
	}
	
	public State getSDState() {
		return state;
	}
	
	public Memento createMemento() {
		m = new Memento();
		State clone = state.clone();
		m.setNowState(clone, pass);
		return m;
	}
	
	public void setMemento(Memento memento) {
		//System.out.println(memento.state.text + " sd. set M  test");
		try {
			state = memento.getState(memento.state.getContext());
			//state = memento.getState(pass);
		} catch (Exception e) {
		/*}catch (IllegalAccessException e) {*/
			System.out.println(e);
		}
	}
}
