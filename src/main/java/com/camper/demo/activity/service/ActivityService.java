package com.camper.demo.activity.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import jakarta.validation.Valid;

import com.camper.demo.activity.entity.Activity;
import com.camper.demo.activity.repository.ActivityRepository;
import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.signup.entity.Signup;
import com.camper.demo.signup.repository.SignupRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private ModelMapper mapper;
    private final ActivityRepository activityRepository;
    private final SignupRepository signupRepository;


    @Autowired
    public ActivityService(ActivityRepository activityRepository, SignupRepository signupRepository,ModelMapper mapper) {
        this.activityRepository = activityRepository;
        this.signupRepository = signupRepository;
        this.mapper = mapper;
    }


    public List<Activity> getAllActivities() {
        Iterable<Activity> activitiesIterable = activityRepository.findAll();
        List<Activity> activities = new ArrayList<>();

        activitiesIterable.forEach(activities::add);

        if (activities.isEmpty()) {
            throw new EntityNotFoundException("No activities found.");
        }

        return activities;
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity with ID " + id + " not found."));
    }

    public Activity createActivity(ActivityDTO activityDTO) {
        Activity newActivity = new Activity();
        newActivity.setName(activityDTO.getName());
        newActivity.setDifficulty(activityDTO.getDifficulty());
        return activityRepository.save(newActivity);
    }


    public Activity updateActivity(Long id, @Valid ActivityDTO activityDTO) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + id));

        if (activityDTO.getName() != null && !activityDTO.getName().isEmpty()) {
            existingActivity.setName(activityDTO.getName());
        }

        if (activityDTO.getDifficulty() != null) {
            existingActivity.setDifficulty(activityDTO.getDifficulty());
        }

        return activityRepository.save(existingActivity);
    }


    public boolean deleteActivity(Long id) {
        Optional<Activity> activityOptional = activityRepository.findById(id);

        if (activityOptional.isPresent()) {
            Activity activity = activityOptional.get();

            // Delete associated signups
            List<Signup> signups = signupRepository.findByActivity(activity);
            signupRepository.deleteAll(signups);

            // Delete the activity
            activityRepository.delete(activity);
            return true;
        }

        throw new EntityNotFoundException("Activity not found with id: " + id);
    }
}