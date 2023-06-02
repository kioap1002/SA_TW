package com.example.data;

import java.time.*;
import java.util.Arrays;
import java.util.Scanner;

import com.example.model.Intersection_static;
import com.example.model.TrafficFlow_ew_s;
import com.example.model.TrafficFlow_ns_s;
import com.example.model.Trafficflow_d;

// import jdbc_test.*;

public class controller { // 有手動跟自動的模式，loop控制更新資料庫 & 更改模板
        // 近30天平均密度
        private double Last30DaysDensity_EW;
        private double Last30DaysDensity_NS;
        private double Last30DaysDensity;
        // 日資料庫的日期
        private LocalDate day = LocalDate.now();
        // 控制每五秒拍攝一次的變數，上固定下變動
        private int time = (int) System.currentTimeMillis() / 1000;
        private int timeNow = (int) System.currentTimeMillis() / 1000;
        // 控制患日更新的變數，上固定下變動
        private int today = (int) System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        private int tomarrow = (int) System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        // 控制緊急模板與低密度模板時判斷模板變數，上固定下變動
        private int modeTime = (int) System.currentTimeMillis() / 1000;
        private int modeTimeNow = (int) System.currentTimeMillis() / 1000;
        // 上秒資料庫、下日資料庫，之後可能會改掉
        private intersectionsDB iDb = new intersectionsDB();
        private intersectionsDB_day iDb_d = new intersectionsDB_day();
        // 相機
        private east_westDetectCamera camera_EW;
        private north_southDetectCamera camera_NS;
        // 整合路口資訊
        private roadSituation_sum road_sum;
        // 提供給pTS的資料
        private changedParameter cP;
        // 實體紅綠燈
        private physicalTrafficSignal pTS = new physicalTrafficSignal();// 用來傳我們要更改的Mode進去 //parameter
        // 取得路口資訊的部份，未完善好，路口資訊需要得到的部分，路權、預設秒數
        //DB相關
        DBManager dbmanager;
        Intersection_static intersection_static;
        Trafficflow_d trafficFlow_d;
        TrafficFlow_ew_s trafficFlow_ew_s;
        TrafficFlow_ns_s trafficFlow_ns_s;
        private String rid = "R01";
        
        // 預設秒數 int[5]，  [0]: glt_EW, [1]: ylt_EW, [2]:arlt_EW, [3]: glt_NS, [4]:ylt_NS,
        // [5]:arlt_NS
        private int[] lightTime = { 0, 0, 0, 0, 0, 0 };
        private Mode mode_B = new BasicDensityMode(lightTime);
        private Scanner userInput = new Scanner(System.in);
        private int[] right = {0 ,0 };// 路權 int[1]，  [0]: right_EW, [1]: right_NS
        
        controller(){
	        // 先套預設模板
	        pTS.setcP(mode_B.changeMode());
	        pTS.changeTrafficLight();
	        
	        //get road right
	        right[0] = dbmanager.getRroadRightByRoadIntersectionId(rid,"ew")?1:0;
	        right[1] = dbmanager.getRroadRightByRoadIntersectionId(rid,"ns")?1:0;
	        
	        
	        //get lightTime
	        lightTime[0] = dbmanager.getGreenLightTime(rid, "ew");
	        lightTime[1] = 0;//自己算 y
	        lightTime[2] = 0;//自己算 ar
	        lightTime[3] = dbmanager.getGreenLightTime(rid, "ns");
	        lightTime[4] = 0;//自己算 y
	        lightTime[5] = 0;//自己算 ar
	        
	        String action = "AUTO";
	        while (true) {
	            action = userInput.nextLine();
	            if (action.equals("AUTO")) {
	                autoMode();
	            } else {
	                manualMode();
	                action = "AUTO";
	            }
	            if (action.equals("CLOSE")) break;
	         }
         }

        

