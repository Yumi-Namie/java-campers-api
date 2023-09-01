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
        var signup1 = new Signup();
        signup1.setId(1L);

        var act1 = new Activity();
        act1.setId(1L);
        act1.setName("Hiking");
        act1.setDifficulty(1);
        act1.setSignups(List.of(signup1));

        var act2 = new Activity();
        act2.setId(2L);
        act2.setName("Yoga");
        act2.setDifficulty(1);
        act2.setSignups(List.of(signup1));

        var mockActivities = List.of(act1, act2);
        when(activityServiceMock.getAllActivities()).thenReturn(mockActivities);

        var act1dto = new ActivityDTO(1L, "Hiking", 1);
        var act2dto = new ActivityDTO(2L, "Yoga", 1);

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
        var signup1 = new Signup();
        var activityId = 1L;
        var activity = new Activity();
        activity.setId(activityId);
        activity.setName("Hiking");
        activity.setDifficulty(1);
        activity.setSignups(List.of(signup1));

        when(activityServiceMock.getActivityById(activityId)).thenReturn(activity);

        var activityDto = new ActivityDTO();
        activityDto.setId(activityId);
        activityDto.setName("Hiking");
        activityDto.setDifficulty(1);

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
        var activityDTO = new ActivityDTO();
        activityDTO.setName("Sample Activity1");
        activityDTO.setDifficulty(2);

        // Mocking service behavior
        var createdActivity = new Activity();
        createdActivity.setId(1L);
        createdActivity.setName("Sample Activity1");
        createdActivity.setDifficulty(2);

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
        var updatedActivityDTO = new ActivityDTO();
        updatedActivityDTO.setId(activityId);
        updatedActivityDTO.setName("Updated Activity");
        updatedActivityDTO.setDifficulty(3);

        // Mocking service behavior
        var updatedActivity = new Activity();
        updatedActivity.setId(activityId);
        updatedActivity.setName("Updated Activity");
        updatedActivity.setDifficulty(3);

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