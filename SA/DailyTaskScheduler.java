import java.time.LocalTime;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
// --------------
class DailyTaskScheduler {
    private static final LocalTime TIME_TO_RUN = LocalTime.of(0, 0); // 設定每天執行的時間

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        // 取得目前時間
        LocalTime currentTime = LocalTime.now();
        
        // 計算到下次執行時間的時間間隔
        long initialDelay = Duration.between(currentTime, TIME_TO_RUN).getSeconds();
        if (initialDelay < 0) {
            initialDelay += Duration.ofDays(1).getSeconds(); // 如果已經過了當天指定的時間，則將初始延遲增加一天
        }
        
        // 使用ScheduledExecutorService在每天指定時間執行任務
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 在這裡放置你想要在每天指定時間執行的程式碼
                // 更新數據到day DB
                System.out.println("執行每天指定時間的任務");
            }
        }, initialDelay, Duration.ofDays(1).getSeconds(), TimeUnit.SECONDS);
    }
}