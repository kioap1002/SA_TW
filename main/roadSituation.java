package main;
import java.util.Date;

//單純用來放置資料的物件
public class roadSituation {
    private Date time;
    private boolean laneDirection;
    protected double vehicleAmount; //private
    protected boolean emergencyVehicle;  //private
    protected double density;  //private
    // private roadSituation_emergency RS_E;
    roadSituation(Date T, boolean LD, double VA, boolean EV, double den) {
        time = T;
        laneDirection = LD;
        vehicleAmount = VA;
        emergencyVehicle = EV;
        density = den;
    }
    public boolean getLaneDirection(){
        return laneDirection;
    }
    public double getVehicleAmount(){
        return vehicleAmount;
    }
    public boolean getEmergencyVehicle(){
        return emergencyVehicle;
    }
    public double getDensity(){
        return density;
    }
}