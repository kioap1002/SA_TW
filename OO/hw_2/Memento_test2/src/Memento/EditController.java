package Memento;

import java.util.ArrayList;

public class EditController {
	ArrayList<Memento> collect = new ArrayList();
	StateDiagram sd = new StateDiagram();
	State state;
	public void beautify(String s) {
		sd.pass = s;
		state = new State();
		state.setContext(s);
		sd.setSDState(state);
		System.out.println(s);
		Memento m = sd.createMemento();
		collect.add(m);
	}
	public void restore(int i) {
		Memento m = collect.get(i);
		sd.setMemento(m);
		System.out.println(sd.getSDState().getContext());
	}
}
