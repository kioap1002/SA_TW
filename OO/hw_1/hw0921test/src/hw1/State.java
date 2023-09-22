package hw1;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;

public class State extends DiagramElement {
    /*public void draw(Graphics g) {
         //Implement State specific behavior 
        System.out.println("State: drawing...");
    }*/

    /*public boolean intersect(Point p) {
         //Implement State specific behavior 
        System.out.println("State: intersecting...");
        return false;
    }*/
    private Color color;
    private int x, y, width;
    Point p;
    public State() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        color = new Color(r, g, b);

        width = 50 + (int) (Math.random() * 40);
        x = (int) (Math.random() * (300 - width));
        y = (int) (Math.random() * (300 - width));
        
        p = new Point(x,y);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, width);
    }

    @Override
    public boolean intersect(Point p1, int w, int h) {
    	
        //判斷圖型是否交叉
    	//circle1: x1,y1, width(z1)
    	//circle2: x2,y2, width(z2)
    	double d = Math.pow((Math.pow((p1.x-this.p.x),2)+Math.pow((p1.y-this.p.y),2)),0.5);
    	return (this.width+w)/2 <= d?true:false;
    }
}
