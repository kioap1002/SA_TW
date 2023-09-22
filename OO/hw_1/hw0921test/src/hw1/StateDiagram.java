package hw1;

import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
import java.util.Iterator;

public class StateDiagram extends DiagramElement {
    ArrayList<DiagramElement> children = new ArrayList<DiagramElement>();
    public static int num = 0;

    public void add(DiagramElement e) {
    	num++;
        /* add e to children */
        children.add(e); // auto
        System.out.println("adding DiagramElement...  " + num);
    }

    public DiagramElement get(int i) {
        /* get from children and return result */
        return children.get(num);
    }

    public void remove(DiagramElement e) {
        /* remove e from children */
    	num--;
        System.out.println("removing DiagramElement...StateDiagram");
        children.remove(e);
    }
    
    public boolean inter = false;//預設無相交
    public void draw(Graphics g) {
    	int count = 0;
        Iterator<DiagramElement> it = children.iterator();
        while (it.hasNext()) {
        	//inter = intersect(it.next().p);
        	inter = false? inter = intersect(it.next().p,it.next().width,it.next().height):inter == true;
            DiagramElement e = (DiagramElement) it.next();
            e.draw(g);
            count++;
        }
        System.out.println(count + "diagram.");
    }

    public boolean intersect(Point p, int width, int height) {
        /* implement StateDiagram specific behavior 
        System.out.println(p.name + "  intersecting DiagramElement...StateDiagram");*/
    	if(this.height == 0 && height != 0 || this.height != 0 && height==0) {
    		//不同圖案的比較在這裡實作
    		
    	}else if (this.height == 0 && height == 0) {
    		//circle
    		return intersect(this.p, this.width, width); //need to check
    	}else {
    		//squared
    		
    	}
        return false;
    }

	/*@Override
	public boolean intersect(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersect(Point p, Point p1, int w1, int w2) {
		// TODO Auto-generated method stub
		return false;
	}*/
}
