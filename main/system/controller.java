package system;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import java.time.*;

// import jdbc_test.*;

public class controller {  //有手動跟自動的模式，loop控制更新資料庫 & 更改模板
    private double Last30DaysDensity_EW;
    private double Last30DaysDensity_NS;
    // private double densityDifferenceValue_EW;//高密度mode計算綠燈增加秒數用
    // private double densityDifferenceValue_NS;//高密度mode計算綠燈增加秒數用
    private int mode_EW; //密度模板 0: 低 1:正常 2:高
    private int mode_NS; //密度模板 0: 低 1:正常 2:高
    private int adjustmentResult;    //目前沒用
    private double timer; //持續時間 //目前沒用
    LocalDate day = LocalDate.now();

    private int time = (int)System.currentTimeMillis() / 1000;  //控制時間
    private int timeNow = (int)System.currentTimeMillis() / 1000;  //控制時間(變動ver)
    private int today = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
    private int tomarrow = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);

    private int modeTime = (int)System.currentTimeMillis() / 1000;
    private int modeTimeNow = (int)System.currentTimeMillis() / 1000;

    private intersectionsDB iDb = new intersectionsDB();
    private intersectionsDB_day iDb_d = new intersectionsDB_day();
    private east_westDetectCamera camera_EW;
    private north_southDetectCamera camera_NS;
    //private trafficLight tL;  //用來傳我們要更改的Mode進去  //parameter//沒用ㄉ東西
    private roadSituation_sum road_sum;

    // private Mode mode;
    private changedParameter cP;
    private physicalTrafficSignal pTS = new physicalTrafficSignal();//用來傳我們要更改的Mode進去  //parameter
    
    // private jdbc_test jdbc;
    // private String intersectionID = jdbc.getInterID("intersection");
    //需要有地方取得路口資訊
    
    while(true){
        try {
            tomarrow = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
            if(tomarrow - today == 1){
                //換日，處理今日資料
                // private double density_avg = (jdbc.retrieveDensity_d_avg("trafficflowdata_ew_s") + jdbc.retrieveDensity_d_avg("trafficflowdata_ns_s"))/2;
                // jdbc.insertData_d(intersectionID, day, density_avg);
                day = LocalDate.now();
                
                //LD=> 0:EW, 1:NS
                
                //將資料包進RS, 好像快不需要了
                roadSituation todayRS_EW = new roadSituation(day, false, iDb.calculateTodayVehicleAmountAverage(false), iDb.calculateTodayEmergencyVehicleCount(false), iDb.calculateTodayDensityAverage(false));
                roadSituation todayRS_NS = new roadSituation(day, true, iDb.calculateTodayVehicleAmountAverage(true), iDb.calculateTodayEmergencyVehicleCount(true), iDb.calculateTodayDensityAverage(true));
                
                iDb_d.addIntersectionData(todayRS_EW, todayRS_NS);
                // 清空資料庫，重製時間
                iDb = new intersectionsDB();
                //iDb.addIntersectionData(todayRS_EW, todayRS_NS); //資料庫清空不需要預設資料
                today = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
                tomarrow = (int)System.currentTimeMillis() / (1000 * 60 * 60 * 24);
            }
            modeTimeNow = (int)System.currentTimeMillis() / 1000;
            timeNow = (int)System.currentTimeMillis() / 1000;
            if(time + 5 <= timeNow){
                camera_EW.shootIntersections();
                camera_NS.shootIntersections();
                // 每五秒拍攝一次路口，更新秒資料庫
                
                road_sum = new roadSituation_sum(camera_EW.RS.emergencyVehicle, camera_NS.RS.emergencyVehicle, camera_EW.RS.density, camera_NS.RS.density);
                iDb.addIntersectionData(camera_EW.RS,camera_NS.RS);
                // jdbc_test.insertData_ew_s(intersectionID, camera_EW.time, todayRS_EW.getEmergencyVehicle(), todayRS_EW.getDensity(), camera_EW.shootIntersections());//插入秒資料
                // jdbc_test.insertData_ew_s(intersectionID, camera_EW.time, todayRS_NS.getEmergencyVehicle(), todayRS_NS.getDensity(), camera_NS.shootIntersections());//插入秒資料
                
                time = (int)System.currentTimeMillis() / 1000;
                timeNow = (int)System.currentTimeMillis() / 1000;

                Last30DaysDensity_EW = iDb_d.calculateTheLast30DaysDensityAverage(false);
                Last30DaysDensity_NS = iDb_d.calculateTheLast30DaysDensityAverage(true);
            }
            modeTimeNow = (int)System.currentTimeMillis() / 1000;
            //套模板的條件    // 紅燈燈號結束前五秒 || 閃燈狀態下(10秒判斷一次)，從秒資料庫獲取最新一筆資料來判斷模板的變更與否
            if (pTS.mode_N == 2 && pTS.getSecond() == 1 && (pTS.now_Light.equals(new int[]{1, 0, 1})||pTS.now_Light.equals(new int[]{0, 1, 2}))) { // 正常換紅綠燈秒數時
                cP = judgeMode();
                if(pTS.mode_N == 0 || pTS.mode_N == 1){
                    modeTime = (int)System.currentTimeMillis() / 1000;
                    modeTimeNow = (int)System.currentTimeMillis() / 1000;
                }
            } else if(pTS.mode_N == 1 && modeTime + 10 <= modeTimeNow){ //閃燈判斷換模板
                modeTime = (int)System.currentTimeMillis() / 1000;
                modeTimeNow = (int)System.currentTimeMillis() / 1000;
                cP = judgeMode();
            } else if(pTS.mode_N == 0 && modeTime + 5 <= modeTimeNow){ //緊急判斷換模板
                modeTime = (int)System.currentTimeMillis() / 1000;
                modeTimeNow = (int)System.currentTimeMillis() / 1000;
                cP = judgeMode();
            }
            pTS.setcP(cP);
        } catch (Exception e) {
            
        }
        
    }

    private changedParameter judgeMode(){
        private Mode mode;
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
                    System.out.println("system error");
                    break;
            }
        }
        return mode.changeMode();
    }
}