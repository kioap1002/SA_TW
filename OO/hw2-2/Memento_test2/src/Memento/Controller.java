package Memento;

import java.util.ArrayList;

public class Controller {
	public static void main(String[] args) {
		EditController ec = new EditController();
		//ec.sd.setState(new State());
		ec.beautify("first");
		System.out.println("--------------------------");
		
		ec.beautify("second");
		System.out.println("--------------------------");
		
		ec.beautify("third");
		System.out.println("--------------------------");
		
		System.out.println("below restore testing1...");
		ec.restoreMemento();
		
		System.out.println("--------------------------");
		System.out.println("add forth...");
		ec.beautify("forth");
		
		System.out.println("--------------------------");
		System.out.println("below restore testing2...");
		ec.restoreMemento();
		//System.out.println("Adding state0...");

	}
}
