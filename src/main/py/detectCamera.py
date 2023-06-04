import cv2
import numpy as np
# 創相機
def create_video_capture(device_index):
    return cv2.VideoCapture(device_index)
# 拍照
def capture_image(video_capture):
    ret, frame = video_capture.read()
    return frame
# 轉圖片格式
def convert_image_to_bytes(image):
    _, img_bytes = cv2.imencode('.jpg', image)
    img_data = img_bytes.tobytes()
    return img_data
# YOLO照片處理
def detect_objects(net, classes, output_layers, image):
    blob = cv2.dnn.blobFromImage(image, 0.00392, (416, 416), (0, 0, 0), True, crop=False)
    net.setInput(blob)
    outs = net.forward(output_layers)

    class_ids = []
    confidences = []
    boxes = []
    for out in outs:
        for detection in out:
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]
            if confidence > 0.5:
                center_x = int(detection[0] * image.shape[1])
                center_y = int(detection[1] * image.shape[0])
                width = int(detection[2] * image.shape[1])
                height = int(detection[3] * image.shape[0])
                x = int(center_x - width / 2)
                y = int(center_y - height / 2)
                boxes.append([x, y, width, height])
                confidences.append(float(confidence))
                class_ids.append(class_id)

    indexes = cv2.dnn.NMSBoxes(np.array(boxes), np.array(confidences), 0.5, 0.4)

    objects = []
    for i in range(len(boxes)):
        if i in indexes:
            x, y, w, h = boxes[i]
            label = classes[class_ids[i]]
            objects.append((label, x, y, w, h))

    return objects
# YOLO資料處理
def process_cctv_image(image):
    classes = []
    with open("coco.names", "r") as f:
        classes = [line.strip() for line in f.readlines()]
    net = cv2.dnn.readNet("yolov3.weights", "yolov3.cfg")
    layer_names = net.getLayerNames()
    output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]

    detections = detect_objects(net, classes, output_layers, image)

    vehicle_count = 0
    emergency_count = 0

    for detection in detections:
        label, _, _, _, _ = detection
        if label in ['car', 'truck']:
            vehicle_count += 1
        elif label in ['ambulance', 'fire engine', 'police car']:
            emergency_count += 1

    return vehicle_count, emergency_count
if __name__ == "__main__":
    device_index=0
    video_capture = create_video_capture(device_index)
    video_capture.release()