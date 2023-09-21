package hw1;

public class Transition extends DiagramElement {
    public void draw(Graphics g) {
        /* implment Transition specific behavior */
        System.out.println(g.name + "  Transition: drawing...");
    }

    public boolean intersect(Point p) {
        /* implment Transition specific behavior */
        System.out.println(p.name + "  Transition: intersecting...");
        return false;
    }
}
