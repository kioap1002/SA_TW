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
        updateCondition(IMlist[0],IMlist[1]);
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
    private int vehicleAmount;
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

    // public int densityMode_EW(double Last30DaysDensity_EW){  //密度模板 0: 低 1:正常 2:高
    //     if(east_westDensity > Last30DaysDensity_EW * 1.5) {
    //         return 2;  //高密度
    //     } else if (camera_EW.RS.density < Last30DaysDensity_EW * 0.5){
    //         return 0;  //低密度
    //     } else {
    //         return 1;  //普通密度
    //     }
    // }
    // public int densityMode_NS(double Last30DaysDensity_NS){  //密度模板 0: 低 1:正常 2:高
    //     if(camera_NS.RS.density > Last30DaysDensity_NS * 1.5) {
    //         return 2;
    //     } else if (camera_NS.RS.density < Last30DaysDensity_NS * 0.5){
    //         mode_NS = 0;
    //     } else {
    //         mode_NS = 1;
    //     }
    // }

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
    // 單處理密度
    // 密度模板 0: both高 1: EW高 2:NS高, 3: NS or EW普通, 4: both低
    public int densityMode(int EW, int NS){  
        int mode_D;
        if (EW == 2 || NS == 2){
            // densityDifferenceValue_EW = camera_EW.C.getCalculateDensityDifferenceValue(Last30DaysDensity_EW);
            // densityDifferenceValue_NS = camera_NS.C.getCalculateDensityDifferenceValue(Last30DaysDensity_NS);
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
    //private roadSituation roadSituation_now;//???
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

class intersectionsDB_day {  //testing...
    private double density;
    //private roadSituation roadSituation_now;//???
    List <roadSituation> intersectionData_EW = new Arraylist<roadSituation>();  //要改存日數據?  每日數據ver?
    List <roadSituation> intersectionData_NS = new Arraylist<roadSituation>();
    List <roadSituation> intersectionData = new Arraylist<roadSituation>();
    public void addIntersectionData(roadSituation RS_EW,roadSituation RS_NS){  
        //如果日期更新就會存資料
        //在DB還是可以找到當日詳細數據?
        //日期          路口名稱    拍攝時間    密度    車子數量    有無緊急(0,1,2,3)    日平均密度
        //2200/01/01    某路口      00:00:05   2.7     30          1                   
        //                          00:00:10   2.3     27          1                   
        //                                        ...
        //     /n          /n          /n       /n        /n             /n              25.83
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
    //private int vehicleAmount;
    private double density;
    private double densityDiffer;
    public double calculateVehicleDensity(int vehicleAmount){  //vehicleAmount
        this.density =  (double)vehicleAmount / laneLength;
        return density;
    }
    public double getCalculateDensityDifferenceValue(double averageDensity){   //averageDensity, currentDensity
        this.densityDiffer = averageDensity - density;
        return densityDiffer;
    }
}

class controlUnit {  //有手動跟自動的模式，loop控制更新資料庫 & 更改模板
    private double Last30DaysDensity_EW;
    private double Last30DaysDensity_NS;
    private double densityDifferenceValue_EW;//高密度mode計算綠燈增加秒數用
    private double densityDifferenceValue_NS;//高密度mode計算綠燈增加秒數用
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
    private trafficLight tL;  //用來傳我們要更改的Mode進去
    private roadSituation_sum road_sum;

    private Mode mode_current;
    
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
            Mode temp;
            if(road_sum.haveEmergency() != 0){
                //road_sum.haveEmergency(), road_sum.densityMode_col(Last30DaysDensity_EW, Last30DaysDensity_NS)
                temp = new emergencyMode();
                tL(temp.changeMode());
            }else{
                temp = new Mode(road_sum.densityMode_col(Last30DaysDensity_EW, Last30DaysDensity_NS));
            }
            mode_current = temp.chandeMode();
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
    // trafficLight(Mode mode){
    //     this.trafficLightMode = mode;  //根據傳入的模板更改為高/低/普通/緊急
    //     changeMode(trafficLightMode.redLightTime, trafficLightMode.greenLightTime, trafficLightMode.flashingLight);
    // }
    trafficLight(changedParameter cP){
        this.cP = cP;//計算/調整完的時間
    }
    trafficLight(double GT, double RT, double YT){//來源不明  預設? 預設ㄅ
        this.greenLightTime_EW = GT;
        this.redLightTime_NS = RT;
        this.yellowLightTime = YT;
    }
    // public void getMode(Mode mode){  //先獲取要改變的模板，再呼叫changeMode
    //     this.trafficLightMode = mode;
    //     changeMode(trafficLightMode.redLightTime, trafficLightMode.greenLightTime, trafficLightMode.flashingLight);
    // }
    public void changeMode(double RLT, double GLT, int flashing){
        if(flashing == 0){
            redLightTime_NS = RLT;
            greenLightTime_EW = GLT;
        }else if(flashing == 1){
            //將燈號設置為：東西閃紅燈/南北閃黃燈
            //return changeResult; ?是所有套用都要回傳控制結果還是只有手動要? //目前是只有手動要
        }else if(flashing == 2){
            //將燈號設置為：南北閃紅燈/東西閃黃燈
        }
    }
}

abstract class Mode{
    public abstract changedParameter changeMode();
}

class emergencyMode extends Mode{
    private int EV;
    private int D;
    emergencyMode(int EV, int D){
        this.EV = EV;
        this.D = D;
    }
    public changedParameter changeMode(){
        switch (EV) {
            case 3: //both EV
                //全紅    
                break;
            case 2:
                //NS EV
                
                break;
            case 1:
                //EW EV
                break;
            default:
                //something wrong
                break;
        }
        

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
    private int EW;
    private int NS;
    LowDensityMode(int EW_right, int NS_right){
        EW = EW_right;
        NS = NS_right;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        //判斷flashing的狀態
        int fla = 1;
        if(EW < NS){
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
}

class trafficLightTime {
    private double totalTime;
    private double greenLightTime;
    private double redLightTime;
    private double yellowLightTime;
    private trafficLight trafficL;
    trafficLightTime(double TT, double GLT){ //只有高密度模板和普通模板會使用，高密度輸入的TT是增加的，正常TT是預設
        totalTime = TT;
        greenLightTime = GLT;
        redLightTime = TT - GLT - yellowLightTime;
        // 一輪紅綠燈的順序
        // GT_EW => (YT_EW -> RT_EW)
        // (YT_NS -> RT_NS) => GT_NS
    }
}

/* 查到的資料，法律規定
 * 
 * 行車管制號誌之週期長度，以三○秒至二○○秒為原則。
 * 
 * 設置黃燈秒數規定
 * 速限x(公里/小時) 黃燈秒數s
 * x<=50    | 3s
 * 50<x<=60 | 4s
 * 60<x     | 5s
 * 
 * 行車管制號誌在黃色燈號結束後，應有一秒以上之全紅時間。 
 * 直行交通之全紅時間計算式(不確定全紅時間指的是什麼)
 * 只有車子 (W+L)/2V ~ (W+L)/V
 * 有行人時 (P+L)/2V ~ (P+L)/V
 * W:交岔路口近端停止線至遠端路段起點之距離長度(公尺)
 * P:交岔路口近端停止線至遠端行人穿越道之距離長度(公尺)
 * (看不懂W、P的法律文說明，簡單來說應該是路口長度，應該需要分EW、NS)
 * Ｌ：平均車長，得採用六公尺。 
 * Ｖ：平均車速，得採用行車速限。單位：公尺／秒
 * 以(W+L)/V 為原則，最短不得小於(W+L)/2V。
 * 
 * 行車管制號誌轉變為閃光號誌時
 * 幹線道上號誌應由綠色燈號經過黃色燈號時段轉變為閃光黃燈
 * 支線道上號誌應由紅燈轉變為閃光紅燈
 * 由閃光號誌轉變為行車管制號誌時，應有三秒全紅時間，再循序轉換。
 * 
 * 行人專用號誌在綠色「行走行人」燈號結束前，應有閃光運轉，
 * 其閃光時間應適足以使已進入道路之行人能以正常速率走完全程
 * 或到達可供行人避護之交通島；其計算公式如下：
 * t ＝dw／v，其中
 * t ：閃光綠燈時間。
 * dw：路口無供行人避讓之交通島時為橫越路口寬；
 * 路口有供行人避讓之交通島時為路邊緣石至供行人避讓交通島寬度較寬。
 * V ：行走速率，一般使用一公尺／秒，
 * 學童眾多地點使用零點八公尺／秒，
 * 盲人音響號誌處使用零點五公尺／秒。
 * 
 * 行車管制號誌之啟動及斷電重開，其燈號顯示須先全紅三秒後再循序運轉。
 */
/* 問bing如何根據車子數量計算綠燈秒數的部分
 * greenLightDuration = (totalCars / carsPerSecond) + yellowLightDuration;
 * totalCars為偵測到的車子總數 : vihicleAmount
 * carsPerSecond則是每秒通過的車子數
 * yellowLightDuration則是黃燈的秒數，通常設定為 3 秒
 * Bing的參考資料
 * (1) 紅燈等太久？交控中心隨時調秒數│紅綠燈│TVBS新聞網. https://news.tvbs.com.tw/life/328720.
 * (2) 交通號誌 - 維基百科，自由的百科全書. https://zh.wikipedia.org/zh-tw/%E7%B4%85%E7%B6%A0%E7%87%88.
 * 
 * 要計算每秒通過的車子數，可以使用以下公式：
 * carsPerSecond = totalCars / time;
 * totalCars為偵測到的車子總數
 * time則是偵測到這些車子所花費的時間
 * 例如，如果偵測到了 100 輛車，
 * 並且這些車子通過所花費的時間為 10 秒，
 * 那麼每秒通過的車子數就是 10。
 */
/* 引用至維基
 * 有些地方在一些情況下可無視號誌燈號而將通行權優先讓給特殊車輛，
 * 通常這些車輛是緊急車輛（例如：消防車、救護車和警車），
 * 大多數的系統
 * 使用小型且可以發送無線電波、紅外線或閃光燈號誌的發射器
 * 使裝設在號誌燈附近的感應器能夠接收得到，
 * 而有些系統則使用聲音偵測的方式，
 * 其中必須使用某種類型的汽笛讓號誌燈上的感應器能接收得到。
 * 一旦號誌燈上的感應器偵測到有特殊車輛經過，
 * 正常的號誌燈週期將被阻斷並將通行權優先讓給特殊車輛：
 * 路口所有方向的號誌燈將轉為紅燈，除了該特殊車輛有通行權之外，
 * 各方向的車都必須停止。
 * 而有的時候會
 * 在一般的交通號誌燈旁增設額外的號誌燈
 * 讓該特殊車輛知道這個「優先通行機制」已被觸發
 * 並告知其他所有車輛有特殊車輛正接近當中，
 * 當這個機制全部結束之後就會恢復成原本正常的號誌燈週期。
 * 在大部分地區，使用這種「優先通行機制」的特殊車輛可不必遵循交通號誌燈。
 * 然而，該特殊車輛必須放慢速度、小心行駛並
 * 開啟警示燈以告知迎面而來的駕駛有特殊緊急車輛經過。
 */