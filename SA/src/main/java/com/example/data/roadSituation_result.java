package com.example.data;

public class roadSituation_result {
    protected int EV_result;
    protected int D_result;
    roadSituation_result(roadSituation road_sum, double Last30DaysDensity){
        EV_result = road_sum.haveEmergency();
        D_result = road_sum.densityMode_col(Last30DaysDensity);
    }
}