    public void autoMode() {
        try {
            tomarrow = (int) System.currentTimeMillis() / (1000 * 60 * 60 * 24);
            if (tomarrow - today == 1) {
                // 換日，處理今日資料
                // private Long ID;
                // private String Road_Intersection_ID;
                // private LocalDate Date;
                // private Double Density_avg;
            	//增加資料
            	trafficFlow_d = new Trafficflow_d();
            	trafficFlow_d.setRoad_Intersection_ID(null);
            	trafficFlow_d.setDate(day);
            	double d = (dbmanager.getDensity_avg_ew() + dbmanager.getDensity_avg_ns())/2;
            	trafficFlow_d.setDensity_avg(d);
            	dbmanager.addTrafficFlow_d(trafficFlow_d);
            	
            	
            	Last30DaysDensity = dbmanager.getDensity();
                // Last30DaysDensity_EW = iDb_d.calculateTheLast30DaysDensityAverage(false);
                // Last30DaysDensity_NS = iDb_d.calculateTheLast30DaysDensityAverage(true);
                
                
                day = LocalDate.now();

                // LD=> 0:EW, 1:NS
                // 將資料包進RS, 好像快不需要了
                roadSituation todayRS_EW = new roadSituation(day, false, iDb.calculateTodayVehicleAmountAverage(false),
                        iDb.calculateTodayEmergencyVehicleCount(false), iDb.calculateTodayDensityAverage(false));
                roadSituation todayRS_NS = new roadSituation(day, true, iDb.calculateTodayVehicleAmountAverage(true),
                        iDb.calculateTodayEmergencyVehicleCount(true), iDb.calculateTodayDensityAverage(true));

                iDb_d.addIntersectionData(todayRS_EW, todayRS_NS);
                // 清空資料庫，重製時間
                iDb = new intersectionsDB();
                today = (int) System.currentTimeMillis() / (1000 * 60 * 60 * 24);
                tomarrow = (int) System.currentTimeMillis() / (1000 * 60 * 60 * 24);
            }
            timeNow = (int) System.currentTimeMillis() / 1000;
            // 每5秒拍攝一次
            if (time + 5 <= timeNow) {
                automaticShooting();
            }
            modeTimeNow = (int) System.currentTimeMillis() / 1000;
            // 套模板的條件
            // 正常狀態下(對向綠燈燈號結束前一秒) || 閃燈狀態下(10秒判斷一次) || 緊急狀態下(5秒判斷一次)
            // 從秒資料庫獲取最新一筆資料來判斷模板的變更與否
            if ((pTS.mode_N == 2 && pTS.getSecond() == 1
                    && (Arrays.equals(pTS.now_Light, new int[] { 1, 0, 1 })
                            || Arrays.equals(pTS.now_Light, new int[] { 0, 1, 2 })))|| // 正常判斷換模板
                    (pTS.mode_N == 1 && modeTime + 10 <= modeTimeNow) || // 閃燈判斷換模板
                    (pTS.mode_N == 0 && modeTime + 5 <= modeTimeNow)) { // 緊急判斷換模板
                cP = judgeMode();
                pTS.setcP(cP);
                pTS.changeTrafficLight();
                if (pTS.mode_N == 0 || pTS.mode_N == 1) {
                    modeTime = (int) System.currentTimeMillis() / 1000;
                    modeTimeNow = (int) System.currentTimeMillis() / 1000;
                }
            }
        } catch (Exception e) { // change mode to default
            pTS.setcP(mode_B.changeMode());
            pTS.changeTrafficLight();
        }
    }

    public void automaticShooting() {
        camera_EW.shootIntersections();
        camera_NS.shootIntersections();
        // 更新秒資料庫，之後可能會改
        iDb.addIntersectionData(camera_EW.RS, camera_NS.RS);

        //在mian call python shoot and return the info to rs, put the object(trafficFlow_ew_s) in rs
        //update: dbmanager.addTrafficFlow_ew_s(rs.getObject());  rs.getObject()=> return trafficFlow_ew_s object
        
        dbmanager.addTrafficFlow_ew_s(trafficFlow_ew_s);
        dbmanager.addTrafficFlow_ns_s(trafficFlow_ns_s);
        
        time = (int) System.currentTimeMillis() / 1000;
        timeNow = (int) System.currentTimeMillis() / 1000;
        // 下面3個可能會放到其他地方
        road_sum = new roadSituation_sum(camera_EW.RS.emergencyVehicle, camera_NS.RS.emergencyVehicle,
                camera_EW.RS.density, camera_NS.RS.density);

    }

    public changedParameter judgeMode() {
        Mode mode = mode_B;
        changedParameter cP_M;
        int EV = road_sum.haveEmergency();
        if (EV != 0) {
            mode = new emergencyMode(EV);
        } else {
            // 0: both高 1: EW高 2:NS高, 3: NS or EW普通, 4: both低
            switch (road_sum.densityMode_col(Last30DaysDensity)) {
                case 0:
                case 1:
                case 2:
                    mode = new HighDensityMode(road_sum.densityMode_col(Last30DaysDensity),
                            camera_EW.RS.density, camera_NS.RS.density, lightTime);
                    break;
                case 3:
                    mode = new BasicDensityMode(lightTime);
                    break;
                case 4:
                    mode = new LowDensityMode(right[0], right[1]);
                    break;
                default:
                    System.out.println("system error");
                    break;
            }
        }
        cP_M = mode.changeMode();
        return cP_M;
    }

    public void manualMode() {
        int situation = userInput.nextInt(); // 0: 某向一直綠燈, 1: 閃燈模式, 2:更改正常秒數, 3: 更改預設秒數
        if (situation != 3) {
            Mode mode_M = mode_B;
            int timer = userInput.nextInt();
            switch (situation) {
                case 0:
                    int direction = userInput.nextInt(); // 0: EW, 1: NS
                    mode_M = new manualMode(direction);
                    break;
                case 1:
                    mode_M = new manualMode(right[0], right[1]);
                    break;
                case 2:
                    int glt_EW = userInput.nextInt();
                    int ylt_EW = userInput.nextInt();
                    int arlt_EW = userInput.nextInt();
                    int glt_NS = userInput.nextInt();
                    int ylt_NS = userInput.nextInt();
                    int arlt_NS = userInput.nextInt();
                    mode_M = new manualMode(glt_EW, ylt_EW, arlt_EW, glt_NS, ylt_NS, arlt_NS);
                    break;
                default:
                    break;
            }
            pTS.setcP(mode_M.changeMode());
            pTS.trafficLightManual(timer, situation);
        } else {
            int glt_EW = userInput.nextInt();
            int ylt_EW = userInput.nextInt();
            int arlt_EW = userInput.nextInt();
            int glt_NS = userInput.nextInt();
            int ylt_NS = userInput.nextInt();
            int arlt_NS = userInput.nextInt();
            lightTime[0] = glt_EW;
            lightTime[1] = ylt_EW;
            lightTime[2] = arlt_EW;
            lightTime[3] = glt_NS;
            lightTime[4] = ylt_NS;
            lightTime[5] = arlt_NS;
        }
    }
}