import cv2
import time
class CCTV:
    def __init__(self):
        self.video_capture = cv2.VideoCapture(0) # 打开影片流 # 0代表默认摄像头
        self.start_time = time.time() # 设置计时器
    # 處理照片的部分，使用AI偵測，未完成
    def process_image(self, image):
        # 程式碼
        height, width, _ = image.shape
    # 运行CCTV监控
    def run(self):
        while True:
            ret, frame = self.video_capture.read() # 读取影片流的帧
            current_time = time.time() # 获取当前时间
            if current_time - self.start_time >= 5: # 检查是否到达拍照间隔
                image_name = f"photo_{int(current_time)}.jpg" 
                cv2.imwrite(image_name, frame) # 保存图片
                self.process_image(frame) # 处理图像
                self.start_time = current_time # 更新计时器
            # 按下 'q' 键退出循环
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
        self.video_capture.release() # 释放影片流
if __name__ == "__main__":
    cctv = CCTV() # 创建CCTV对象并运行监控
    cctv.run()