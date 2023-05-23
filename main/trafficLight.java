package main;

public class trafficLight{
    private double greenLightTime_EW;//east_west_greenLight
    private double redLightTime_NS;//north_south_greenLight  
    private double yellowLightTime;
    private Mode trafficLightMode;// = new Mode();
    private changedParameter cP;


    trafficLight(changedParameter cP){
        this.cP = cP;//計算/調整完的時間
        //chandeMode();
    }
    trafficLight(double GT, double RT, double YT){
        this.greenLightTime_EW = GT;
        this.redLightTime_NS = RT;
        this.yellowLightTime = YT;
    }
    public void applyParameter(){

    }
}
