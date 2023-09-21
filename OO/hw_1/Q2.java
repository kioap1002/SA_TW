
/**********************************************/
public class StateDiagram { //Context
    /*Date date;
    ... 
    public Date getContextDate(){
        return date;
    }*/
    LayoutAlgorithm layoutAlgorithm;
    public void draw(Graphics g){
        System.out.println("Drawing State Diagram");
        layoutAlgorithm.layout();
    }
    public void setLayoutAlgo(LayoutAlgorithm a){
        layoutAlgorithm = a;
        a.setDiagram(this);
    }
}
/**********************************************/
public abstract class LayoutAlgorithm { //Strategy
    /*Context context;
    public void setContext(Context c){
        context = c;
    }
    public abstract void operation();*/
    StateDiagram stateDiagram;
    public void setDiagram(StateDiagram d){
        stateDiagram = d;
    }
    public abstract void layout();
}
/**********************************************/
public class Force_BasedLayout extends LayoutAlgorithm{ //StrategyK123
    //public void operation(){}
    public void layout(){
        printlnln("Force-Based Layout");
    }
}
public class OrthogonalLayout extends LayoutAlgorithm{
    public void layout(){
        printlnln(" Orthogonal Layout");
    }
}
public class HierarchicalLayout extends LayoutAlgorithm{
    public void layout(){
        printlnln("Hierarchical Layout");
    }
}
/**********************************************/
public class EditDiagrmController { //Client
    /*Strategy strategy;
    public void use(){
        Context context = new Context();
        strategy = new StrategyK();
        strategy.setContext(Context);
        strategy.operation();
    }*/
    LayoutAlgorithm layoutAlgorithm;
    public main(String[] args){
        
        StateDiagram stateDiagram = new StateDiagram();
        layoutAlgorithm = new Force_BasedLayout();
        layoutAlgorithm.setDiagram(stateDiagram);
        layoutAlgorithm.layout();

    }
}