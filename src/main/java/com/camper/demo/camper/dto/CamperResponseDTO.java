package com.camper.demo.camper.dto;

import lombok.Builder;
import lombok.Data;
@Data
public class CamperResponseDTO {

    private Long id;
    private String name;
    private Integer age;

    public CamperResponseDTO() {

    }

    public CamperResponseDTO(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


}



