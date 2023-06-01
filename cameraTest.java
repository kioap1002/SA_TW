import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.Mat;

import java.util.Timer;
import java.util.TimerTask;

public class ImageCaptureExample {
    public static void main(String[] args) throws FrameGrabber.Exception, InterruptedException {
        // 創建影片採集器
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0); // 0 表示默認攝像頭

        // 打開影片流
        grabber.start();

        // 創建計時器任務
        TimerTask captureTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    // 讀取影片幀
                    Frame frame = grabber.grab();
                    if (frame != null) {
                        // 在這裡可以对每一幀進行圖像處理
                        // 例如，可以保存圖像到文件

                        // 保存圖像
                        String fileName = "capture_" + System.currentTimeMillis() + ".jpg";
                        Java2DFrameConverter converter = new Java2DFrameConverter();
                        BufferedImage image = converter.getBufferedImage(frame);
                        ImageIO.write(image, "jpg", new File(fileName));

                        System.out.println("Captured: " + fileName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // 創建計時器，並設置每隔五秒執行一次任務
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(captureTask, 0, 5000); // 5000 毫秒表示五秒

        // 等待一段时间，讓拍摄任務運行
        Thread.sleep(60000); // 運行一分钟，可以根據需要進行調整

        // 停止計時器和採集器，並釋放資源
        timer.cancel();
        grabber.stop();
        grabber.release();
    }
}
