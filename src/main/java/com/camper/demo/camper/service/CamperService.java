package com.camper.demo.camper.service;

import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperMapper;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.dto.CamperWithActivitiesDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.repository.CamperRepository;
import com.camper.demo.signup.repository.SignupRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamperService {

    private final CamperRepository camperRepository;
    private final SignupRepository signupRepository;
    private final CamperMapper camperMapper;

    @Autowired
    public CamperService(CamperRepository camperRepository, SignupRepository signupRepository, CamperMapper camperMapper) {
        this.camperRepository = camperRepository;
        this.signupRepository = signupRepository;
        this.camperMapper = camperMapper;
    }

    public CamperResponseDTO createCamper(CamperDTO camperCreateDTO) {

        if (camperRepository.existsByUsername(camperCreateDTO.getUsername())) {
            throw new EntityExistsException("Camper with username " + camperCreateDTO.getUsername() + " already exists.");
        }

        Camper camper = new Camper(
                camperCreateDTO.getName(),
                camperCreateDTO.getAge(),
                camperCreateDTO.getUsername(),
                camperCreateDTO.getPassword()
        );

        Camper createdCamper = camperRepository.save(camper);
        return convertToResponseDto(createdCamper);
    }


    public List<Camper> getAllCampers() {
        List<Camper> campers = camperRepository.findAll();
        if (campers.isEmpty()) {
            throw new EntityNotFoundException("No campers found.");
        }
        return campers;
    }


    public CamperWithActivitiesDTO getCamperWithActivitiesAndSignupsById(Long id) {
        Camper camper = camperRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Camper with ID " + id + " not found."));

        var bla = camperMapper.map(camper, CamperWithActivitiesDTO.class);
        return bla;
    }

    private CamperResponseDTO convertToResponseDto(Camper camper) {
        return camperMapper.map(camper, CamperResponseDTO.class);
    }



}
