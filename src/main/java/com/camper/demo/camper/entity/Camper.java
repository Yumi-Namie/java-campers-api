package com.camper.demo.camper.entity;

import com.camper.demo.activity.entity.Activity;
import com.camper.demo.signup.entity.Signup;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Builder
public class Camper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String username;
    private String password;

    @OneToMany(mappedBy = "camper")
    private List<Signup> signups;

}
