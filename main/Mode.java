package main;

public abstract class Mode{ //回傳已經計算完的秒數
    // int modeID = 2;// 0: 緊急 1: 低 2: 基礎 3: 高
    public abstract changedParameter changeMode();
    // public int getModeID(){
    //     return modeID;
    // }
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
        //this.modeID = 0;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        cP = new changedParameter(EV,0);
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
        //this.modeID = 3;
    }
    public double calculateGreenLightSeconds(double density){
        return 0.0;
    }
    public changedParameter changeMode(){
        return new changedParameter();//x
        
    }
}
class BasicDensityMode extends Mode{
    BasicDensityMode(){
        //this.modeID = 2;
    }
    public changedParameter changeMode(){
        //拿到該路口的defalt
        changedParameter cP = new changedParameter();
        //light time == 0 => defalt
        cP = new changedParameter(0,0);
        return cP;
    }
}
class LowDensityMode extends Mode{
    private int EW_right;
    private int NS_right;
    LowDensityMode(int EW, int NS){
        EW_right = EW;
        NS_right = NS;
        //this.modeID = 1;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        //判斷flashing的狀態
        int fla = 1;
        if(EW_right < NS_right){
            fla = 2;
        }
        cP = new changedParameter(0, fla);
        return cP;
    }
}