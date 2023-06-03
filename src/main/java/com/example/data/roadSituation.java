package com.example.data;

import java.time.LocalDate;
import java.util.Date;

//Data Coupling
//單純用來放置資料的物件
public class roadSituation {
    private Date time;
    private LocalDate time_day;
    private boolean laneDirection;
    protected double vehicleAmount; // private
    protected double vehicleAmount_aver;
    protected boolean emergencyVehicle; // private
    protected double emergencyVehicle_count;
    protected double density; // private
    protected double density_aver; // private
    // private roadSituation_emergency RS_E;

    roadSituation(Date T, boolean LD, double VA, boolean EV, double den) {
        time = T;
        laneDirection = LD;
        vehicleAmount = VA;
        emergencyVehicle = EV;
        density = den;
    }

    roadSituation(LocalDate T, boolean LD, double VA_aver, double EV_n, double den_aver) {
        time_day = T;
        laneDirection = LD;
        vehicleAmount_aver = VA_aver;
        emergencyVehicle_count = EV_n;
        density_aver = den_aver;
    }

    public boolean getLaneDirection() {
        return laneDirection;
    }

    public double getVehicleAmount() {
        return vehicleAmount;
    }

    public boolean getEmergencyVehicle() {
        return emergencyVehicle;
    }

    public double getDensity() {
        return density;
    }
}