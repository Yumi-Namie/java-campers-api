package com.camper.demo.activity.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class ActivityDTO {

    private Long id;

    @NotBlank(message = "Invalid Activity: Empty")
    @NotNull(message = "Invalid Activity: NULL")
    @Size(min = 3, max = 30, message = "Invalid Activity: Must be of 3 - 30 characters")
    private String name;

    @NotNull(message = "Difficulty field should not be null.")
    @Min(value = 1, message = "Difficulty  should be at least 1.")
    @Max(value = 5, message = "Difficulty  should not exceed 5.")
    private Integer difficulty;

    public ActivityDTO() {

    }

    public ActivityDTO(Long id, String name, Integer difficulty) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
    }

}