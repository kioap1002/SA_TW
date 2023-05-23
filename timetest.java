import java.util.Timer;
import java.util.TimerTask;

public class timetest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("5秒已經過去了");
            }
        },0, 5000);
    }
}

public class TimerExample {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Hello World!");
            }
        };
        timer.schedule(task, 0, 5000);
    }
}

class trafficLightTime {

    public void physicalTrafficSignal(double greenLightTime_EW, double yellowLightTime, double greenLightTime_NS, double redLightTime ){

        int EW_now_Light = 0;//0: 紅, 1: 綠, 2: 黃
        int NS_now_Light = 0;//0: 紅, 1: 綠, 2: 黃
        
        int[] EW_Light = {0, 0, 0}; //綠, 黃, 紅 //綠: 0, 1 黃: 0, 1, 2 紅: 0, 1, 2 //0: 沒亮 1: 有亮 2: 閃燈
        int[] NS_Light = {0, 0, 0}; //綠, 黃, 紅

        Timer LightTimer = new Timer();
        
        TimerTask EW_side_Passable_g = new TimerTask(){
            public void run(){
                EW_Light = new int[] {1, 0, 0};
                NS_Light = new int[] {0, 0, 1};
            }
        }

        TimerTask EW_side_Passable_y = new TimerTask(){
            public void run(){
                EW_Light = new int[] {0, 1, 0};
                NS_Light = new int[] {0, 0, 1};
            }
        }

        TimerTask Both_side_Passable_r = new TimerTask(){
            public void run(){
                EW_Light = new int[] {0, 0, 1};
                NS_Light = new int[] {0, 0, 1};
            }
        }

        TimerTask NS_side_Passable_g = new TimerTask(){
            public void run(){
                EW_Light = new int[] {0, 0, 1};
                NS_Light = new int[] {1, 0, 0};
            }
        }

        TimerTask NS_side_Passable_y = new TimerTask(){
            public void run(){
                EW_Light = new int[] {0, 0, 1};
                NS_Light = new int[] {0, 1, 0};
            }
        }

        while(true){
            //EW亮起綠燈
            LightTimer.schedule(EW_side_Passable_g, 0);                 //EW綠燈，等待0秒後執行
            LightTimer.schedule(EW_side_Passable_y, greenLightTime_EW); //EW黃燈，等待綠燈結束後執行
            LightTimer.schedule(Both_side_Passable_r, yellowLightTime); //全紅燈，等待黃燈結束後執行
            LightTimer.schedule(NS_side_Passable_g, redLightTime);      //NS綠燈，等待全紅時間後執行
            LightTimer.schedule(NS_side_Passable_y, greenLightTime_NS); //NS黃燈，等待綠燈結束後執行
            LightTimer.schedule(Both_side_Passable_r, yellowLightTime); //全紅燈，等待黃燈結束後執行

        }
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