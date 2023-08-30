package com.camper.demo.signup.entity;

import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.entity.Camper;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Signup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "camper_id")
    private Camper camper;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    private Integer time;

}