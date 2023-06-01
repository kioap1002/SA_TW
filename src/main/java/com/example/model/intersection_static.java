package com.example.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class intersection_static {
    private String Road_Intersection_ID;
    private String Intersection_Name;
    private Integer TotalSeconds;
    private Integer SpeedLimit_ew;
    private Integer LaneWidth_ew;
    private Boolean RoadRight_ew;
    private Integer GreenLightTime_ew;
    
    private Integer SpeedLimit_ns;
    private Integer LaneWidth_ns;
    private Boolean RoadRight_ns;
    private Integer GreenLightTime_ns;
}
