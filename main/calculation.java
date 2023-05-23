package main;

public class calculation {
    private double laneLength;  //300m
    private double density;
    private double densityDiffer;
    public double calculateVehicleDensity(int vehicleAmount){
        this.density =  (double)vehicleAmount / laneLength;
        return density;
    }
    // public double getCalculateDensityDifferenceValue(double averageDensity){
    //     this.densityDiffer = averageDensity - density;
    //     return densityDiffer;
    // }
}
