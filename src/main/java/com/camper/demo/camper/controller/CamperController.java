package com.camper.demo.camper.controller;

import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.dto.CamperWithActivitiesDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.service.CamperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CamperController {

    private final CamperService camperService;

    @Autowired
    public CamperController(CamperService camperService) {
        this.camperService = camperService;
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

        CamperWithActivitiesDTO responseDTO = convertToResponseDtoWithActivities(camper);
        return ResponseEntity.ok().body(responseDTO);
    }


    @GetMapping("/campers")
    public ResponseEntity<List<CamperResponseDTO>> getAllCampers() {
        List<Camper> allCampers = camperService.getAllCampers();
        List<CamperResponseDTO> responseDTOs = allCampers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }

    private ActivityDTO convertActivityToDto(Activity activity) {
        ActivityDTO dto = ActivityDTO.builder()
                .id(activity.getId())
                .name(activity.getName())
                .difficulty(activity.getDifficulty())
                .build();
        return dto;
    }
    private CamperResponseDTO convertToResponseDto(Camper camper) {
        return CamperResponseDTO.builder()
                .id(camper.getId())
                .name(camper.getName())
                .age(camper.getAge())
                .build();
    }


    private CamperWithActivitiesDTO convertToResponseDtoWithActivities(Camper camper) {
        return CamperWithActivitiesDTO.builder()
                .id(camper.getId())
                .name(camper.getName())
                .age(camper.getAge())
                .activities(camper.getSignups().stream()
                        .map(signup -> convertActivityToDto(signup.getActivity()))
                        .collect(Collectors.toList()))
                .build();
    }



}


