package com.camper.demo.camper.dto;

import com.camper.demo.activity.dto.ActivityDTO;


import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
public class CamperWithActivitiesDTO {
    private Long id;
    private String name;
    private int age;
    private List<ActivityDTO> activities;

    public CamperWithActivitiesDTO() {
    }
    public CamperWithActivitiesDTO(Long id, String name, Integer age, List<ActivityDTO> activities) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.activities = activities;
    }

}
