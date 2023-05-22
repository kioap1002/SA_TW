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
    // private double totalTime;
    // private double greenLightTime;
    // private double redLightTime;
    // private double yellowLightTime;
    // private trafficLight trafficL;
    // trafficLightTime(double TT, double GLT){ //只有高密度模板和普通模板會使用，高密度輸入的TT是增加的，正常TT是預設
    //     totalTime = TT;
    //     greenLightTime = GLT;
    //     redLightTime = TT - GLT - yellowLightTime;
    //     // 一輪紅綠燈的順序
    //     // GT_EW => (YT_EW -> RT_EW)
    //     // (YT_NS -> RT_NS) => GT_NS
    // }
    public void physicalTrafficSignal(double greenLightTime, double yellowLightTime, double redLightTime, ){
        boolean greenLight;
        int yellowLight;
        int redLight;
        int nowLight = 0;//0: 紅, 1: 綠, 2: 黃

        Timer greenLightTimer = new Timer();
        Timer yellowLightTimer = new Timer();
        Timer redLightTimer = new Timer();
        Timer totalTimer = new Timer();
        
        totalTimer.schedule(new TimerTask(){
            greenLightTimer.schedule(new TimerTask(){
                nowLight++;
            }, 0, greenLightTime);
            yellowLightTimer.schedule(new TimerTask(){
                nowLight++;
            }, 0, yellowLightTime);
            redLightTimer.schedule(new TimerTask(){
                nowLight=0;
            }, 0, redLightTime);
        }, 0, greenLightTime + yellowLightTime + redLightTime);
    }
    
}