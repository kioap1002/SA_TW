package hw1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Client {
	private static JLabel label1;
    public static void main(String[] args) throws Exception {
    	SwingUtilities.invokeLater(() -> {
	        JFrame frame = new JFrame("Diagram");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(350, 350);
	
	        DiagramElement stateDiagram = new StateDiagram();
	        DiagramElement state1 = new State();
	        DiagramElement state2 = new State();
	        DiagramElement transition2 = new Transition();
	
	        stateDiagram.add(state1);
	        stateDiagram.add(state2);
	        stateDiagram.add(transition2);
	
	        JPanel panel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	
	                stateDiagram.draw(g);
	                
	                DiagramElement e = stateDiagram.get(1);
	                System.out.println("getting DiagramElement...");
	                stateDiagram.remove(e);
	                System.out.println("removing DiagramElement...");
	                
	
	                label1.setText("intersect: " + stateDiagram.getIt());
	            }
	        };
	    	label1 = new JLabel("intersect: " + stateDiagram.getIt());        
	    	label1.setBounds(30,20,100,40);
	    	frame.add(label1, BorderLayout.NORTH);
	        frame.add(panel);
	        frame.setVisible(true);
	        
	    });
    }
}

