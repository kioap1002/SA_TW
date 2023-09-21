package hw1;

import java.util.*;
import java.util.Iterator;

public class StateDiagram extends DiagramElement {
    ArrayList<DiagramElement> children = new ArrayList<DiagramElement>(); // 可論
    public static int index = -1;

    public void add(DiagramElement e) {
        index++;
        /* add e to children */
        children.add(e); // auto
        System.out.println("adding DiagramElement...StateDiagram  " + index);
    }

    public DiagramElement get(int i) {
        /* get from children and return result */
        return children.get(index);
    }

    public void remove(DiagramElement e) {
        /* remove e from children */
        index--;
        System.out.println("removing DiagramElement...StateDiagram");
    }

    public void draw(Graphics g) {
        Iterator it = children.iterator();
        while (it.hasNext()) {
            DiagramElement e = (DiagramElement) it.next();
            e.draw(g);
        }
    }

    public boolean intersect(Point p) {
        /* implement StateDiagram specific behavior */
        System.out.println(p.name + "  intersecting DiagramElement...StateDiagram");
        return false;
    }
}
