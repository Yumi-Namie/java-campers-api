package com.camper.demo.camper.service;

import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.repository.CamperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CamperService {

    private final CamperRepository camperRepository;

    @Autowired
    public CamperService(CamperRepository camperRepository) {
        this.camperRepository = camperRepository;
    }

    public CamperResponseDTO createCamper(CamperDTO camperCreateDTO) {
        Camper camper = new Camper();
        camper.setName(camperCreateDTO.getName());
        camper.setAge(camperCreateDTO.getAge());
        camper.setUsername(camperCreateDTO.getUsername());
        camper.setPassword(camperCreateDTO.getPassword());

        Camper createdCamper = camperRepository.save(camper);
        return convertToResponseDto(createdCamper);
    }

    private CamperResponseDTO convertToResponseDto(Camper camper) {
        CamperResponseDTO dto = new CamperResponseDTO();
        dto.setId(camper.getId());
        dto.setName(camper.getName());
        dto.setAge(camper.getAge());
        return dto;
    }


    public List<Camper> getAllCampers() {
        return camperRepository.findAll();
    }

    public Camper getCamperById(Long id) {
        Optional<Camper> camper = camperRepository.findById(id);
        return camper.orElse(null);
    }
}
