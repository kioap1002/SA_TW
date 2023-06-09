package com.example.data;

import java.util.Arrays;

public class physicalTrafficSignal {
    protected int[] now_Light = { 0, 0, 2 };// [1]EW, [2]NS, 0: 紅, 1: 綠, 2: 黃，[2]代表現在輪到哪一方，0: 閃燈, 1: EW, 2: NS，預設2
    private int[] EW_Light = { 0, 0, 0 }; // 綠, 黃, 紅 //綠: 0, 1 黃: 0, 1, 2 紅: 0, 1, 2 //0: 沒亮 1: 有亮 2: 閃燈
    private int[] NS_Light = { 0, 0, 0 }; // 綠, 黃, 紅

    protected int seconds = 0; // 剩下的倒數秒數
    protected int mode_N = 2;// 0: 緊急 1: 有閃 2: 正常(高密度&普通)

    // 讓閃燈模式能按順序變化的秒數
    private int greenLightTime_EW = 15;
    private int yellowLightTime_EW = 3;
    private int allRedLightTime_EW = 1;
    private int greenLightTime_NS = 15;
    private int yellowLightTime_NS = 3;
    private int allRedLightTime_NS = 1;

    // physicalTrafficSignal(){}
    public int countDownSeconds = 0;
    private changedParameter cP;

    public void setcP(changedParameter cP) {
        this.cP = cP;
    }

    public void changeTrafficLight() {
        if (cP.flashing == 0 && cP.condition == 0) {
            trafficLightTime();
        } else if (cP.condition != 0) {
            trafficLightEmergency(cP.condition);
        } else {
            trafficLightFlashing(cP.flashing);
        }
    }

