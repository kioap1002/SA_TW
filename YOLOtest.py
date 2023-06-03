import cv2
import numpy as np
class YOLODetector:
    def __init__(self): # 載入YOLOv3模型相關檔案和權重
        self.net = cv2.dnn.readNet("yolov3.weights", "yolov3.cfg")
        self.classes = []
        with open("coco.names", "r") as f:
            self.classes = [line.strip() for line in f.readlines()]
        self.layer_names = self.net.getLayerNames()
        self.output_layers = [self.layer_names[i[0] - 1] for i in self.net.getUnconnectedOutLayers()]
    def detect_objects(self, image):
        # 處理圖像
        blob = cv2.dnn.blobFromImage(image, 0.00392, (416, 416), (0, 0, 0), True, crop=False)
        self.net.setInput(blob)
        outs = self.net.forward(self.output_layers)
        # 解析模型輸出
        class_ids = []
        confidences = []
        boxes = []
        for out in outs:
            for detection in out:
                scores = detection[5:]
                class_id = np.argmax(scores)
                confidence = scores[class_id]
                if confidence > 0.5:  # 設定置信度閾值
                    # 目標位置和尺寸
                    center_x = int(detection[0] * image.shape[1])
                    center_y = int(detection[1] * image.shape[0])
                    width = int(detection[2] * image.shape[1])
                    height = int(detection[3] * image.shape[0])
                    # 邊框座標
                    x = int(center_x - width / 2)
                    y = int(center_y - height / 2)
                    boxes.append([x, y, width, height])
                    confidences.append(float(confidence))
                    class_ids.append(class_id)
        indexes = cv2.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4) # 進行非最大值抑制
        # 返回偵測結果
        objects = []
        for i in range(len(boxes)):
            if i in indexes:
                x, y, w, h = boxes[i]
                label = self.classes[class_ids[i]]
                objects.append((label, x, y, w, h))
        return objects
if __name__ == "__main__":
    detector = YOLODetector() # 創建YOLODetector物件
    image = cv2.imread("road_image.jpg") # 讀取圖像
    detections = detector.detect_objects(image) # 進行目標檢測
    # 統計車輛和緊急車輛數量
    vehicle_count = 0
    emergency_count = False
    for detection in detections:
        label, _, _, _, _ = detection
        if label == 'car' or label == 'truck':
            vehicle_count += 1
        elif label == 'ambulance' or label == 'fire engine' or label == 'police car':
            emergency_count = True