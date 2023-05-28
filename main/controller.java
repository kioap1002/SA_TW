package main;

import javafx.print.PrintColor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import java.time.*;
public class controller {  //有手動跟自動的模式，loop控制更新資料庫 & 更改模板
    private double Last30DaysDensity_EW;
    private double Last30DaysDensity_NS;
    // private double densityDifferenceValue_EW;//高密度mode計算綠燈增加秒數用
    // private double densityDifferenceValue_NS;//高密度mode計算綠燈增加秒數用
    private int mode_EW; //密度模板 0: 低 1:正常 2:高
    private int mode_NS; //密度模板 0: 低 1:正常 2:高
    private int adjustmentResult;    //目前沒用
    private double timer; //持續時間 //目前沒用

    private LocalTime time = LocalTime.now();  //控制時間
    private LocalTime timeNow = LocalTime.now();  //控制時間(變動ver)
    private int today = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
    private int tomarrow = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);

    private intersectionsDB iDb = new intersectionsDB();
    private intersectionsDB_day iDb_d = new intersectionsDB_day();
    private east_westDetectCamera camera_EW;
    private north_southDetectCamera camera_NS;
    private trafficLight tL;  //用來傳我們要更改的Mode進去  //parameter
    private roadSituation_sum road_sum;

    private Mode mode;
    private physicalTrafficSignal pTS = new physicalTrafficSignal();
    
    //需要有地方取得路口資訊
    // pTS.EW_side_Passable_g();
    while(true){
        tomarrow = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        if(tomarrow - today == 1){
            //換日，處理今日資料
            LocalDate day = LocalDate.now();
            //LD=> 0:EW, 1:NS
            roadSituation todayRS_EW = new roadSituation(day, false, iDb.calculateTodayVehicleAmountAverage(false), iDb.calculateTodayEmergencyVehicleCount(false), iDb.calculateTodayDensityAverage(false));
            roadSituation todayRS_NS = new roadSituation(day, true, iDb.calculateTodayVehicleAmountAverage(true), iDb.calculateTodayEmergencyVehicleCount(true), iDb.calculateTodayDensityAverage(true));
            iDb_d.addIntersectionData(todayRS_EW, todayRS_NS);
            // 清空資料庫，重製時間
            iDb = new intersectionsDB();
            //iDb.addIntersectionData(todayRS_EW, todayRS_NS); //資料庫清空不需要預設資料
            today = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
            tomarrow = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        }

        timeNow = LocalTime.now();
        if(time + 5 <= timeNow){
            camera_EW.shootIntersections();
            camera_NS.shootIntersections();
            /*每五秒拍攝一次路口，更新秒資料庫 */
            /*紅燈燈號結束前五秒 || 閃燈狀態下(10秒判斷一次)，從秒資料庫獲取最新一筆資料來判斷模板的變更與否 */
            road_sum = new roadSituation_sum(camera_EW.RS.emergencyVehicle, camera_NS.RS.emergencyVehicle, camera_EW.RS.density, camera_NS.RS.density);
            iDb.addIntersectionData(camera_EW.RS,camera_NS.RS);

            time = LocalTime.now();
            timeNow = LocalTime.now();

            Last30DaysDensity_EW = iDb_d.calculateTheLast30DaysDensityAverage(false);
            Last30DaysDensity_NS = iDb_d.calculateTheLast30DaysDensityAverage(true);
            
            if(road_sum.haveEmergency() != 0){
                mode = new emergencyMode(road_sum.haveEmergency());
            }else{  
                // 0: both高 1: EW高 2:NS高, 3: NS or EW普通, 4: both低
                switch (road_sum.densityMode_col(Last30DaysDensity_EW, Last30DaysDensity_NS)) {
                    case 0:
                    case 1:
                    case 2:
                        mode = new HighDensityMode(road_sum.densityMode_col(Last30DaysDensity_EW, Last30DaysDensity_NS), camera_EW.RS.density, camera_NS.RS.density);
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
            tL = new trafficLight(mode.changeMode());
            pTS.setcP(mode.changeMode());
        }

    }

}
