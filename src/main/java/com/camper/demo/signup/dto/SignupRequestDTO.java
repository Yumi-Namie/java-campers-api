package com.camper.demo.signup.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
@Data
public class SignupRequestDTO {


    @NotNull(message = "Camper ID must not be null.")
    private Long camperId;


    @NotNull(message = "Activity ID must not be null.")
    private Long activityId;

    @NotNull(message = "Time field must not be null.")
    @Min(value = 0, message = "Time should be between 0 and 23.")
    @Max(value = 23, message = "Time should be between 0 and 23.")
    private Integer time;

    public SignupRequestDTO() {
    }

    public SignupRequestDTO(Long camperId, Long activityId, Integer time) {
        this.camperId = camperId;
        this.activityId = activityId;
        this.time = time;
    }
}
