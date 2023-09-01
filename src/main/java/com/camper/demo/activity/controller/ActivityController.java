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
import org.modelmapper.ModelMapper;
@RestController
public class ActivityController {

    private final ActivityService activityService;
    private final ModelMapper modelMapper;

    @Autowired
    public ActivityController(ActivityService activityService, ModelMapper modelMapper) {
        this.activityService = activityService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/activities")
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        List<ActivityDTO> activityDTOs = activities.stream()
                .map(activity -> modelMapper.map(activity, ActivityDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(activityDTOs);
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        Activity activity = activityService.getActivityById(id);
        return ResponseEntity.ok(modelMapper.map(activity, ActivityDTO.class));
    }

    @PostMapping("/activity")
    public ResponseEntity<ActivityDTO> createActivity(@Valid @RequestBody ActivityDTO activityDTO) {
        Activity createdActivity = activityService.createActivity(activityDTO);
        return ResponseEntity.ok(modelMapper.map(createdActivity, ActivityDTO.class));
    }


    @PutMapping("/activity/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @RequestBody @Valid ActivityDTO updatedActivityDTO) {
        Activity updatedActivity = modelMapper.map(updatedActivityDTO, Activity.class);
        updatedActivity = activityService.updateActivity(id, updatedActivityDTO);
        return ResponseEntity.ok(modelMapper.map(updatedActivity, ActivityDTO.class));
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
