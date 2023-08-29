package com.camper.demo.camper.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Camper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String username;
    private String password;

}
