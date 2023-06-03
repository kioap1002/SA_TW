import cv2
import time
import pymysql
class CCTV:
    def __init__(self):
        self.video_capture = cv2.VideoCapture(0) # 打開影片流 # 0代表預設攝像頭
    def capture_image(self):
        ret, frame = self.video_capture.read() # 讀取影片流的幀
        return frame # 返回捕獲的圖像
class Database:
    def __init__(self):
        self.connection = pymysql.connect(host='localhost', user='username', password='password', db='database') # 連接到資料庫
    def insert_image(self, image):
        # 將影像轉換為位元組資料
        _, img_bytes = cv2.imencode('.jpg', image)
        img_data = img_bytes.tobytes()
        # 插入圖像數據到資料庫
        cursor = self.connection.cursor()
        cursor.execute("INSERT INTO images (image_data) VALUES (%s)", (img_data,))
        self.connection.commit()
        cursor.close()
if __name__ == "__main__":
    db = Database() # 建立資料庫物件
    cctv = CCTV() # 建立CCTV物件並運行監控
    start_time = time.time() # 設定計時器
    while True:
        current_time = time.time() # 抓取目前的時間
        if current_time - start_time >= 5: # 檢查是否到達拍照間隔
            image = cctv.capture_image() # 拍攝照片
            db.insert_image(image) # 將影像儲存到資料庫中
            start_time = current_time # 更新計時器
        # 按下 'q' 鍵退出迴圈
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    db.connection.close()
    cctv.video_capture.release()