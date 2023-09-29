package Memento;

public class Controller {
	public static void main(String[] args) {
		EditController ec = new EditController();
		ec.beautify("first");
		System.out.println("--------------------------");
		
		ec.beautify("second");
		System.out.println("--------------------------");
		
		ec.beautify("third");
		System.out.println("--------------------------");
		
		System.out.println("below restore testing1... => first");
		ec.restoreMemento(0);
		
		System.out.println("--------------------------");
		System.out.println("add forth...");
		ec.beautify("forth");
		
		System.out.println("--------------------------");
		System.out.println("below restore testing2... => third");
		ec.restoreMemento(2);
	}
}
