package com.example.data;

//單純用來設定和放置 east_westLane 跟 north_southLane 的
public class roadSituation_sum {
  private boolean east_westLane = false;  //ew有無緊急車輛，false = ew車道上無緊急車輛
  private boolean north_southLane = false;  //ns有無緊急車輛，false = ns車道上無緊急車輛
  private double east_westDensity;
  private double north_southDensity;

  roadSituation_sum(boolean EV_EW,boolean EV_NS, double D_EW, double D_NS) {
      east_westLane = EV_EW;
      north_southLane = EV_NS;
      east_westDensity = D_EW;
      north_southDensity = D_NS;
  }
  //回傳緊急車輛出現狀態(有無緊急車輛, if 有: 車道方向為何)
  public int haveEmergency(){  //0:no_EV, 1:e_w_EV, 2:n_s_EV, 3:both_EV
      if(east_westLane && north_southLane){
          return 3;
      } else if(north_southLane){
          return 2;
      } else if(east_westLane){
          return 1;
      }else {
          return 0;
      }
  }
//  public int densityMode_col(double Last30DaysDensity_EW, double Last30DaysDensity_NS){  //密度模板 0: 低 1:正常 2:高
  public int densityMode_col(double Last30DaysDensity){  //密度模板 0: 低 1:正常 2:高
      int EW=0,NS=0;
      if(east_westDensity > Last30DaysDensity * 1.5) {
          EW = 2;  //高密度
      } else if (east_westDensity < Last30DaysDensity * 0.5){
          EW = 0;  //低密度
      } else {
          EW = 1;  //普通密度
      }
      if(north_southDensity > Last30DaysDensity * 1.5) {
          NS = 2;
      } else if (north_southDensity < Last30DaysDensity * 0.5){
          NS = 0;
      } else {
          NS = 1;
      }
      return densityMode(EW, NS);
  }
  // 單處理密度　密度模板 0: both高 1: EW高 2:NS高, 3: NS or EW普通, 4: both低
  public int densityMode(int EW, int NS){  
      int mode_D;
      if (EW == 2 || NS == 2){
          if (EW == 2 && NS == 2){
              // 兩邊高密度，增加時間配比
              mode_D = 0;
          } else if (EW == 2){
              // EW高密度，增加綠燈秒數
              mode_D = 1;
          } else {
              // NS高密度，增加紅燈秒數
              mode_D = 2;
          }
      } else if (EW == 1 || NS == 1){
          // 套用正常模板
          mode_D = 3;
      } else {
          // 套用低密度模板
          mode_D = 4;
      }
      return mode_D;
  }
}