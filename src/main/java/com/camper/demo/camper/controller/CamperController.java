package com.camper.demo.camper.controller;

import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperMapper;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.dto.CamperWithActivitiesDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.repository.CamperRepository;
import com.camper.demo.camper.service.CamperService;
import com.camper.demo.signup.entity.Signup;
import jakarta.persistence.EntityNotFoundException;
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
    private final CamperRepository camperRepository;
    private final CamperMapper mapper;

    @Autowired
    public CamperController(CamperService camperService, CamperMapper mapper, CamperRepository camperRepository) {
        this.camperService = camperService;
        this.mapper = mapper;
        this.camperRepository = camperRepository;
    }

    @PostMapping("/camper")
    public ResponseEntity<CamperResponseDTO> createCamper(@Valid @RequestBody CamperDTO camperCreateDTO) {
        CamperResponseDTO createdCamper = camperService.createCamper(camperCreateDTO);
        return ResponseEntity.ok().body(createdCamper);
    }

    @GetMapping("/camper/{id}")
    public ResponseEntity<CamperWithActivitiesDTO> getCamperById(@PathVariable Long id) {
        CamperWithActivitiesDTO responseDTO = camperService.getCamperWithActivitiesAndSignupsById(id);
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

    public CamperWithActivitiesDTO getCamperWithActivitiesAndSignupsById(Long id) {
        Camper camper = camperRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Camper with ID " + id + " not found."));

        return mapper.map(camper, CamperWithActivitiesDTO.class);
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
