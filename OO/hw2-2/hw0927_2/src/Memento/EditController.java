package Memento;

import java.util.ArrayList;

public class EditController {
	//這裡是main??
	public static void main(String[] args) throws Exception {
		final ArrayList<Memento> collect = new ArrayList<Memento>();  //儲存memento的arraylist
		
		StateDiagram sd = new StateDiagram("state0");
		collect.add(sd.createMemento());
		
		StateDiagram sd1 = new StateDiagram("state1");
		collect.add(sd1.createMemento());
	
		StateDiagram sd2 = new StateDiagram("state2");
		collect.add(sd2.createMemento());
		
		
		/*
		public void beautify() {
			//strategy
		}
		public void restore() {
			
		}*/
	}
}
