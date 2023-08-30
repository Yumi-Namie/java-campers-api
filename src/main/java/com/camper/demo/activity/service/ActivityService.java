package com.camper.demo.activity.service;

import java.util.List;
import java.util.ArrayList;

import com.camper.demo.activity.entity.Activity;
import com.camper.demo.activity.repository.ActivityRepository;
import com.camper.demo.activity.dto.ActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;


    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    public List<Activity> getAllActivities() {
        Iterable<Activity> activitiesIterable = activityRepository.findAll();
        List<Activity> activities = new ArrayList<>();

        activitiesIterable.forEach(activities::add);

        return activities;
    }

    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found with id: " + id));
    }
    public Activity createActivity(ActivityDTO activityDTO) {
        Activity newActivity = new Activity();
        newActivity.setName(activityDTO.getName());
        newActivity.setDifficulty(activityDTO.getDifficulty());

        return activityRepository.save(newActivity);
    }

    public Activity updateActivity(Long id, ActivityDTO activityDTO) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found with id: " + id));

        existingActivity.setName(activityDTO.getName());
        existingActivity.setDifficulty(activityDTO.getDifficulty());

        return activityRepository.save(existingActivity);
    }

    public void deleteActivity(Long id) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity not found with id: " + id));

        activityRepository.delete(activity);
    }

}