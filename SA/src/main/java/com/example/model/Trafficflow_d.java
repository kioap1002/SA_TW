package com.example.model;


import java.time.LocalDate;

import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class Trafficflow_d {

    private Long ID;
    private String Road_Intersection_ID;
    private LocalDate Date;
    private Double Density_avg;
}
