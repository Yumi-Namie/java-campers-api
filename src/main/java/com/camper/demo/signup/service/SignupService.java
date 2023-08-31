package com.camper.demo.signup.service;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.camper.demo.activity.repository.ActivityRepository;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.camper.repository.CamperRepository;
import com.camper.demo.signup.dto.SignupRequestDTO;
import com.camper.demo.signup.entity.Signup;
import com.camper.demo.signup.repository.SignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignupService {

    private static final Logger log = LoggerFactory.getLogger(SignupService.class);


    private final SignupRepository signupRepository;
    private final CamperRepository camperRepository;
    private final ActivityRepository activityRepository;

    @Autowired //inject
    public SignupService(SignupRepository signupRepository, CamperRepository camperRepository, ActivityRepository activityRepository) {
        this.signupRepository = signupRepository;
        this.camperRepository = camperRepository;
        this.activityRepository = activityRepository;
    }

    public Signup createSignup(SignupRequestDTO signupRequestDTO) {
        Camper camper = camperRepository.findById(signupRequestDTO.getCamperId())
                .orElseThrow(() -> new EntityNotFoundException("Camper not found with id: " + signupRequestDTO.getCamperId()));

        Activity activity = activityRepository.findById(signupRequestDTO.getActivityId())
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + signupRequestDTO.getActivityId()));

  /*      Signup signup = new Signup();
        signup.setCamper(camper);
        signup.setActivity(activity);
        signup.setTime(signupRequestDTO.getTime());*/

        Signup signup = Signup.builder()
                .camper(camper)
                .activity(activity)
                .time(signupRequestDTO.getTime())
                .build();

        return signupRepository.save(signup);
    }



    public List<Signup> getAllSignups() {
        Iterable<Signup> allSignupsIterable = signupRepository.findAll();
        List<Signup> allSignups = new ArrayList<>();
        allSignupsIterable.forEach(allSignups::add);
        return allSignups;
    }
}
