package com.camper.demo.signup.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.camper.demo.signup.dto.SignupRequestDTO;
import com.camper.demo.signup.dto.SignupResponseDTO;
import com.camper.demo.signup.entity.Signup;
import com.camper.demo.signup.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private static final Logger log = LoggerFactory.getLogger(SignupService.class);

    private final SignupService signupService;
    private final ModelMapper mapper;

    @Autowired
    public SignupController(SignupService signupService, ModelMapper mapper) {
        this.signupService = signupService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<SignupResponseDTO> createSignup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        Signup createdSignup = signupService.createSignup(signupRequestDTO);
        SignupResponseDTO responseDTO = mapper.map(createdSignup, SignupResponseDTO.class);
        return ResponseEntity.ok().body(responseDTO);
    }



    private SignupResponseDTO convertSignupToDto(Signup signup) {
        SignupResponseDTO signupResponseDTO = mapper.map(signup, SignupResponseDTO.class);
        return signupResponseDTO;
        /*return SignupResponseDTO.builder()
            .id(signup.getId())
            .camperId(signup.getCamper().getId())
            .activityId(signup.getActivity().getId())
            .time(signup.getTime())
            .build();*/
}

    @GetMapping
    public ResponseEntity<List<SignupResponseDTO>> getAllSignups() {
        List<Signup> allSignups = signupService.getAllSignups();
        Type listType = new TypeToken<List<SignupResponseDTO>>() {}.getType();
        List<SignupResponseDTO> responseDTOs = mapper.map(allSignups, listType);
        return ResponseEntity.ok().body(responseDTOs);
    }



}
