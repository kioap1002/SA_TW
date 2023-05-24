package main;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class intersectionsDB {  //一直更新，單位為秒
    List <roadSituation> intersectionData_EW = new ArrayList<roadSituation>();
    List <roadSituation> intersectionData_NS = new ArrayList<roadSituation>();
    List <roadSituation> intersectionData = new ArrayList<roadSituation>();
    public void addIntersectionData(roadSituation RS_EW,roadSituation RS_NS){
        intersectionData_EW.add(RS_EW);
        intersectionData_NS.add(RS_NS);
    }
    //計算今日平均密度
    public double calculateTodayDensityAverage(boolean laneDirection){  
        double DensityAverage;
        double count = 0.0;
        EWorNS(laneDirection);
        for(roadSituation rS : intersectionData){
            DensityAverage += rS.density;
            count++;
        }
        return DensityAverage / count;
    }
    //計算今日平均車數
    public double calculateTodayVehicleAmountAverage(boolean laneDirection){  
        double vehicleAmountAverage;
        double count = 0.0;
        EWorNS(laneDirection);
        for(roadSituation rS : intersectionData){
            vehicleAmountAverage += (double)rS.vehicleAmount;
            count++;
        }
        return vehicleAmountAverage / count;
    }
    //計算今日緊急次數
    public double calculateTodayEmergencyVehicleCount(boolean laneDirection){  
        int emergencyVehicleCount;
        EWorNS(laneDirection);
        for(roadSituation rS : intersectionData){
            if(rS.emergencyVehicle) emergencyVehicleCount++;
        }
        return emergencyVehicleCount;
    }
    //判斷該用 東西 還是 南北 資料庫，縮短程式碼用
    public void EWorNS(boolean laneDirection){
        if(laneDirection){
            intersectionData = intersectionData_NS;
        }else{
            intersectionData = intersectionData_EW;
        }
    }
}