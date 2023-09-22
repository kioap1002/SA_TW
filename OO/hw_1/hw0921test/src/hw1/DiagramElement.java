package hw1;

import java.awt.Graphics;
import java.awt.Point;

public abstract class DiagramElement {
	public Point p;
	public int width,height;
	public boolean inter = false;
    public void add(DiagramElement e) {
    	throw new UnsupportedOperationException();
    }
    public DiagramElement get(int i) {
        return null;
    }
    public void remove(DiagramElement e) {
    	throw new UnsupportedOperationException();
    }
    public boolean getIt() {
    	return this.inter;
    }
    public abstract void draw(Graphics g);

    public abstract boolean intersect(DiagramElement e, DiagramElement e1);
    
}