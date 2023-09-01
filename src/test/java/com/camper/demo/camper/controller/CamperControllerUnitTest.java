package com.camper.demo.camper.controller;

import com.camper.demo.camper.dto.CamperDTO;
import com.camper.demo.camper.dto.CamperResponseDTO;
import com.camper.demo.camper.service.CamperService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    void getCamperById() {
    }

    @Test
    void getAllCampers() {
    }
}