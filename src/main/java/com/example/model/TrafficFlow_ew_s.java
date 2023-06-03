package com.example.model;

import java.sql.Blob;
import java.time.LocalTime;


import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class TrafficFlow_ew_s {

    private Long ID;
    private String Road_Intersection_ID;
    private LocalTime Time;
    private Boolean Emergency_Vehicle;
    private Double Density;
    private Blob Photo;

}