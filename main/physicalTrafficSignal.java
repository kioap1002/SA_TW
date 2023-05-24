package main;

public class physicalTrafficSignal {
    int[] now_Light = {0, 0};//0: 紅, 1: 綠, 2: 黃
    int[] EW_Light = {0, 0, 0}; //綠, 黃, 紅 //綠: 0, 1 黃: 0, 1, 2 紅: 0, 1, 2 //0: 沒亮 1: 有亮 2: 閃燈
    int[] NS_Light = {0, 0, 0}; //綠, 黃, 紅
    // physicalTrafficSignal(){
        
    // }
    int seconds = 0; // 倒數秒數
    public void trafficLightTime(double greenLightTime_EW, double yellowLightTime, double greenLightTime_NS, double redLightTime ){
        // private long secend_pri = System.currentTimeMillis() / 1000;
        // private long secend_now = System.currentTimeMillis() / 1000;
        // boolean 
        while(true){ //應該會在controller做
            //secend_now = System.currentTimeMillis() / 1000;
            seconds = (int)greenLightTime_EW; // 倒數秒數
            //切換成東西向綠燈
            EW_side_Passable_g();
            for (int i = seconds; i >= 0; i--) {
                try {
                    Thread.sleep(1000); // 暫停 1 秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            EW_side_Passable_y();
            try {
                Thread.sleep((int)yellowLightTime*1000); // 黃燈時間
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Both_side_Passable_r();
            try {
                Thread.sleep((int)yellowLightTime*1000); // 全紅時間
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds = (int)greenLightTime_NS;
            for (int i = seconds; i >= 0; i--) {
                try {
                    Thread.sleep(1000); // 暫停 1 秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            EW_side_Passable_y();
            try {
                Thread.sleep((int)yellowLightTime*1000); // 黃燈時間
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Both_side_Passable_r();
            try {
                Thread.sleep((int)yellowLightTime*1000); // 全紅時間
            } catch (InterruptedException e) {
                e.printStackTrace();
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
/* 紅綠燈轉換情況
 * 高、普、低、緊
 * 
 * 高⇒普：直接改變秒數
 * 高⇒低：路權大的那一方黃燈時改變
 * 高⇒緊：兩方：綠燈方黃燈再全紅
 *        緊急車輛方為綠燈：直接更改
 *                 為紅燈：對向車道進入黃燈時間，緊急向車道再切換成綠燈
 * 
 * 普⇒高：直接改變秒數
 * 普⇒低：路權大的那一方黃燈時改變
 * 普⇒緊：同高⇒緊
 * 
 * 低⇒高：全紅三秒後，套用計算好的秒數，依序顯示
 * 低⇒普：全紅三秒後，回歸基礎，依序顯示
 * 低⇒緊：不需要考慮
 * 
 * 緊⇒高：全紅三秒後，套用計算好的秒數，依序顯示
 * 緊⇒普：全紅三秒後，回歸基礎，依序顯示
 * 緊⇒低：不需要考慮
 */
/* 紅綠燈轉換情況 相同處理整理
 * 高、普、低、緊
 * 1. 高⇒普、普⇒高：直接改變秒數
 * 2. 高⇒低、普⇒低：路權大的那一方黃燈時改變
 * 3. 高⇒緊、普⇒緊：兩方：綠燈方黃燈再全紅
 *             緊急車輛方為綠燈：直接更改
 *                      為紅燈：對向車道進入黃燈時間，緊急向車道再切換成綠燈
 * 4. 低⇒高、緊⇒高：全紅三秒後，套用計算好的秒數，依序顯示
 * 5. 低⇒普、緊⇒普：全紅三秒後，回歸基礎，依序顯示
 * 6. 低⇒緊、緊⇒低：不需要考慮
 */




