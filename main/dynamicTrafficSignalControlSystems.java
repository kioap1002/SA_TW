package main;
import javafx.print.PrintColor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.time.*;

// import org.apache.commons.lang3.time;

public class dynamicTrafficSignalControlSystems {
    public static void main() {
        //run
        final physicalTrafficSignal physicalTrafficSignal= new physicalTrafficSignal();
        final controller controller = new controller();
        //java只給用final
    }
}
/* 會執行的東東
 * 1. 一直執行的實體紅綠燈，要讓控制器得知現在是什麼狀態
 * -  要根據路口ID將靜態資料庫的資料丟給相機跟控制器
 * 2. 每5秒拍1張照片的相機 //這個相機要自己把資料丟進秒資料庫嗎??? 
 * 3. 在紅燈結束前5秒或每10秒或每5秒，取得現在最新資料，判斷現在需要使用什麼模板，套到實體紅綠燈上
 * 
 */