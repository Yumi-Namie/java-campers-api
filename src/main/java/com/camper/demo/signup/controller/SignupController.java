package com.camper.demo.signup.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.camper.demo.signup.dto.SignupRequestDTO;
import com.camper.demo.signup.dto.SignupResponseDTO;
import com.camper.demo.signup.entity.Signup;
import com.camper.demo.signup.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private static final Logger log = LoggerFactory.getLogger(SignupService.class);

    private final SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {this.signupService = signupService;}

    @PostMapping
    public ResponseEntity<SignupResponseDTO> createSignup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {

        Signup createdSignup = signupService.createSignup(signupRequestDTO);

        SignupResponseDTO responseDTO = convertToResponseDto(createdSignup);
        return ResponseEntity.ok().body(responseDTO);
    }


    private SignupResponseDTO convertToResponseDto(Signup signup) {
        SignupResponseDTO dto = new SignupResponseDTO();
        dto.setId(signup.getId());
        dto.setCamperId(signup.getCamper().getId());
        dto.setActivityId(signup.getActivity().getId());
        dto.setTime(signup.getTime());
        return dto;
    }

    @GetMapping
    public ResponseEntity<List<SignupResponseDTO>> getAllSignups() {
        List<Signup> allSignups = signupService.getAllSignups();
        List<SignupResponseDTO> responseDTOs = allSignups.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }


}
