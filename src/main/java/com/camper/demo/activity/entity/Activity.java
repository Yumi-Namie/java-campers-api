package com.camper.demo.activity.entity;

import com.camper.demo.signup.entity.Signup;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer difficulty;

    @OneToMany(mappedBy = "activity")
    private List<Signup> signups;

}