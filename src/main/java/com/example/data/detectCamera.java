package com.example.data;

import java.io.File;
import java.sql.Time;
import java.time.*;
import java.util.Date;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

// 偵測相機 一個紅綠燈一個相機
public class detectCamera {
    private String roadID;
    private int direction;
    private calculation C = new calculation();
    private boolean emergency = false;
    private double density;
    private byte[] photo;
    private PythonInterpreter pyCamera = new PythonInterpreter();
    detectCamera(String rid, int dire){
        roadID = rid;
        direction = dire;
        pyCamera.execfile("src\\main\\py\\detectCamera.py");
    }
    public void shootIntersections(){
        int time = (int) System.currentTimeMillis() / 1000;
        int timeNow = (int) System.currentTimeMillis() / 1000;
        
        PyFunction c_V = pyCamera.get("create_video_capture", PyFunction.class);
        PyObject camera = c_V.__call__(new PyInteger(direction));
        PyFunction c_I = pyCamera.get("capture_image", PyFunction.class);
        PyFunction p_I = pyCamera.get("process_cctv_image", PyFunction.class);
        PyFunction ItBs = pyCamera.get("convert_image_to_bytes", PyFunction.class);

        PyObject image = c_I.__call__(new PyInteger(camera));
        PyObject image_date = p_I.__call__(image);
        PyObject image_bytes = ItBs.__call__(image);
        byte[] imageBytes = image_bytes.tojava(byte[].class);
        int[] imageData = image_data.tojava(int[].class);
        photo = imageBytes;
        density = C.calculateVehicleDensity((double)imageData[0]);
        if (imageData[1] > 0) {
            emergency = true;
        }
    }
}
// 下面兩個class都是依據相機設定車道方向
class east_westDetectCamera extends detectCamera {
    east_westDetectCamera(String rid) {
        super(rid, 0);
    }
}
class north_southDetectCamera extends detectCamera {
    north_southDetectCamera(String rid) {
        super(rid, 1);
    }
}