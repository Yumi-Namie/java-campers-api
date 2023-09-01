package com.camper.demo.camper.controller;

import com.camper.demo.activity.entity.Activity;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CamperControllerUnitTest {

    private CamperController camperController;
    @Mock
    private CamperService camperServiceMock;

    @BeforeEach
    void setUp() {

        camperController = new CamperController(camperServiceMock);
    }

    @Test
    void createCamper() {
        //Mocking data
        var camperDTO = CamperDTO.builder()
                .name("Harry Olsen")
                .age(12)
                .username("hols12")
                .password("pass1234")
                .build();

        //Mocking service behavior
        var createdCamperResponseDTO = CamperResponseDTO.builder()
                .id(1L)
                .name("Harry Olsen")
                .age(12)
                .build();

        when(camperServiceMock.createCamper(any())).thenReturn(createdCamperResponseDTO);


        //Testing the controller method
        ResponseEntity<CamperResponseDTO> result = camperController.createCamper(camperDTO);


        //Assertions
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
        Camper camper1 = Camper.builder()
                .id(1L)
                .name("Camper 1")
                .age(15)
                .build();
        Camper camper2 = Camper.builder()
                .id(2L)
                .name("Camper 2")
                .age(17)
                .build();

        List<Camper> mockCampers = List.of(camper1, camper2);

        when(camperServiceMock.getAllCampers()).thenReturn(mockCampers);

        // Expected DTOs
        List<CamperResponseDTO> expectedDtos = mockCampers.stream()
                .map(camper -> CamperResponseDTO.builder()
                        .id(camper.getId())
                        .name(camper.getName())
                        .age(camper.getAge())
                        .build())
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
        Camper camper = Camper.builder()
                .id(camperId)
                .name("Harry Olsen")
                .age(12)
                .username("hols12")
                .password("pass1234")
                .build();

        Activity activity1 = Activity.builder()
                .id(1L)
                .name("Hiking")
                .difficulty(1)
                .build();

        Activity activity2 = Activity.builder()
                .id(2L)
                .name("Yoga")
                .difficulty(1)
                .build();

        Signup signup1 = Signup.builder()
                .id(1L)
                .camper(camper)
                .activity(activity1)
                .build();

        Signup signup2 = Signup.builder()
                .id(2L)
                .camper(camper)
                .activity(activity2)
                .build();

        camper.setSignups(List.of(signup1, signup2));

        return camper;
    }

    @Test
    void getCamperById_shouldReturnCamperWithActivities() {
        // Mocking data
        Long camperId = 1L;
        Camper camper = createSampleCamperWithActivities(camperId);

        when(camperServiceMock.getCamperById(camperId)).thenReturn(camper);

        // Testing the controller method
        ResponseEntity<CamperWithActivitiesDTO> result = camperController.getCamperById(camperId);

        // Assertions
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(camperId, result.getBody().getId());
        assertEquals("Harry Olsen", result.getBody().getName());
        assertEquals(12, result.getBody().getAge());
        // You can add more assertions to verify the list of activities in the DTO
        assertNotNull(result.getBody().getActivities());
        assertEquals(2, result.getBody().getActivities().size());
        assertEquals("Hiking", result.getBody().getActivities().get(0).getName());
        assertEquals("Yoga", result.getBody().getActivities().get(1).getName());
    }


}