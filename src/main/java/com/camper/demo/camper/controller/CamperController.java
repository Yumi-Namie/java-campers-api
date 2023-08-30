package com.camper.demo.camper.controller;

import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.service.CamperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/camper")
public class CamperController {

    private final CamperService camperService;

    @Autowired
    public CamperController(CamperService camperService) {
        this.camperService = camperService;
    }

    @PostMapping
    public ResponseEntity<CamperResponseDTO> createCamper(@RequestBody CamperDTO camperCreateDTO) {
        CamperResponseDTO createdCamper = camperService.createCamper(camperCreateDTO);
        return ResponseEntity.ok().body(createdCamper);
    }


    @GetMapping
    public ResponseEntity<List<CamperResponseDTO>> getAllCampers() {
        List<Camper> allCampers = camperService.getAllCampers();
        List<CamperResponseDTO> responseDTOs = allCampers.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamperResponseDTO> getCamperById(@PathVariable Long id) {
        Camper camper = camperService.getCamperById(id);

        if (camper == null) {
            return ResponseEntity.notFound().build();
        }

        CamperResponseDTO responseDTO = convertToResponseDto(camper);
        return ResponseEntity.ok().body(responseDTO);
    }


    /*private CamperDTO convertToDto(Camper camper) {
        CamperDTO dto = new CamperDTO();
        dto.setId(camper.getId());
        dto.setName(camper.getName());
        dto.setAge(camper.getAge());
        return dto;
    }*/

    private CamperResponseDTO convertToResponseDto(Camper camper) {
        CamperResponseDTO dto = new CamperResponseDTO();
        dto.setId(camper.getId());
        dto.setName(camper.getName());
        dto.setAge(camper.getAge());
        return dto;
    }
}
