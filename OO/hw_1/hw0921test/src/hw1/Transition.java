package hw1;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;


public class Transition extends DiagramElement {
    private Color color;
    private int x, y, width, height;
    Point p;
    public Transition() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        color = new Color(r, g, b);

        width = 50 + (int) (Math.random() * 40);
        height = 50 + (int) (Math.random() * 40);
        /*Random rand = new Random();
        x = rand.nextInt(300 - (int)(width/2))*/
        
        x = (int) (Math.random() * (300 - width));
        y = (int) (Math.random() * (300 - height));
        
        p = new Point(x, y);
        
    }

    public void draw(Graphics g) {
        /* Implement Transition specific behavior 
        System.out.println(g.name + "  Transition: drawing...");*/
        
        g.setColor(color);
        g.fillRect(p.x, p.y, width, height);
        //intersect(p);
    }

    public boolean intersect(Point p, int w, int h) {
        /* Implement Transition specific behavior */
        //System.out.println(p.name + "  Transition: intersecting...");
    	//self(this),跟外部(p,w,h)做比較?
        return false;
    }
}

