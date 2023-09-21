import java.util.*;
import java.util.Iterator;

// Component
public abstract class DiagramElement {
    public void add(DiagramElement e) {

    }
    public DiagramElement get(int i){
        return null;
    }
    public void remove(DiagramElement e) {
    }
    public abstract void draw(Graphics g);
    public abstract boolean intersect(Point p);
    /*... */
}
// left
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

