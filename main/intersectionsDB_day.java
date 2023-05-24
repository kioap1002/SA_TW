package main;
import javafx.print.PrintColor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;



public class intersectionsDB_day {
    private double density;
    List <roadSituation> intersectionData_EW = new ArrayList<roadSituation>();
    List <roadSituation> intersectionData_NS = new ArrayList<roadSituation>();
    List <roadSituation> intersectionData = new ArrayList<roadSituation>();
    public void addIntersectionData(roadSituation RS_EW,roadSituation RS_NS){  
        intersectionData_EW.add(RS_EW);
        intersectionData_NS.add(RS_NS);
    }

    public double calculateTheLast30DaysDensityAverage(boolean laneDirection){  //density
        double average_30 = 0.0;
        if(laneDirection){
            intersectionData = intersectionData_NS;
        }else{
            intersectionData = intersectionData_EW;
        }
        List<String> TheLast30Days = intersectionData.stream().skip(Math.max(0, intersectionData.size() - 30)).collect(Collectors.toList());
        for (roadSituation RS_I : TheLast30Days){
            average_30 += RS_I.density;
        }
        return average_30 / 30.0;
    }
}
