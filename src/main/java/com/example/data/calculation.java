package com.example.data;

public class calculation {
    private double laneLength = 300;
    private double density;
    // private double densityDiffer;
    public double calculateVehicleDensity(double vehicleAmount){  //change the type of vA from int to double
        this.density =  (double)vehicleAmount / laneLength;
        return density;
    }
}