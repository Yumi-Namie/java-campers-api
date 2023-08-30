package com.camper.demo.camper.dto;

import com.camper.demo.activity.dto.ActivityDTO;

import com.camper.demo.signup.dto.SignupResponseDTO;
import lombok.Data;
import java.util.List;

@Data
public class CamperWithActivitiesDTO {
    private Long id;
    private String name;
    private int age;
    private List<ActivityDTO> activities;
}
