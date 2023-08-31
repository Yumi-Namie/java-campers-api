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
import org.springframework.http.HttpStatusCode;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {

    @Mock
    private ActivityService activityServiceMock;

    private ActivityController activityController;

    @BeforeEach
    void setUp() {

        activityController = new ActivityController(activityServiceMock);
    }

    @Test
    void getAllActivities_shouldReturnListOfActivities() {
        var signup1 = Signup.builder().build();

        var act1 = Activity.builder().id(1L).name("Jose").difficulty(1).signups(List.of(signup1)).build();
        var act2 = Activity.builder().id(2L).name("Maria").difficulty(1).signups(List.of(signup1)).build();
        var mockActivities = List.of(act1, act2);
        when(activityServiceMock.getAllActivities()).thenReturn(mockActivities);

        var act1dto = ActivityDTO.builder()
                .id(1L)
                .name("Jose")
                .difficulty(1)
                .build();
        var act2dto = ActivityDTO.builder()
                .id(2L)
                .name("Maria")
                .difficulty(1)
                .build();

       var expectedResult = List.of(act1dto, act2dto);

        var result = activityController.getAllActivities();

        assertNotNull(result);
        assertEquals(HttpStatusCode.valueOf(200),result.getStatusCode());
        assertEquals(expectedResult, result.getBody());
    }

    @Test
    void getActivityById() {
    }

    @Test
    void createActivity() {
    }

    @Test
    void updateActivity() {
    }

    @Test
    void deleteActivity() {
    }
}