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
        return new changedParameter(EV,0);
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
        return new changedParameter(lightTime);
    }
}
class BasicDensityMode extends Mode{
    int[] lightTime;
    BasicDensityMode(int[] lT /*從資料庫取得的預設秒數 */){
        this.lightTime = lT;
    }
    public changedParameter changeMode(){
        return new changedParameter(lightTime);;
    }
}
class LowDensityMode extends Mode{
    private int fla = 1;
    LowDensityMode(int EW, int NS){
        //判斷flashing的狀態
        if(EW < NS){
            fla = 2;
        }
    }
    public changedParameter changeMode(){
        return new changedParameter(0, fla);
    }
}

class manualMode extends Mode{
    private int direction = 0;
    private int flashing = 0;
    private int[] lightTime = {0, 0, 0, 0, 0, 0};
    manualMode(int dire){
        direction = dire;
    }
    manualMode(int EW, int NS){
        flashing = 1;
        if(EW < NS){
            flashing = 2;
        }
    }
    manualMode(int glt_EW, int ylt_EW, int arlt_EW, int glt_NS, int ylt_NS, int arlt_NS){
        lightTime[0] = glt_EW;
        lightTime[1] = ylt_EW;
        lightTime[2] = arlt_EW;
        lightTime[3] = glt_NS;
        lightTime[4] = ylt_NS;
        lightTime[5] = arlt_NS;
    }
    public changedParameter changeMode(){
        return new changedParameter(direction, flashing, lightTime);
    }
}