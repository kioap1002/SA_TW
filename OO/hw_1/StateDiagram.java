import java.util.*;
import java.util.Iterator;
package DiagramElement;
//Composite
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