package main;

public class changedParameter{
    protected int greenLightTime_EW = 15;//20S R
    protected int yellowLightTime_EW = 3;
    protected int allRedLightTime_EW = 1;
    protected int greenLightTime_NS = 15;
    protected int yellowLightTime_NS = 3;
    protected int allRedLightTime_NS = 1;
    protected int condition;
    protected int flashing;

    //G8 Y2 R10
    //此G8 對R8
    //此Y2 對R2
    //此AR1 對AR1
    //此R8 對G6+Y2
    //此AR1 對AR1 
    //此G8 對R8
    //此G8 Y2 R10
    //對G6 Y2 R12
    changedParameter(int cd, int right){
        condition = cd;//0: 正常, 1: EW, 2: NS, 3: both
        flashing = right;//0: 正常, 1: EW, 2: NS
    }
    changedParameter(double rlt, double glt, double ylt){
        
    }
    public void setParameter(double rlt, double glt, double ylt, int fla){
        redLightTime = rlt;
        greenLightTime = glt;
        yellowLightTime = ylt;
        flashing = fla;
    }
    public int check(){
        //if(fla == 0 && redLightTime == greenLightTime && greenLightTime == yellowLightTime && yellowLightTime == redLightTime){
            return (int)redLightTime;
        //}
    }
    
}