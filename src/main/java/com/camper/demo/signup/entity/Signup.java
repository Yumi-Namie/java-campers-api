package com.camper.demo.signup.entity;

import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.entity.Camper;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data

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


    public Signup() {
    }
    public Signup(Long id, Camper camper, Activity activity, Integer time) {
        this.id = id;
        this.camper = camper;
        this.activity = activity;
        this.time = time;

    }


}