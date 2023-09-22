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
        children.add(e);
        System.out.println("adding DiagramElement...  " + num);
    }

    public DiagramElement get(int i) {
        return children.get(i);
    }

    public void remove(DiagramElement e) {
    	num--;
        children.remove(e);
    }
    

    public void draw(Graphics g) {
    	Iterator<DiagramElement> it = children.iterator();
        
        DiagramElement s1 = it.next();  //head
        DiagramElement prev = s1;  //previous
        DiagramElement e = s1;  //now
        
        e.draw(g);
        while (it.hasNext()) {
        	e.draw(g);
            e = (DiagramElement) it.next();
            if (!inter) {
            	inter = intersect(prev, e);
            }
        	prev = e;
        }
        //System.out.println(count + "diagram.");
        e.draw(g);
        if (!inter) {
        	inter = intersect(s1, e);
        }
		
        
    }
    public boolean intersect(DiagramElement e, DiagramElement e1) {
    	if(e.height != 0 && e1.height != 0) {
    		//squared    	
        	boolean test = false;
        	test = e.intersect(e,e1);
    		return test;
    	
    	}else if (e.height == 0 && e1.height == 0) {
    		//circle
        	boolean test = false;
        	test = e.intersect(e,e1);
    		return test; 
    	}else {
    		//不同圖案的比較在這裡實作
    		boolean test = false;
    		double dx = Math.abs(e1.p.x-e.p.x);
    		double dy = Math.abs(e1.p.y-e.p.y);
    		double dist = 0.0;
    		if(e.height == 0) {
    			//e是圓形, e1矩形
    			if(dx > e1.width/2) {
    				//兩點x距離小於圓半徑
    				dist = Math.sqrt(Math.pow(dx - e1.width/2,2) + Math.pow(dy, 2));
    			}else if(dy > e1.height/2){
    				dist = Math.sqrt(Math.pow(dy - e1.height/2,2) + Math.pow(dx, 2));
    			}else {
    				dist = Math.max(dx,dy);
    			}
    				
    			return dist <= e.width/2  ? true: false;
    		} else {
    			//e1是圓形, e矩形
    			if(dx > e.width/2) {
    				//兩點x距離小於圓半徑
    				dist = Math.sqrt(Math.pow(dx - e.width/2,2) + Math.pow(dy, 2));
    			}else if(dy > e1.height/2){
    				dist = Math.sqrt(Math.pow(dy - e.height/2,2) + Math.pow(dx, 2));
        		}else {
    				dist = Math.max(dx,dy);
    			}
    			return dist <= e1.width/2  ? true: false;
    		}
    	}
    }
}
