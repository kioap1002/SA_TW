public class Countdown {
    public static void main(String[] args) {
        int seconds = 10; // 倒數秒數

        for (int i = seconds; i >= 0; i--) {
            System.out.println("倒數：" + i + " 秒");

            try {
                Thread.sleep(1000); // 暫停 1 秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("倒數計時結束");
    }
}
