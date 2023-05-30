package system;

public class calculation {
    private double laneLength;  //300m
    private double density;
//    private double densityDiffer;
    public double calculateVehicleDensity(double vehicleAmount){  //change the type of vA from int to double
        this.density =  (double)vehicleAmount / laneLength;
        return density;
    }
    // public double getCalculateDensityDifferenceValue(double averageDensity){
    //     this.densityDiffer = averageDensity - density;
    //     return densityDiffer;
    // }
}
