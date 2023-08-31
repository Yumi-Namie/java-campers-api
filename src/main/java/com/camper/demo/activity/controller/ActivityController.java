package com.camper.demo.activity.controller;

import java.util.List;
import java.util.stream.Collectors;


import com.camper.demo.activity.entity.Activity;
import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }


    @GetMapping("/activities")
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        List<ActivityDTO> activityDTOs = activities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(activityDTOs);
    }


    @GetMapping("/activity/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivityById(id);
        return ResponseEntity.ok(convertToDto(activity));
    }

    @PostMapping("/activity")
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityDTO activity) {
        Activity createdActivity = activityService.createActivity(activity);
        return ResponseEntity.ok(convertToDto(createdActivity));
    }

    @PutMapping("/activity/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @RequestBody ActivityDTO activity) {
        Activity updatedActivity = activityService.updateActivity(id, activity);
        return ResponseEntity.ok(convertToDto(updatedActivity));
    }

    @DeleteMapping("/activity/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        boolean deleted = activityService.deleteActivity(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ActivityDTO convertToDto(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setDifficulty(activity.getDifficulty());
        return dto;
    }
}