package Memento;

import java.util.ArrayList;

public class EditController {
	ArrayList<Memento> collect = new ArrayList();
	StateDiagram sd = new StateDiagram();
	Memento m = new Memento();
	
	public void beautify(String s) {
		sd.pass = s;
		sd.setState(s);
		System.out.println(s + "...");
		saveMemento();
	}
	
	public void restoreMemento() {
		//sd.state = collect.get(collect.size()-1).state;  //回到上一步
		sd.state = collect.get(1).state;
		System.out.println(sd.state.text);
	}
	
	public void saveMemento() {
		m.state = sd.getState();
		collect.add(m);
	}
}
