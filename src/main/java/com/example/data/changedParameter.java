package com.example.data;

public class changedParameter{
    protected int greenLightTime_EW = 15;//20S R
    protected int yellowLightTime_EW = 3;
    protected int allRedLightTime_EW = 1;
    protected int greenLightTime_NS = 15;
    protected int yellowLightTime_NS = 3;
    protected int allRedLightTime_NS = 1;
    protected int condition = 0; //在自動是緊急方向，手動是哪邊要維持綠燈
    protected int flashing = 0;

    changedParameter(int cd, int right){
        condition = cd;//0: 正常, 1: EW, 2: NS, 3: both
        flashing = right;//0: 正常, 1: EW, 2: NS
    }
    changedParameter(int lightTime[]){
        condition = 0;
        flashing = 0;
        greenLightTime_EW = lightTime[0];
        yellowLightTime_EW = lightTime[1];
        allRedLightTime_EW = lightTime[2];
        greenLightTime_NS = lightTime[3];
        yellowLightTime_NS = lightTime[4];
        allRedLightTime_NS = lightTime[5];
    }
    changedParameter(int direction, int right, int lightTime[]){//手動用
        condition = direction;
        flashing = right;
        greenLightTime_EW = lightTime[0];
        yellowLightTime_EW = lightTime[1];
        allRedLightTime_EW = lightTime[2];
        greenLightTime_NS = lightTime[3];
        yellowLightTime_NS = lightTime[4];
        allRedLightTime_NS = lightTime[5];
    }
    /* 目前沒用的部份
     * public void setParameter(double rlt, double glt, double ylt, int fla){
     *     redLightTime = rlt; 
     *     greenLightTime = glt;
     *     yellowLightTime = ylt;
     *     flashing = fla;
     * }
     * public int check(){
     *     return (int)redLightTime;
     * }
     */
}

