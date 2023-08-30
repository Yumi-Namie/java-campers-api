package com.camper.demo.camper.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CamperDTO {

    private Long id;

    @NotBlank(message = "Invalid Name: Empty")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
    private String name;

    @NotBlank(message = "Invalid Age: Empty")
    @NotNull(message = "Age field should not be null.")
    @Min(value = 8, message = "Age should be at least 8.")
    @Max(value = 18, message = "Age should not exceed 18.")
    private Integer age;

    @NotNull(message = "Username field should not be null.")
    @NotBlank(message = "Username field should not be empty.")
    private String username;

    @NotNull(message = "Password field should not be null.")
    @NotBlank(message = "Password field should not be empty.")
    @Size(min = 8, message = "Password should have at least 8 characters.")
    private String password;
}
