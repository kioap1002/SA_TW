package main;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class intersectionsDB {  //一直更新，單位為秒
    private double density;
    List <roadSituation> intersectionData_EW = new Arraylist<roadSituation>();
    List <roadSituation> intersectionData_NS = new Arraylist<roadSituation>();
    public void addIntersectionData(roadSituation RS_EW,roadSituation RS_NS){
        intersectionData_EW.add(RS_EW);
        intersectionData_NS.add(RS_NS);
    }
    //計算今日平均密度
    public double calculateTodayDensityAverage(boolean laneDirection){  
        double TodaysDensityAverage;
        double count=0.0;
        for(roadSituation rS : intersectionData_EW){
            TodaysDensityAverage += rS.density;
            count += 1.0;
        }
        TodaysDensityAverage /=count; 
        return TodaysDensityAverage;
    }
    //計算今日平均車數
    public double calculateTodayVehicleAmountAverage(boolean laneDirection){  
        double vehicleAmountAverage;
        
        return vehicleAmountAverage;
    }
    //計算今日緊急次數
    public double calculateTodayEmergencyVehicleCount(boolean laneDirection){  
        double emergencyVehicleCount;
        
        return emergencyVehicleCount;
    }

}