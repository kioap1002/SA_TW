package hw1;

public abstract class DiagramElement {
    public void add(DiagramElement e) {
        System.out.println("adding DiagramElement...");
    }

    public DiagramElement get(int i) {
        return null;
    }

    public void remove(DiagramElement e) {
        // ArrayList.remove(e);
        System.out.println("removing DiagramElement...");
    }

    public abstract void draw(Graphics g);

    public abstract boolean intersect(Point p);
    /* ... */
}