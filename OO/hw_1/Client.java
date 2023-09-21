import java.util.*;
import java.util.Iterator;
package DiagramElement;
//TransparencyMain
public class Client {
    public void use(){
        DiagramElement stateDiagram = new StateDiagram();
        DiagramElement state1 = new State();
        stateDiagram.add(state1);
        DiagramElement state2 = new State();
        stateDiagram.add(state2);
        DiagramElement Transition1 = new State();
        stateDiagram.add(Transition1);
        DiagramElement Transition2 = new State();
        stateDiagram.add(Transition2);
        stateDiagram.draw(g);
        DiagramElement e = stateDiagram.get(...);
        stateDiagram.remove(e);
    }
}
