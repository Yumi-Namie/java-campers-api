package com.camper.demo.signup.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
@Data
public class SignupRequestDTO {

    @NotBlank(message = "Invalid camper ID: Empty")
    @NotNull(message = "Camper ID must not be null.")
    private Long camperId;

    @NotBlank(message = "Invalid Activity ID: Empty")
    @NotNull(message = "Activity ID must not be null.")
    private Long activityId;

    @NotBlank(message = "Invalid time: Empty")
    @NotNull(message = "Time field must not be null.")
    @Min(value = 0, message = "Time should be between 0 and 23.")
    @Max(value = 23, message = "Time should be between 0 and 23.")
    private Integer time;
}
