package com.camper.demo.signup.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponseDTO {
    private Long id;
    private Long camperId;
    private Long activityId;
    private Integer time;

    public SignupResponseDTO(Long id, Long camperId, Long activityId, Integer time) {
        this.id = id;
        this.camperId = camperId;
        this.activityId = activityId;
        this.time = time;
    }
}
