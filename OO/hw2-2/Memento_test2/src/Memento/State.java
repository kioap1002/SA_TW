package Memento;

public class State {
	String text = "beginning";
	State(){
		//System.out.println("creating State...");
		//出現了兩次，晚點偵錯
	}
	public void setContext(String s) {
		//System.out.println("setting Context...");
		this.text = s;
	}
	public String getContext(String s) {
		//System.out.println("getting Context...");
		return text;
	}
}
