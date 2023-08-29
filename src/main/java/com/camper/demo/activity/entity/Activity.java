package com.camper.demo.activity.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer difficulty;

}