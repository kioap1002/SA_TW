package system;

public class judgeMode {
    judgeMode(){

    }
    if(road_sum.haveEmergency() != 0){
        mode = new emergencyMode(road_sum.haveEmergency());
    }else{
        // 0: both高 1: EW高 2:NS高, 3: NS or EW普通, 4: both低
        switch (road_sum.densityMode_col(Last30DaysDensity_EW, Last30DaysDensity_NS)) {
            case 0:
            case 1:
            case 2:
                mode = new HighDensityMode(road_sum.densityMode_col(Last30DaysDensity_EW, Last30DaysDensity_NS), camera_EW.RS.density, camera_NS.RS.density);
                //density 可以改成從資料庫拿
                break;
            case 3:
                mode = new BasicDensityMode();
                break;
            case 4:
                mode = new LowDensityMode(/*路權 */);
                break;
            default:
                // error
                break;
        }
    }
}
