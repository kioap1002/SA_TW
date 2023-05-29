package main;

public class changedParameter{
    protected double redLightTime;
    protected double greenLightTime;
    protected double yellowLightTime;
    protected int flashing;

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