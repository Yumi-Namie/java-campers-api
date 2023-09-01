package com.camper.demo.camper.service;

import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.dto.CamperWithActivitiesDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.repository.CamperRepository;
import com.camper.demo.signup.dto.SignupResponseDTO;
import com.camper.demo.signup.entity.Signup;
import com.camper.demo.signup.repository.SignupRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CamperService {

    private final CamperRepository camperRepository;
    private final SignupRepository signupRepository;

    @Autowired
    public CamperService(CamperRepository camperRepository, SignupRepository signupRepository) {
        this.camperRepository = camperRepository;
        this.signupRepository = signupRepository;
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

    public Camper getCamperById(Long id) {
        return camperRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Camper with ID " + id + " not found."));
    }

    public CamperWithActivitiesDTO getCamperWithActivitiesAndSignupsById(Long id) {
        Camper camper = camperRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Camper with ID " + id + " not found."));

        return new CamperWithActivitiesDTO(
                camper.getId(),
                camper.getName(),
                camper.getAge(),
                camper.getSignups().stream()
                        .map(signup -> convertActivityToDto(signup.getActivity()))
                        .collect(Collectors.toList())
        );
    }

    private CamperResponseDTO convertToResponseDto(Camper camper) {
        return new CamperResponseDTO(camper.getId(), camper.getName(), camper.getAge());
    }

    private ActivityDTO convertActivityToDto(Activity activity) {
        return new ActivityDTO(activity.getId(), activity.getName(), activity.getDifficulty());
    }
}