    // trafficLightTime的參數會存在cP裡，直接從cP拿
    public void trafficLightTime() {
        this.greenLightTime_EW = cP.greenLightTime_EW;
        this.yellowLightTime_EW = cP.yellowLightTime_EW;
        this.allRedLightTime_EW = cP.allRedLightTime_EW;
        this.greenLightTime_NS = cP.greenLightTime_NS;
        this.yellowLightTime_NS = cP.yellowLightTime_NS;
        this.allRedLightTime_NS = cP.allRedLightTime_NS;

        if (Arrays.equals(now_Light, new int[] { 2, 0, 0 }) || Arrays.equals(now_Light, new int[] { 1, 0, 0 })) {
            NS_side_ar();
            countDown(3);
        } else {
            countDown(seconds);
        }

        mode_N = 2;
        while (mode_N == 2) {
            if (Arrays.equals(now_Light, new int[] { 0, 0, 2 })) {
                EW_side_g();
                countDownSeconds = greenLightTime_EW;
            } else if (Arrays.equals(now_Light, new int[] { 1, 0, 1 })) {
                EW_side_y();
                countDownSeconds = yellowLightTime_EW;
            } else if (Arrays.equals(now_Light, new int[] { 2, 0, 1 })) {
                EW_side_ar();
                countDownSeconds = allRedLightTime_EW;
            } else if (Arrays.equals(now_Light, new int[] { 0, 0, 1 })) {
                NS_side_g();
                countDownSeconds = greenLightTime_NS;
            } else if (Arrays.equals(now_Light, new int[] { 0, 1, 2 })) {
                NS_side_y();
                countDownSeconds = yellowLightTime_NS;
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 2 })) {
                NS_side_ar();
                countDownSeconds = allRedLightTime_NS;
            }
            countDown(countDownSeconds);
        }
    }

    public void trafficLightFlashing(int right) {
        mode_N = 1;
        countDown(seconds);
        if (right == 1) { // EW路權大
            if (Arrays.equals(now_Light, new int[] { 1, 0, 1 })) { // EW綠燈
                EW_side_y();
                countDown(yellowLightTime_EW);
            } else if (Arrays.equals(now_Light, new int[] { 0, 0, 1 })) { // EW⇒NS紅燈
                NS_side_g();
                countDown(greenLightTime_NS);
                NS_side_y();
                countDown(yellowLightTime_NS);
                NS_side_ar();
                countDown(allRedLightTime_NS);
                EW_side_g();
                countDown(greenLightTime_EW);
                EW_side_y();
                countDown(yellowLightTime_EW);
            } else if (Arrays.equals(now_Light, new int[] { 0, 1, 2 })) { // NS綠燈
                NS_side_y();
                countDown(yellowLightTime_NS);
                NS_side_ar();
                countDown(allRedLightTime_NS);
                EW_side_g();
                countDown(greenLightTime_EW);
                EW_side_y();
                countDown(yellowLightTime_EW);
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 2 })) { // NS黃燈
                NS_side_ar();
                countDown(allRedLightTime_NS);
                EW_side_g();
                countDown(greenLightTime_EW);
                EW_side_y();
                countDown(yellowLightTime_EW);
            } else if (Arrays.equals(now_Light, new int[] { 0, 0, 2 })) { // NS⇒EW紅燈
                EW_side_g();
                countDown(greenLightTime_EW);
                EW_side_y();
                countDown(yellowLightTime_EW);
            }
            EW_side_f();
        } else { // NS路權大
            if (Arrays.equals(now_Light, new int[] { 0, 1, 2 })) { // NS綠燈 OK
                NS_side_y();
                countDown(yellowLightTime_NS);
            } else if (Arrays.equals(now_Light, new int[] { 0, 0, 2 })) { // NS⇒EW紅燈
                EW_side_g();
                countDown(greenLightTime_EW);
                EW_side_y();
                countDown(yellowLightTime_EW);
                EW_side_ar();
                countDown(allRedLightTime_EW);
                NS_side_g();
                countDown(greenLightTime_NS);
                NS_side_y();
                countDown(yellowLightTime_NS);
            } else if (Arrays.equals(now_Light, new int[] { 1, 0, 1 })) { // EW綠燈 OK
                EW_side_y();
                countDown(yellowLightTime_EW);
                EW_side_ar();
                countDown(allRedLightTime_EW);
                NS_side_g();
                countDown(greenLightTime_NS);
                NS_side_y();
                countDown(yellowLightTime_NS);
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 1 })) { // EW黃燈 ok
                EW_side_ar();
                countDown(allRedLightTime_EW);
                NS_side_g();
                countDown(greenLightTime_NS);
                NS_side_y();
                countDown(yellowLightTime_NS);
            } else if (Arrays.equals(now_Light, new int[] { 0, 0, 1 })) { // EW⇒NS紅燈
                NS_side_g();
                countDown(greenLightTime_NS);
                NS_side_y();
                countDown(yellowLightTime_NS);
            }
            NS_side_f();
        }
    }

    // condition在cP裡判定，直接呼叫cP的condition
    public void trafficLightEmergency(int condition) {
        mode_N = 0;
        if (condition == 1) { // 緊急車輛從EW來時
            if (Arrays.equals(now_Light, new int[] { 0, 1, 2 })) { // NS是綠燈
                NS_side_y();
                countDown(3);
                NS_side_ar();
                countDown(1);
                seconds = greenLightTime_EW;
                EW_side_g();
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 2 })) { // NS是黃燈
                countDown(seconds);
                NS_side_ar();
                countDown(1);
                seconds = greenLightTime_EW;
                EW_side_g();
            } else if (Arrays.equals(now_Light, new int[] { 2, 0, 0 })) { // 如果之前是閃燈模式
                EW_side_f();
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 0 })) { // 如果之前是閃燈模式
                NS_side_f();
            } else { // NS是紅燈
                if (Arrays.equals(now_Light, new int[] { 0, 0, 2 })) {
                    seconds = greenLightTime_EW;
                } else if (Arrays.equals(now_Light, new int[] { 2, 0, 1 })
                        || Arrays.equals(now_Light, new int[] { 0, 0, 1 })) {
                    seconds = 5;
                }
                EW_side_g();
            }
        } else if (condition == 2) { // 緊急車輛從NS來時
            if (Arrays.equals(now_Light, new int[] { 1, 0, 1 })) { // EW是綠燈
                EW_side_y();
                countDown(3);
                EW_side_ar();
                countDown(1);
                seconds = greenLightTime_NS;
                NS_side_g();
            } else if (Arrays.equals(now_Light, new int[] { 2, 0, 1 })) { // EW是黃燈
                countDown(seconds);
                EW_side_ar();
                countDown(1);
                seconds = greenLightTime_NS;
                NS_side_g();
            } else if (Arrays.equals(now_Light, new int[] { 2, 0, 0 })) { // 如果之前是閃燈模式
                EW_side_f();
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 0 })) { // 如果之前是閃燈模式
                NS_side_f();
            } else { // EW是紅燈
                if (Arrays.equals(now_Light, new int[] { 0, 0, 1 })) {
                    seconds = greenLightTime_NS;
                } else if (Arrays.equals(now_Light, new int[] { 0, 2, 2 })
                        || Arrays.equals(now_Light, new int[] { 0, 0, 2 })) {
                    seconds = 5;
                }
                NS_side_g();
            }
        } else { // 兩方：綠燈方3秒黃燈再全紅，黃燈、全紅直接變
            if (Arrays.equals(now_Light, new int[] { 1, 0, 1 })) { // EW向綠燈
                EW_side_y();
                countDown(3);
                EW_side_ar();
            } else if (Arrays.equals(now_Light, new int[] { 0, 1, 2 })) { // NS向綠燈
                NS_side_y();
                countDown(3);
                NS_side_ar();
            } else if (Arrays.equals(now_Light, new int[] { 2, 0, 1 })
                    || Arrays.equals(now_Light, new int[] { 0, 0, 1 })) { // EW向黃燈||EW⇒NS紅燈
                EW_side_ar();
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 2 })
                    || Arrays.equals(now_Light, new int[] { 0, 0, 2 })) { // NS向黃燈||NS⇒EW紅燈
                NS_side_ar();
            } else if (Arrays.equals(now_Light, new int[] { 2, 0, 0 })) { // 如果之前是閃燈模式
                EW_side_f();
            } else if (Arrays.equals(now_Light, new int[] { 0, 2, 0 })) { // 如果之前是閃燈模式
                NS_side_f();
            }
            seconds = 3;
        }
    }

    public void trafficLightManual(int timer, int situation) {
        NS_side_ar();
        countDown(3);
        switch (situation) {
            case 0:// 一直綠燈(輸入方向)
                if (cP.condition == 1) {
                    EW_side_g();
                } else {
                    NS_side_g();
                }
                countDown(timer);
                break;
            case 1:// 閃燈，從路權拿
                   // flashing, countdown
                if (cP.flashing == 0) {
                    EW_side_f();
                } else {
                    NS_side_f();
                }
                countDown(timer);
                break;
            case 2:// 正常(輸入秒數)
                int grandTotal = 0;
                while (grandTotal < timer) {
                    if (Arrays.equals(now_Light, new int[] { 0, 0, 2 })) {
                        EW_side_g();
                        countDownSeconds = cP.greenLightTime_EW;
                    } else if (Arrays.equals(now_Light, new int[] { 1, 0, 1 })) {
                        EW_side_y();
                        countDownSeconds = cP.yellowLightTime_EW;
                    } else if (Arrays.equals(now_Light, new int[] { 2, 0, 1 })) {
                        EW_side_ar();
                        countDownSeconds = cP.allRedLightTime_EW;
                    } else if (Arrays.equals(now_Light, new int[] { 0, 0, 1 })) {
                        NS_side_g();
                        countDownSeconds = cP.greenLightTime_NS;
                    } else if (Arrays.equals(now_Light, new int[] { 0, 1, 2 })) {
                        NS_side_y();
                        countDownSeconds = cP.yellowLightTime_NS;
                    } else if (Arrays.equals(now_Light, new int[] { 0, 2, 2 })) {
                        NS_side_ar();
                        countDownSeconds = cP.allRedLightTime_NS;
                    }
                    countDown(countDownSeconds);
                    grandTotal += countDownSeconds;
                }
                break;
        }
        NS_side_ar();
        countDown(3);
    }

    public void EW_side_g() {
        EW_Light = new int[] { 1, 0, 0 };
        NS_Light = new int[] { 0, 0, 1 };
        now_Light = new int[] { 1, 0, 1 };
    }

    public void EW_side_y() {
        EW_Light = new int[] { 0, 1, 0 };
        NS_Light = new int[] { 0, 0, 1 };
        now_Light = new int[] { 2, 0, 1 };
    }

    public void EW_side_ar() {
        EW_Light = new int[] { 0, 0, 1 };
        NS_Light = new int[] { 0, 0, 1 };
        now_Light = new int[] { 0, 0, 1 };
    }

    public void NS_side_g() {
        EW_Light = new int[] { 0, 0, 1 };
        NS_Light = new int[] { 1, 0, 0 };
        now_Light = new int[] { 0, 1, 2 };
    }

    public void NS_side_y() {
        EW_Light = new int[] { 0, 0, 1 };
        NS_Light = new int[] { 0, 1, 0 };
        now_Light = new int[] { 0, 2, 2 };
    }

    public void NS_side_ar() {
        EW_Light = new int[] { 0, 0, 1 };
        NS_Light = new int[] { 0, 0, 1 };
        now_Light = new int[] { 0, 0, 2 };
    }

    public void EW_side_f() {
        EW_Light = new int[] { 0, 2, 0 };
        NS_Light = new int[] { 0, 0, 2 };
        now_Light = new int[] { 2, 0, 0 };
    }

    public void NS_side_f() {
        EW_Light = new int[] { 0, 0, 2 };
        NS_Light = new int[] { 0, 2, 0 };
        now_Light = new int[] { 0, 2, 0 };
    }

    public void countDown(int cDSs) {
        for (int i = cDSs; i >= 0; i--) {
            seconds = cDSs;
            try {
                Thread.sleep(1000); // 暫停 1 秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSecond() {
        return seconds;
    }

    public int[] getNow_Light() {
        return now_Light;
    }

    public int[] getEW_Light() {
        return EW_Light;
    }

    public int[] getNS_Light() {
        return NS_Light;
    }
}