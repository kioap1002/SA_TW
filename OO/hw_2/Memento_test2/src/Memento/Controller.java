package Memento;

public class Controller {
	public static void main(String[] args) {
		EditController ec = new EditController();
		ec.beautify("color: white");
		System.out.println("--------------------------");
		
		ec.beautify("color: pink");
		System.out.println("--------------------------");
		
		ec.beautify("color: purple");
		System.out.println("--------------------------");
		
		System.out.println("restore to state0");
		ec.restore(0);
		
		System.out.println("--------------------------");
		ec.beautify("color: yellow");
		
		System.out.println("--------------------------");
		System.out.println("restore to state2");
		ec.restore(2);
		
		System.out.println("--------------------------");
		ec.beautify("color: black");
	}
}
