package com.camper.demo.signup.dto;

import lombok.Data;
@Data
public class SignupRequestDTO {
    private Long camperId;
    private Long activityId;
    private Integer time;
}
