package hw1;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;


public class Transition extends DiagramElement {
    private Color color;
    private int x, y;
    
    public Transition() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        color = new Color(r, g, b);

        width = 25 + (int) (Math.random() * 40);
        height = 25 + (int) (Math.random() * 40);
        
        x = (int) (Math.random() * (350 - width*2));
        y = (int) (Math.random() * (350 - width*2));
        
        p = new Point(x, y);   
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(p.x, p.y, width, height);
    }
    public boolean intersect(DiagramElement e, DiagramElement e1) {    	
    	int dx = Math.abs(e.p.x - e1.p.x);
    	int dy = Math.abs(e.p.y - e1.p.y); 
        return (((e.width + e1.width)/2 >=dx) && ((e.height + e1.height)/2 >=dy)) ? true:false;
    	
    }
}

