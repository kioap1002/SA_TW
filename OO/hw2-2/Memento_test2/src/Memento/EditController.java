package Memento;

import java.util.ArrayList;

public class EditController {
	ArrayList<Memento> collect = new ArrayList();
	StateDiagram sd = new StateDiagram();
	Memento m;
	State state;
	public void beautify(String s) {
		sd.pass = s;
		state = new State();
		state.setContext(s);
		sd.setState(state);
		System.out.println(s + "...");
		saveMemento();
	}
	public void saveMemento() {
		//System.out.println("saving...adding...");
		m = new Memento();
		//m.state = sd.getState();
		m.setNowState(sd.getState());
		collect.add(m);
	}
	public void restoreMemento() {
		//sd.state = collect.get(collect.size()-1).state;  //回到上一步
		//sd.state = collect.get(1).state;
		//Memento m = collect.get(collect.size()-1);
		Memento m = collect.get(1);
		sd.setState(m.getState());
		System.out.println(m.getState().text);
	}
	

}
