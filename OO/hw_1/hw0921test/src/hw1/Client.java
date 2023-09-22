package hw1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Client {

    public static void main(String[] args) throws Exception {

        JFrame frame = new JFrame("Diagram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        //boolean result;
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                DiagramElement stateDiagram = new StateDiagram();
                
                DiagramElement state1 = new State();
                stateDiagram.add(state1);
                DiagramElement state2 = new State();
                stateDiagram.add(state2);
                
                DiagramElement Transition1 = new Transition();
                stateDiagram.add(Transition1);

                
                stateDiagram.draw(g);
                // DiagramElement e = stateDiagram.get(...);
                // stateDiagram.remove(e);
                
                new Client(); //text test
                


            }
        };

        //JLabel label1 = new JLabel(stateDiagram.intersect());
    	
        
    	JLabel label1 = new JLabel("標籤1");
    	label1.setBounds(30,20,100,40);
    	frame.add(label1);
    	
        frame.add(panel);
        
        frame.setVisible(true);
        
    }
}
