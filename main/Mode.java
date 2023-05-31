package main;

public abstract class Mode{ //傳給cp已經算完的秒數
    public abstract changedParameter changeMode();
}

class emergencyMode extends Mode{
    private int EV;

    emergencyMode(int EV){
        this.EV = EV;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        cP = new changedParameter(EV,0);
        return cP;
    }
}

class HighDensityMode extends Mode{
    private int D;
    private double density_EW;
    private double density_NS;
    HighDensityMode(int D, double EW_d, double NS_d){
        this.D = D;
        this.density_EW = EW_d;
        this.density_NS = NS_d;
    }
    public double calculateGreenLightSeconds(){



        
        return 0.0;
    }
    public changedParameter changeMode(){
        return new changedParameter();//x
        
    }
}
class BasicDensityMode extends Mode{
    int[] lightTime;
    BasicDensityMode(int[] lT /*從資料庫取得的預設秒數 */){
        lightTime = lT;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        cP = new changedParameter(0, 0, lightTime);
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
        cP = new changedParameter(0, fla);
        return cP;
    }
}