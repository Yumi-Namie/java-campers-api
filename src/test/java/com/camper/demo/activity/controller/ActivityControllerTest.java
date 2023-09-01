package com.camper.demo.activity.controller;

import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.activity.service.ActivityService;
import com.camper.demo.signup.entity.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {


    private ActivityController activityController;
    @Mock
    private ActivityService activityServiceMock;

    @BeforeEach
    void setUp() {

        activityController = new ActivityController(activityServiceMock);
    }

    @Test
    void getAllActivities_shouldReturnListOfActivities() {
        var signup1 = Signup.builder().build();

        var act1 = Activity.builder().id(1L).name("Hiking").difficulty(1).signups(List.of(signup1)).build();
        var act2 = Activity.builder().id(2L).name("Yoga").difficulty(1).signups(List.of(signup1)).build();
        var mockActivities = List.of(act1, act2);
        when(activityServiceMock.getAllActivities()).thenReturn(mockActivities);

        var act1dto = ActivityDTO.builder()
                .id(1L)
                .name("Hiking")
                .difficulty(1)
                .build();
        var act2dto = ActivityDTO.builder()
                .id(2L)
                .name("Yoga")
                .difficulty(1)
                .build();

        var expectedResult = List.of(act1dto, act2dto);

        // Testing the controller method
        var result = activityController.getAllActivities();

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expectedResult, result.getBody());
    }

    @Test
    void getActivityById_shouldReturnActivity() {

        // Mocking data
        var signup1 = Signup.builder().build();
        var activityId = 1L;
        var activity = Activity.builder().id(activityId).name("Hiking").difficulty(1).signups(List.of(signup1)).build();

        when(activityServiceMock.getActivityById(activityId)).thenReturn(activity);

        // Expected DTO - build constructor
        var activityDto = ActivityDTO.builder()
                .id(activityId)
                .name("Hiking")
                .difficulty(1)
                .build();

        // Testing the controller method
        var result = activityController.getActivityById(1L);

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(activityDto, result.getBody());

    }


    @Test
    void createActivity_shouldReturnCreated() {
        // Mocking data
        var activityDTO = ActivityDTO.builder()
                .name("Sample Activity1")
                .difficulty(2)
                .build();

        // Mocking service behavior
        var createdActivity = Activity.builder()
                .id(1L)
                .name("Sample Activity1")
                .difficulty(2)
                .build();

        when(activityServiceMock.createActivity(any())).thenReturn(createdActivity);

        // Testing the controller method
        ResponseEntity<ActivityDTO> result = activityController.createActivity(activityDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(createdActivity.getId(), result.getBody().getId());
        assertEquals(createdActivity.getName(), result.getBody().getName());
        assertEquals(createdActivity.getDifficulty(), result.getBody().getDifficulty());
    }

    @Test
    void updateActivity_shouldReturnUpdated() {
        // Mocking data
        var activityId = 1L;
        var updatedActivityDTO = ActivityDTO.builder()
                .id(activityId)
                .name("Updated Activity")
                .difficulty(3)
                .build();

        // Mocking service behavior
        var updatedActivity = Activity.builder()
                .id(activityId)
                .name("Updated Activity")
                .difficulty(3)
                .build();

        when(activityServiceMock.updateActivity(eq(activityId), any())).thenReturn(updatedActivity);

        // Testing the controller method
        ResponseEntity<ActivityDTO> result = activityController.updateActivity(activityId, updatedActivityDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(updatedActivity.getId(), result.getBody().getId());
        assertEquals(updatedActivity.getName(), result.getBody().getName());
        assertEquals(updatedActivity.getDifficulty(), result.getBody().getDifficulty());
    }


    @Test
    void deleteActivity_shouldReturnNoContent() {
        // Mocking data
        var activityId = 1L;

        // Mocking service behavior
        when(activityServiceMock.deleteActivity(activityId)).thenReturn(true);

        // Testing the controller method
        ResponseEntity<Void> result = activityController.deleteActivity(activityId);

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

}