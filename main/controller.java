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
    private int adjustmentResult;
    private double timer; //持續時間

    // https://www.uuu.com.tw/Public/content/article/18/20180430.htm
    // private Date time = now();
    // private Date timeNow = now();
    // https://blog.csdn.net/qq_37370132/article/details/107905587
    
    private LocalTime time = LocalTime.now();  //控制時間
    private LocalTime timeNow = LocalTime.now();  //控制時間(變動ver)
    private int secend = (int)System.currentTimeMillis() / 1000;
    private LocalDate date = LocalDate.now();  //控制日期
    private LocalDate dateNow = LocalDate.now();  //控制日期(變動ver)

    private intersectionsDB iDb = new intersectionsDB();
    private intersectionsDB_day iDb_d = new intersectionsDB_day();
    private east_westDetectCamera camera_EW;
    private north_southDetectCamera camera_NS;
    private trafficLight tL;  //用來傳我們要更改的Mode進去  //parameter
    private roadSituation_sum road_sum;

    private Mode mode;
    private physicalTrafficSignal pTS;
    pTS.EW_side_Passable_g();
    while(true){
        dateNow = LocalDate.now();
        if(dateNow - date == 1){
            //換日，把資料丟進日資料庫   (清空秒資料庫?)//清空

        }else {
            //持續計算密度&拍照
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
        }

    }

}