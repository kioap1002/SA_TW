package main;
import java.io.File;
import java.sql.Time;
import java.time.*;
import java.util.Date;

// 偵測相機 一個紅綠燈一個相機
public class detectCamera {
    private Date date;// = now();
    protected Time time;
    private boolean laneDirection;  //false:南北, true:東西 //不是拍照能得到的訊息
    //private boolean emergencyVehicle = false; //好像可以用RS_E取代
    private calculation C = new calculation();
    protected roadSituation RS;  //private
    detectCamera(boolean LD){
        date = new Date();  //time = LocalTime.now();
        laneDirection = LD;
    }
    // 拍攝路口 將拍攝後的照片交給其他函式(?)處理
    public File shootIntersections(){
        System.out.println("Shooting...");
        detectIntersections("emergencyVehicle = false;vehicleAmount=30;");//因為拍照和處理照片很麻煩，先寫這樣
        //return image ??????
        //updateCondition(laneDirection, emergencyVehicle, vehicleAmount);
        String img_path = "path/img.jpg";
        File file = new File(img_path);
        return file;
    }
    // 處理路口照片 並將資料更新???
    public void detectIntersections(String imageMassage){
        boolean EV;
        double VA;
        // System.out.println(imageMassage);//處理照片資訊，因為麻煩先這樣表示
        //IMlist[1]=[false, 30]; // EV, VA 處理照片資訊完得到的資料，因為麻煩先這樣表示
        //updateCondition(IMlist[0],IMlist[1]);
        RS = new roadSituation(time, laneDirection, VA, EV, C.calculateVehicleDensity(VA));
    }
    // 設定最新資料
    //public Void updateCondition(boolean EV, int VA){  //intersectionSituation // 緊急車輛經過狀態&道路狀態
        //emergencyVehicle = EV;
        //RS = new roadSituation(time, laneDirection, VA, EV, C.calculateVehicleDensity(VA));
    //}
}

// 下面兩個class都是依據相機設定車道方向
class east_westDetectCamera extends detectCamera{
    east_westDetectCamera(){
        super(false);
    }
}
class north_southDetectCamera extends detectCamera{
    north_southDetectCamera(){
        super(true);
    }
}