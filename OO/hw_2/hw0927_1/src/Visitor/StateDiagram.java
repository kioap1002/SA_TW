package Visitor;

import java.util.ArrayList;
import java.util.Iterator;

public class StateDiagram extends DiagramElement {
	ArrayList<DiagramElement> children = new ArrayList<DiagramElement>();
	
	public void add(DiagramElement d) {
		children.add(d);
	}
	
	public void accept(Checker c) {
		Iterator it = children.iterator();
		while (it.hasNext()) {
			((DiagramElement)it.next()).accept(c);
		}
		c.check(this);
	}
}