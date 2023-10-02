package Memento;

public class State {  //Diagram State
	private String text = "default";
	public void setContext(String s) {
		this.text = s;
	}
	public String getContext() {
		return text;
	}
	public State clone() {
		return this;
	}
}
