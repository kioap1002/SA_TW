import java.util.Timer;
import java.util.TimerTask;

public class timetest {
  public static void main(String[] args) {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        System.out.println("5秒已經過去了");
      }
    },0, 5000);
  }
}