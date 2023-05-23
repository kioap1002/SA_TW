package main;


public abstract class Mode{ //回傳已經計算完的秒數
    public abstract changedParameter changeMode();
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
    }
    public changedParameter changeMode(){
        changedParameter cP = new changedParameter();
        
        switch (EV) {
            case 3: //both EV
                //全紅    
                cP.setParameter(3, 3, 3, 0);
                break;
            case 2:
                //NS EV
                cP.setParameter(2, 2, 2, 0);
                break;
            case 1:
                //EW EV
                cP.setParameter(1, 1, 1, 0);
                break;
            default:
                //something wrong
                System.out.println("wrong message");
                break;
        }
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
    }
    public changedParameter changeMode(){
        return new changedParameter();//x
        
    }
}
class BasicDensityMode extends Mode{
    public changedParameter changeMode(){
        //拿到該路口的defalt
        changedParameter cP = new changedParameter();
        //light time == 0 => defalt
        cP.setParameter(0, 0, 0, 0);
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
        cP.setParameter(0, 0, 0, fla);
        return cP;
    }
}