package com.camper.demo.camper.dto;

import com.camper.demo.activity.dto.ActivityDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class CamperWithActivitiesDTO {
    private Long id;
    private String name;
    private int age;
    private List<ActivityDTO> activities;

}
