package Memento;

import java.util.ArrayList;

public class Controller {
	public static void main(String[] args) {
		EditController ec = new EditController();
		//ec.sd.setState(new State());
		ec.beautify("add");
		ec.beautify("second");
		ec.beautify("third");
		ec.restoreMemento();
		ec.restoreMemento();
		//System.out.println("Adding state0...");

	}
}
