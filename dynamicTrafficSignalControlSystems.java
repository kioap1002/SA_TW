import javafx.print.PrintColor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.time.*;
// import org.apache.commons.lang3.time;

public class dynamicTrafficSignalControlSystems {
    public static void main() {

    }
}
// 偵測相機 一個紅綠燈一個相機
class detectCamera {
    private Date time;// = now();
    private boolean laneDirection;  //false:南北, true:東西 //不是拍照能得到的訊息
    //private boolean emergencyVehicle = false; //好像可以用RS_E取代
    private calculation C = new calculation();
    protected roadSituation RS;  //private
    detectCamera(boolean LD){
        time = now();
        laneDirection = LD;
    }
    // 拍攝路口 將拍攝後的照片交給其他函式(?)處理
    public Void shootIntersections(){
        System.out.println("Shooting...");
        detectIntersections("emergencyVehicle = false;vehicleAmount=30;");//因為拍照和處理照片很麻煩，先寫這樣
        //return image ??????
        //updateCondition(laneDirection, emergencyVehicle, vehicleAmount);
    }
    // 處理路口照片 並將資料更新???
    public void detectIntersections(String imageMassage){
        boolean EV;
        int VA;
        System.out.println(imageMassage);//處理照片資訊，因為麻煩先這樣表示
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

//單純用來放置資料的物件
class roadSituation {
    private Date time;
    private boolean laneDirection;
    protected int vehicleAmount; //private
    protected boolean emergencyVehicle;  //private
    protected double density;  //private
    // private roadSituation_emergency RS_E;
    roadSituation(Date T, boolean LD, int VA, boolean EV, double den) {
        time = T;
        laneDirection = LD;
        vehicleAmount = VA;
        emergencyVehicle = EV;
        density = den;
    }
}

//單純用來設定和放置 east_westLane 跟 north_southLane 的
class roadSituation_sum {
    private boolean east_westLane = false;  //ew有無緊急車輛，false = ew車道上無緊急車輛
    private boolean north_southLane = false;  //ns有無緊急車輛，false = ns車道上無緊急車輛
    private double east_westDensity;
    private double north_southDensity;

    roadSituation_sum(boolean EV_EW,boolean EV_NS, double D_EW, double D_NS) {
        east_westLane = EV_EW;
        north_southLane = EV_NS;
        east_westDensity = D_EW;
        north_southDensity = D_NS;
    }
    //回傳緊急車輛出現狀態(有無緊急車輛, if 有: 車道方向為何)
    public int haveEmergency(){  //0:no_EV, 1:e_w_EV, 2:n_s_EV, 3:both_EV
        if(east_westLane && north_southLane){
            return 3;
        } else if(north_southLane){
            return 2;
        } else if(east_westLane){
            return 1;
        }else {
            return 0;
        }
    }
    
    public int densityMode_col(double Last30DaysDensity_EW, double Last30DaysDensity_NS){  //密度模板 0: 低 1:正常 2:高
        int EW=0,NS=0;
        if(east_westDensity > Last30DaysDensity_EW * 1.5) {
            EW = 2;  //高密度
        } else if (east_westDensity < Last30DaysDensity_EW * 0.5){
            EW = 0;  //低密度
        } else {
            EW = 1;  //普通密度
        }
        if(north_southDensity > Last30DaysDensity_NS * 1.5) {
            NS = 2;
        } else if (north_southDensity < Last30DaysDensity_NS * 0.5){
            NS = 0;
        } else {
            NS = 1;
        }
        densityMode(EW, NS);
    }
    // 單處理密度　密度模板 0: both高 1: EW高 2:NS高, 3: NS or EW普通, 4: both低
    public int densityMode(int EW, int NS){  
        int mode_D;
        if (EW == 2 || NS == 2){
            if (EW == 2 && NS == 2){
                // 兩邊高密度，增加時間配比
                mode_D = 0;
            } else if (EW == 2){
                // EW高密度，增加綠燈秒數
                mode_D = 1;
            } else {
                // NS高密度，增加紅燈秒數
                mode_D = 2;
            }
        } else if (EW == 1 || NS == 1){
            /*套用正常模板 */
            mode_D = 3;
        } else {
            /*套用低密度模板 */
            mode_D = 4;
        }
        return mode_D;
    }
}

class intersectionsDB {  //一直更新，單位為秒
    private double density;
    List <roadSituation> intersectionData_EW = new Arraylist<roadSituation>();
    List <roadSituation> intersectionData_NS = new Arraylist<roadSituation>();
    public void addIntersectionData(roadSituation RS_EW,roadSituation RS_NS){
        intersectionData_EW.add(RS_EW);
        intersectionData_NS.add(RS_NS);
    }
    //計算今日平均密度
    public double calculateTodayDensityAverage(boolean laneDirection){  
        double TodaysDensityAverage;
        double count=0.0;
        for(roadSituation rS : intersectionData_EW){
            TodaysDensityAverage += rS.density;
            count += 1.0;
        }
        TodaysDensityAverage /=count; 
        return TodaysDensityAverage;
    }
    //計算今日平均車數
    public double calculateTodayVehicleAmountAverage(boolean laneDirection){  
        double vehicleAmountAverage;
        
        return vehicleAmountAverage;
    }
    //計算今日緊急次數
    public double calculateTodayEmergencyVehicleCount(boolean laneDirection){  
        double emergencyVehicleCount;
        
        return emergencyVehicleCount;
    }

}

class intersectionsDB_day {
    private double density;
    List <roadSituation> intersectionData_EW = new Arraylist<roadSituation>();
    List <roadSituation> intersectionData_NS = new Arraylist<roadSituation>();
    List <roadSituation> intersectionData = new Arraylist<roadSituation>();
    public void addIntersectionData(roadSituation RS_EW,roadSituation RS_NS){  
        intersectionData_EW.add(RS_EW);
        intersectionData_NS.add(RS_NS);
    }

    public double calculateTheLast30DaysDensityAverage(boolean laneDirection){  //density
        double average_30 = 0.0;
        if(laneDirection){
            intersectionData = intersectionData_NS;
        }else{
            intersectionData = intersectionData_EW;
        }
        List<String> TheLast30Days = intersectionData.stream().skip(Math.max(0, intersectionData.size() - 30)).collect(Collectors.toList());
        for (roadSituation RS_I : TheLast30Days){
            average_30 += RS_I.density;
        }
        return average_30 / 30.0;
    }
}

class calculation {
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

class controlUnit {  //有手動跟自動的模式，loop控制更新資料庫 & 更改模板
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
            road_sum = new roadSituation_sum(camera_EW.RS.emergencyVehicle, camera_NS.RS.emergencyVehicle, camera_EW.RS.density, camera_NS.RS.density);
            iDb.addIntersectionData(camera_EW.RS,camera_NS.RS);
            time = LocalTime.now();
            timeNow = LocalTime.now();
            Last30DaysDensity_EW = iDb.calculateTheLast30DaysDensityAverage(false);
            Last30DaysDensity_NS = iDb.calculateTheLast30DaysDensityAverage(true);
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

    // public Void requestCalculateDensityDifferenceValue(){  //averageDensity, currentDensity
    //     densityDifferenceValue = C.getCalculateDensityDifferenceValue(iDb.calculateTheLast30DaysDensityAverage());
    // }
}

class trafficLight{
    private double greenLightTime_EW;//east_west_greenLight
    private double redLightTime_NS;//north_south_greenLight  
    private double yellowLightTime;
    private Mode trafficLightMode;// = new Mode();
    private changedParameter cP;


    trafficLight(changedParameter cP){
        this.cP = cP;//計算/調整完的時間
        //chandeMode();
    }
    trafficLight(double GT, double RT, double YT){
        this.greenLightTime_EW = GT;
        this.redLightTime_NS = RT;
        this.yellowLightTime = YT;
    }
    public void applyParameter(){

    }
}

abstract class Mode{ //回傳已經計算完的秒數
    public abstract changedParameter changeMode();
}

class emergencyMode extends Mode{
    private int EV;
    // private int D;
    // emergencyMode(int EV, int D){
    //     this.EV = EV;
    //     this.D = D;
    // }
    emergencyMode(int EV){
        this.EV = EV;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        
        switch (EV) {
            case 3: //both EV
                //全紅    
                cP.setParameter(3, 3, 3, 0);
                break;
            case 2:
                //NS EV
                cP.setParameter(2, 2, 2, 0);
                break;
            case 1:
                //EW EV
                cP.setParameter(1, 1, 1, 0);
                break;
            default:
                //something wrong
                System.out.println("wrong message");
                break;
        }
        return cP;
    }
}
class HighDensityMode extends Mode{
    private int D;
    private double EW_dF;
    private double NS_dF;
    HighDensityMode(int D, double EW_dF, double NS_dF){
        this.D = D;
        this.EW_dF = EW_dF;
        this.NS_dF = NS_dF;
    }
    public changedParameter changeMode(){
        
        
    }
}
class BasicDensityMode extends Mode{
    public changedParameter changeMode(){
        //拿到該路口的defalt
        changedParameter cP = new changedParameter();
        //light time == 0 => defalt
        cP.setParameter(0, 0, 0, 0);
        return cP;
    }
}
class LowDensityMode extends Mode{
    private int EW_right;
    private int NS_right;
    LowDensityMode(int EW, int NS){
        EW_right = EW;
        NS_right = NS;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        //判斷flashing的狀態
        int fla = 1;
        if(EW_right < NS_right){
            fla = 2;
        }
        cP.setParameter(0, 0, 0, fla);
        return cP;
    }
}

class changedParameter{
    protected double redLightTime;
    protected double greenLightTime;
    protected double yellowLightTime;
    protected int flashing;

    public void setParameter(double rlt, double glt, double ylt, int fla){
        redLightTime = rlt;
        greenLightTime = glt;
        yellowLightTime = ylt;
        flashing = fla;
    }
    public int check(){
        //if(fla == 0 && redLightTime == greenLightTime && greenLightTime == yellowLightTime && yellowLightTime == redLightTime){
            return (int)redLightTime;
        //}
    }
}
