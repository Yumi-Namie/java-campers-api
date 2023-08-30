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
    public ResponseEntity<List<CamperDTO>> getAllCampers() {
        List<CamperDTO> camperDTOs = camperService.getAllCampers()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(camperDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamperDTO> getCamperById(@PathVariable Long id) {
        Camper camper = camperService.getCamperById(id);
        return ResponseEntity.ok(convertToDto(camper));
    }

    private CamperDTO convertToDto(Camper camper) {
        CamperDTO dto = new CamperDTO();
        dto.setId(camper.getId());
        dto.setName(camper.getName());
        dto.setAge(camper.getAge());
        return dto;
    }
}
