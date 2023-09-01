package com.camper.demo.camper.dto;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CamperResponseDTO {

    private Long id;
    private String name;
    private Integer age;
}



