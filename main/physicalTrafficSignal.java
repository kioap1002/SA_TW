package main;
// --------------
import java.time.LocalTime;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
// --------------


public class physicalTrafficSignal {
    int[] now_Light = {0, 0};//0: 紅, 1: 綠, 2: 黃
    int[] EW_Light = {0, 0, 0}; //綠, 黃, 紅 //綠: 0, 1 黃: 0, 1, 2 紅: 0, 1, 2 //0: 沒亮 1: 有亮 2: 閃燈
    int[] NS_Light = {0, 0, 0}; //綠, 黃, 紅
    physicalTrafficSignal(){
        
    }
    public void trafficLightTime(double greenLightTime_EW, double yellowLightTime, double greenLightTime_NS, double redLightTime ){
        // private long secend_pri = System.currentTimeMillis() / 1000;
        // private long secend_now = System.currentTimeMillis() / 1000;
        //boolen 
        while(true){ //應該會在controller做
            //secend_now = System.currentTimeMillis() / 1000;
            int seconds = (int)greenLightTime_EW; // 倒數秒數

            //切換成東西向綠燈
            for (int i = seconds; i >= 0; i--) {
                if (i == greenLightTime_EW && i == seconds){
                    EW_side_Passable_g();
                }
                try {
                    Thread.sleep(1000); // 暫停 1 秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public int[] EW_side_Passable_g (){
        EW_Light = new int[] {1, 0, 0};
        NS_Light = new int[] {0, 0, 1};
        now_Light = new int[] {1, 0};
        return now_Light;
    }
    public int[] EW_side_Passable_y(){
        EW_Light = new int[] {0, 1, 0};
        NS_Light = new int[] {0, 0, 1};
        now_Light = new int[] {2, 0};
        return now_Light;
    }
    public int[] Both_side_Passable_r(){
        EW_Light = new int[] {0, 0, 1};
        NS_Light = new int[] {0, 0, 1};
        now_Light = new int[] {0, 0};
        return now_Light;
    }
    public int[] NS_side_Passable_g(){
        EW_Light = new int[] {0, 0, 1};
        NS_Light = new int[] {1, 0, 0};
        now_Light = new int[] {0, 1};
        return now_Light;
    }
    public int[] NS_side_Passable_y(){
        EW_Light = new int[] {0, 0, 1};
        NS_Light = new int[] {0, 1, 0};
        now_Light = new int[] {0, 2};
        return now_Light;
    }

    public int[] trafficLightFlashing(int right){
        if (right == 1) {
            EW_Light = new int[] {0, 2, 0};
            NS_Light = new int[] {0, 0, 2};
            now_Light = new int[] {2, 0};
        } else {
            EW_Light = new int[] {0, 0, 2};
            NS_Light = new int[] {0, 2, 0};
            now_Light = new int[] {0, 2};
        }
        return now_Light;
    }
    public int[] trafficLightEmergency(int condition){
        if (condition == 1) {
            EW_Light = new int[] {1, 0, 0};
            NS_Light = new int[] {0, 0, 1};
            now_Light = new int[] {1, 0};
        } else if (condition == 2){
            EW_Light = new int[] {0, 0, 1};
            NS_Light = new int[] {1, 0, 0};
            now_Light = new int[] {0, 1};
        } else {
            EW_Light = new int[] {0, 0, 1};
            NS_Light = new int[] {0, 0, 1};
            now_Light = new int[] {0, 0};
        }
        return now_Light;
    }
}
/*
    * 當EW_side=1 東西向通行
    * EW_g_light<=1 NS_r_light<=1 EW綠燈 NS紅燈
    * EW_y_light<=1 NS_r_light<=1 EW黃燈 NS紅燈
    * EW_r_light<=1 NS_r_light<=1 全紅
    * 當EW_side=0 南北向通行
    * NS_g_light<=1 EW_r_light<=1 
    * NS_y_light<=1 EW_r_light<=1
    * NS_r_light<=1 EW_r_light<=1 全紅
*/



class DailyTaskScheduler {
    private static final LocalTime TIME_TO_RUN = LocalTime.of(0, 0); // 設定每天執行的時間

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        // 取得目前時間
        LocalTime currentTime = LocalTime.now();
        
        // 計算到下次執行時間的時間間隔
        long initialDelay = Duration.between(currentTime, TIME_TO_RUN).getSeconds();
        if (initialDelay < 0) {
            initialDelay += Duration.ofDays(1).getSeconds(); // 如果已經過了當天指定的時間，則將初始延遲增加一天
        }
        
        // 使用ScheduledExecutorService在每天指定時間執行任務
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 在這裡放置你想要在每天指定時間執行的程式碼
                // 更新數據到day DB
                System.out.println("執行每天指定時間的任務");
            }
        }, initialDelay, Duration.ofDays(1).getSeconds(), TimeUnit.SECONDS);
    }
}
