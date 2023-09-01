package com.camper.demo.camper.dto;

import com.camper.demo.activity.dto.ActivityDTO;


import lombok.Builder;
import lombok.Data;
import java.util.List;
@Builder
@Data
public class CamperWithActivitiesDTO {
    private Long id;
    private String name;
    private int age;
    private List<ActivityDTO> activities;
}
