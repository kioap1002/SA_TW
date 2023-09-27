public interface DiagramElement {
    public void accept(Checker c);
    // other operations
}
public class State implements DiagramElement {
    public void accept(Checker c) {
        c.check(this); // visit
    }
    // other operations
}
public class Transition implements DiagramElement {
    public void accept(Checker c) {
        c.check(this); // visit
    }
    // other operations
}
public class StateDiagram implements DiagramElement {
    Collection components;
    //...////////////////////////////////
    public void accept(Checker c) {
        Iterator it = components.iterator();
        while (it.hasNext()) {
            ((DiagramElement)it.next()).accept(c);
        }
        // other operations
        //...////////////////////////////////
    }
}
public class Checker{
    public void check(State s) {}
    public void check(Transition t) {}
}
public class StateChecker extends Checker {
    public void check(State s) {
        /*implement state */
    }
}
public class TransitionChecker extends Checker{
    public void check(Transition t) {
        /*implement transition */
    }
}
public class Controller{
    StateDiagram diagram;
    //...////////////////////////////////
    public void use(){
        Checker c = new StateChecker();
        diagram.accept(c);
    }
}