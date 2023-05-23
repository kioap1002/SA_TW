package main;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class intersectionsDB {  //一直更新，單位為秒
    private int count = 0;
    List <roadSituation> intersectionData_EW = new Arraylist<roadSituation>();
    List <roadSituation> intersectionData_NS = new Arraylist<roadSituation>();
    public void addIntersectionData(roadSituation RS_EW,roadSituation RS_NS){
        intersectionData_EW.add(RS_EW);
        intersectionData_NS.add(RS_NS);
        count++;
    }
    //計算今日平均密度
    public double calculateTodayDensityAverage(boolean laneDirection){  
        double TodaysDensityAverage;
        if(laneDirection){
            for(roadSituation rS : intersectionData_NS){
                TodaysDensityAverage += rS.density;
            }
        } else {
            for(roadSituation rS : intersectionData_EW){
                TodaysDensityAverage += rS.density;
            }
        }
        TodaysDensityAverage /= (double)count; 
        return TodaysDensityAverage;
    }
    //計算今日平均車數
    public double calculateTodayVehicleAmountAverage(boolean laneDirection){  
        double vehicleAmountAverage;
        if(laneDirection){
            for(roadSituation rS : intersectionData_NS){
                vehicleAmountAverage += (double)rS.vehicleAmount;
            }
        } else {
            for(roadSituation rS : intersectionData_EW){
                vehicleAmountAverage += (double)rS.vehicleAmount;
            }
        }
        vehicleAmountAverage /= (double)count;
        return vehicleAmountAverage;
    }
    //計算今日緊急次數
    public double calculateTodayEmergencyVehicleCount(boolean laneDirection){  
        int emergencyVehicleCount;
        if(laneDirection){
            for(roadSituation rS : intersectionData_NS){
                if(rS.emergencyVehicle) emergencyVehicleCount++;
            }
        } else {
            for(roadSituation rS : intersectionData_EW){
                if(rS.emergencyVehicle) emergencyVehicleCount++;
            }
        }
        return emergencyVehicleCount;
    }
}