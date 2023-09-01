package com.camper.demo.camper.controller;

import com.camper.demo.activity.controller.ActivityController;
import com.camper.demo.activity.dto.ActivityDTO;
import com.camper.demo.activity.entity.Activity;
import com.camper.demo.activity.service.ActivityService;
import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.dto.CamperWithActivitiesDTO;
import com.camper.demo.camper.entity.Camper;
import com.camper.demo.camper.service.CamperService;

import com.camper.demo.signup.entity.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CamperControllerUnitTest {

    private CamperController camperController;
    private ActivityController activityController;
    @Mock
    private CamperService camperServiceMock;
    @Mock
    private ActivityService activityServiceMock;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        modelMapper = new ModelMapper();
        camperController = new CamperController(camperServiceMock, modelMapper);
        activityController = new ActivityController(activityServiceMock, modelMapper);
    }

    @Test
    void createCamper() {
        // Mocking data
        var camperDTO = new CamperDTO();
        camperDTO.setName("Harry Olsen");
        camperDTO.setAge(12);
        camperDTO.setUsername("hols12");
        camperDTO.setPassword("pass1234");

        // Mocking service behavior
        var createdCamperResponseDTO = new CamperResponseDTO();
        createdCamperResponseDTO.setId(1L);
        createdCamperResponseDTO.setName("Harry Olsen");
        createdCamperResponseDTO.setAge(12);

        when(camperServiceMock.createCamper(any())).thenReturn(createdCamperResponseDTO);

        // Testing the controller method
        ResponseEntity<CamperResponseDTO> result = camperController.createCamper(camperDTO);

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(createdCamperResponseDTO.getId(), result.getBody().getId());
        assertEquals(createdCamperResponseDTO.getName(), result.getBody().getName());
        assertEquals(createdCamperResponseDTO.getAge(), result.getBody().getAge());
    }


    @Test
    void getAllCampers_shouldReturnListOfCampers() {
        // Mocking data
        Camper camper1 = new Camper();
        camper1.setId(1L);
        camper1.setName("Camper 1");
        camper1.setAge(15);

        Camper camper2 = new Camper();
        camper2.setId(2L);
        camper2.setName("Camper 2");
        camper2.setAge(17);

        List<Camper> mockCampers = List.of(camper1, camper2);

        when(camperServiceMock.getAllCampers()).thenReturn(mockCampers);

        // Expected DTOs
        List<CamperResponseDTO> expectedDtos = mockCampers.stream()
                .map(camper -> {
                    CamperResponseDTO dto = new CamperResponseDTO();
                    dto.setId(camper.getId());
                    dto.setName(camper.getName());
                    dto.setAge(camper.getAge());
                    return dto;
                })
                .collect(Collectors.toList());

        // Testing the controller method
        ResponseEntity<List<CamperResponseDTO>> result = camperController.getAllCampers();

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(expectedDtos, result.getBody());
    }


    // Helper method to create a sample Camper with activities
    private Camper createSampleCamperWithActivities(Long camperId) {
        Camper camper = new Camper();
        camper.setId(camperId);
        camper.setName("Harry Olsen");
        camper.setAge(12);
        camper.setUsername("hols12");
        camper.setPassword("pass1234");

        Activity activity1 = new Activity();
        activity1.setId(1L);
        activity1.setName("Hiking");
        activity1.setDifficulty(1);

        Activity activity2 = new Activity();
        activity2.setId(2L);
        activity2.setName("Yoga");
        activity2.setDifficulty(1);

        Signup signup1 = new Signup();
        signup1.setId(1L);
        signup1.setCamper(camper);
        signup1.setActivity(activity1);

        Signup signup2 = new Signup();
        signup2.setId(2L);
        signup2.setCamper(camper);
        signup2.setActivity(activity2);

        camper.setSignups(List.of(signup1, signup2));

        return camper;
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




}