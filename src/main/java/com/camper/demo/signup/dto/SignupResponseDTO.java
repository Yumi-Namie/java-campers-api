package com.camper.demo.signup.dto;
import lombok.Data;

@Data
public class SignupResponseDTO {
    private Long id;
    private Long camperId;
    private Long activityId;
    private Integer time;
}