import java.util.*;
import java.util.Iterator;

public abstract class DiagramElement {
    public void add(DiagramElement e) {
    }

    public DiagramElement get(...){
        return null;
    }

    public void remove(DiagramElement e) {
    }

    public abstract void draw(Graphics g);

    public abstract boolean intersect(Point p);
}

public class State extends DiagramElement {
    public void draw(Graphics g) {
        /* implment State specific behavior */
        System.out.println("State: drawing...");
    }

    public boolean intersect(Point p) {
        /* implment State specific behavior */
        System.out.println("State: intersecting...");
    }
}

public class Transition extends DiagramElement {
    public void draw(Graphics g) {
        /* implment Transition specific behavior */
        System.out.println("Transition: drawing...");
    }

    public boolean intersect(Point p) {
        /* implment Transition specific behavior */
        System.out.println("Transition: intersecting...");
    }
}

public class StateDiagram extends DiagramElement {
    ArrayList<DiagramElement> children = new ArrayList<DiagramElement>(); // 可論

    public void add(DiagramElement e) {
        /* add e to children */
        children.add(e); // auto
    }

    public DiagramElement get(...){
        /*get from children and return result */

    }

    public void remove(DiagramElement e) {
        /* remove e from children */
    }

    public void draw(Graphics g) {
        Iterator it = children.iterator();
        while (it.hasNext()) {
            DiagramElement e = (DiagramElement) it.next();
            e.draw(g);
        }
    }

    public boolean intersects(Point p) {
        /* implement StateDiagram specific behavior */
    }
}

public class Client {
    public void use(){
        DiagramElement stateDiagram = new StateDiagram();
        DiagramElement state1 = new State();
        stateDiagram.add(state1);
        DiagramElement state2 = new State();
        stateDiagram.add(state2);
        ...
        stateDiagram.draw(g);
        DiagramElement e = stateDiagram.get(...);
        stateDiagram.remove(e);
    }
}