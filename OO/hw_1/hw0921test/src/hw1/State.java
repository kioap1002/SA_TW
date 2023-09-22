package hw1;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;

public class State extends DiagramElement {
    private Color color;
    public int x, y;
    public State() {
        int r = (int) (Math.random() * 255);
        int g = (int) (Math.random() * 255);
        int b = (int) (Math.random() * 255);
        color = new Color(r, g, b);

        width = 35 + (int) (Math.random() * 40);
        height = 0;
        x = (int) (Math.random() * (350 - (int)(width*2)));
        y = (int) (Math.random() * (350 - (int)(width*2)));
        p = new Point(x,y);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, width);
    }

    @Override
    public boolean intersect(DiagramElement e, DiagramElement e1) {
        //判斷圖型是否交叉
    	double a = Math.pow(e.p.getX()-e1.p.getX(),2);
    	double b = Math.pow((e.p.getY()-e1.p.getY()),2);
    	double d = Math.sqrt(a+b);
    	return (e.width+e1.width)/2 >= d?true:false;
    	
    }
}
