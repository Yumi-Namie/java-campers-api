package com.camper.demo.camper.controller;

import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.dto.CamperWithActivitiesDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.service.CamperService;
import com.camper.demo.signup.entity.Signup;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CamperController {

    private final CamperService camperService;
    private final ModelMapper mapper;

    @Autowired
    public CamperController(CamperService camperService, ModelMapper mapper) {
        this.camperService = camperService;
        this.mapper = mapper;
    }

    @PostMapping("/camper")
    public ResponseEntity<CamperResponseDTO> createCamper(@Valid @RequestBody CamperDTO camperCreateDTO) {
        CamperResponseDTO createdCamper = camperService.createCamper(camperCreateDTO);
        return ResponseEntity.ok().body(createdCamper);
    }

    @GetMapping("/camper/{id}")
    public ResponseEntity<CamperWithActivitiesDTO> getCamperById(@PathVariable Long id) {
        Camper camper = camperService.getCamperById(id);

        if (camper == null) {
            return ResponseEntity.notFound().build();
        }

        CamperWithActivitiesDTO responseDTO = mapper.map(camper, CamperWithActivitiesDTO.class);
        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/campers")
    public ResponseEntity<List<CamperResponseDTO>> getAllCampers() {
        List<Camper> allCampers = camperService.getAllCampers();
        List<CamperResponseDTO> responseDTOs = allCampers.stream()
                .map(camper -> mapper.map(camper, CamperResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }

    private CamperResponseDTO convertToResponseDto(Camper camper) {
        CamperResponseDTO camperResponseDTO = mapper.map(camper, CamperResponseDTO.class);
        /*CamperResponseDTO dto = new CamperResponseDTO();
        dto.setId(camper.getId());
        dto.setName(camper.getName());
        dto.setAge(camper.getAge());*/
        return camperResponseDTO;
    }

    private CamperWithActivitiesDTO convertToResponseDtoWithActivities(Camper camper) {
        CamperWithActivitiesDTO camperWithActivitiesDTO = mapper.map(camper, CamperWithActivitiesDTO.class);

        List<ActivityDTO> activityDTOs = camper.getSignups().stream()
                .map(signup -> mapper.map(signup.getActivity(), ActivityDTO.class))
                .collect(Collectors.toList());

        camperWithActivitiesDTO.setActivities(activityDTOs);
        return camperWithActivitiesDTO;

        }
    }



/*
    private CamperResponseDTO convertToResponseDto(Camper camper) {
        CamperResponseDTO dto = new CamperResponseDTO();
        dto.setId(camper.getId());
        dto.setName(camper.getName());
        dto.setAge(camper.getAge());
        return dto;
    }

    private CamperWithActivitiesDTO convertToResponseDtoWithActivities(Camper camper) {
        CamperWithActivitiesDTO dto = new CamperWithActivitiesDTO();
        dto.setId(camper.getId());
        dto.setName(camper.getName());
        dto.setAge(camper.getAge());

        List<ActivityDTO> activityDTOs = new ArrayList<>();
        for (Signup signup : camper.getSignups()) {
            Activity activity = signup.getActivity();
            ActivityDTO activityDTO = new ActivityDTO();
            activityDTO.setId(activity.getId());
            activityDTO.setName(activity.getName());
            activityDTO.setDifficulty(activity.getDifficulty());
            activityDTOs.add(activityDTO);
        }

        dto.setActivities(activityDTOs);
        return dto;
    }*/
