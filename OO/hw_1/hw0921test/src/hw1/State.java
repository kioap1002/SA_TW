package hw1;

public class State extends DiagramElement {
    public void draw(Graphics g) {
        /* Implement State specific behavior */
        System.out.println("State: drawing...");
    }

    public boolean intersect(Point p) {
        /* Implement State specific behavior */
        System.out.println("State: intersecting...");
        return false;
    }
}
