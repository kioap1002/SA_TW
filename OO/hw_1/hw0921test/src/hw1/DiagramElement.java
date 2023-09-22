package hw1;

import java.awt.Graphics;
import java.awt.Point;

public abstract class DiagramElement {
	Point p;
	int width,height;
    public void add(DiagramElement e) {
    	throw new UnsupportedOperationException();
    }

    public DiagramElement get(int i) {
        return null;
    }

    public void remove(DiagramElement e) {
    	throw new UnsupportedOperationException();
    }

    public abstract void draw(Graphics g);

    /*public abstract boolean intersect(Point p);
    public abstract boolean intersect(Point p, Point p1, int w1, int w2);*/
    public abstract boolean intersect(Point p, int w, int h);
    
}