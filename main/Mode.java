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
    private int[] lightTime;
    HighDensityMode(int D, double EW_d, double NS_d, int[] lT){
        this.D = D;
        this.density_EW = EW_d;
        this.density_NS = NS_d;
        this.lightTime = lT;
        calculateGreenLightSeconds();
    }
    public void calculateGreenLightSeconds(){

        switch (D) {
            case 0://both
                lightTime[0] = lightTime[0] + (int)(density_EW * 10);
                lightTime[3] = lightTime[3] + (int)(density_NS * 10);
                break;
            case 1://ew
                lightTime[0] = lightTime[0] + (int)(density_EW * 10);
                lightTime[3] = lightTime[3] - (int)(density_NS * 10);
                break;
            case 2://ns
                lightTime[3] = lightTime[3] + (int)(density_NS * 10);
                lightTime[0] = lightTime[0] - (int)(density_EW * 10);
                break;
        }

    }
    public changedParameter changeMode(){
        return new changedParameter(lightTime);//x
    }
}
class BasicDensityMode extends Mode{
    int[] lightTime;
    BasicDensityMode(int[] lT /*從資料庫取得的預設秒數 */){
        this.lightTime = lT;
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        cP = new changedParameter(lightTime);
        return cP;
    }
}
class LowDensityMode extends Mode{
    private int EW_right;
    private int NS_right;
    LowDensityMode(int EW, int NS){
        this.EW_right = EW;
        this.NS_right = NS;
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